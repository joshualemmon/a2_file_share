package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;

import java.net.Socket;
import java.util.Optional;


public class Main extends Application {

    private static Socket clientSocket      = null;
    private static BufferedInputStream bis  = null;
    private static BufferedOutputStream bos = null;
    private static BufferedReader in        = null;
    private static PrintWriter writer       = null;

    public  static String SERVER_ADDRESS    = "127.0.0.8";
    public  static int    SERVER_PORT       = 8080;

    private static String computerName      = "";
    private static String folderPath         = "";
    private static String destPath = ".serverFolder";

    private static File downloadFile        = null;
    private static File uploadFile          = null;         //File to upload
    private static File copyFile            = null;         //File is filename + copy of uploadFile that will send through stream

    public static ListView<String> leftList;
    public static ListView<String> rightList;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 500,500, Color.LIGHTGRAY);
        primaryStage.setTitle("Assignment 2 - File Sharer");
        primaryStage.setScene(scene);
        primaryStage.show();

        drawUI(root);
    }

    public static void drawUI(Group root)
    {
        TextInputDialog argsPrompt = new TextInputDialog();

        argsPrompt.setHeaderText("Computer Name");
        argsPrompt.setContentText("Please input your computer name");
        do
        {
            argsPrompt.showAndWait();
            computerName = argsPrompt.getEditor().getText();
        } while(computerName.equals(""));

        argsPrompt.setHeaderText("Folder Path");
        argsPrompt.getEditor().setText("");
        argsPrompt.setContentText("Please input your local folder path");
        do
        {
            argsPrompt.showAndWait();
            folderPath = argsPrompt.getEditor().getText();
        } while(folderPath.equals(""));

        leftList = new ListView<>();
        rightList = new ListView();
        updateFileLists(leftList, rightList);

        BorderPane bp = new BorderPane();
        HBox h = new HBox();

        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(event -> {
            String fileName = rightList.getSelectionModel().getSelectedItem();
            downloadFile = new File(destPath + "/" + fileName);
            copyFile = new File(destPath + "/COPY-" + fileName);
            Alert downloadAlert = new Alert(Alert.AlertType.CONFIRMATION, "File \'" + fileName + "\' will be downloaded.\nContinue?");
            Optional<ButtonType> result = downloadAlert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                String line = null;
                try
                {
                    clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                    copyFile.createNewFile();
                    in = new BufferedReader(new FileReader(downloadFile));    //BufferedReader for original file
                    writer = new PrintWriter(copyFile);                     //Writer to temporary file

                    writer.println("DOWNLOAD");
                    writer.println(downloadFile.getName());                   //Write file name to temporary file
                    writer.println(folderPath);

                    while((line = in.readLine()) != null) {                 //Write file data to temporary file
                        writer.println(line);
                    }

                    in.close();
                    writer.close();

                    long lengthOfFile = copyFile.length();
                    byte[] bytesInFile = new byte[(int)lengthOfFile];

                    bis = new BufferedInputStream(new FileInputStream(copyFile)); //Stream for file
                    bis.read(bytesInFile, 0, bytesInFile.length); //Read in the file to stream

                    bos = new BufferedOutputStream(clientSocket.getOutputStream()); //Stream to socket
                    bos.write(bytesInFile, 0, bytesInFile.length); //Write file data to socket
                    bos.flush();

                    bis.close();
                    bos.close();
                    copyFile.delete();

                    updateFileLists(leftList, rightList);
                }
                catch(IOException ioe)
                {
                    ioe.printStackTrace();
                }
                updateFileLists(leftList, rightList);
                Alert downloadedAlert = new Alert(Alert.AlertType.INFORMATION, "File \'" + fileName + "\' downloaded.");
                downloadedAlert.show();
            }

        });
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(event -> {
            String fileName = leftList.getSelectionModel().getSelectedItem();
            uploadFile = new File(folderPath + "/" + fileName);
            copyFile = new File(folderPath + "/COPY-" + fileName);
            Alert uploadAlert = new Alert(Alert.AlertType.CONFIRMATION, "File \'" + uploadFile.getName() + "\' will be uploaded.\nContinue?");
            Optional<ButtonType> result = uploadAlert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                String line = null;
                try
                {
                    clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                    copyFile.createNewFile();                               //Create a temporary file

                    in = new BufferedReader(new FileReader(uploadFile));    //BufferedReader for original file
                    writer = new PrintWriter(copyFile);                     //Writer to temporary file

                    writer.println("UPLOAD");                               //Write UPLOAD message to server
                    writer.println(uploadFile.getName());                   //Write file name to temporary file
                    writer.println(destPath);


                    while((line = in.readLine()) != null) {                 //Write file data to temporary file
                        writer.println(line);
                    }

                    in.close();
                    writer.close();

                    long lengthOfFile = copyFile.length();                          //Counts the length/size of the file
                    byte[] bytesInFile = new byte[(int)(lengthOfFile)];             //Stores the bytes in the file, I don't know how though

                    bis = new BufferedInputStream(new FileInputStream(copyFile));   //Stream for temporary file
                    bis.read(bytesInFile, 0, bytesInFile.length);                   //Read in the file to stream

                    bos = new BufferedOutputStream(clientSocket.getOutputStream()); //Stream to socket
                    bos.write(bytesInFile, 0, bytesInFile.length);                  //Write file data to socket
                    bos.flush();                                                    //Release stream and close

                    bis.close();
                    bos.close();
                    copyFile.delete();

                    updateFileLists(leftList, rightList);
                } catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
                updateFileLists(leftList, rightList);
                Alert uploadedAlert = new Alert(Alert.AlertType.INFORMATION, "File \'" + uploadFile.getName() + "\' uploaded.");
                uploadedAlert.show();
            }
        });
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            String fileName = rightList.getSelectionModel().getSelectedItem();
            File deleteFile = new File(fileName);
            Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "File \'" + deleteFile.getName() + "\' will be deleted from server.\nContinue?");
            Optional<ButtonType> result = deleteAlert.showAndWait();
            if(result.isPresent() &&  result.get() == ButtonType.OK)
            {
                try
                {
                    clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                    writer = new PrintWriter(deleteFile);                     //Writer to temporary file

                    writer.println("DELETE");                               //Write UPLOAD message to server
                    writer.println(fileName);                               //Write file name to temporary file
                    writer.println(destPath);
                    writer.close();

                    long lengthOfFile = deleteFile.length();                          //Counts the length/size of the file
                    byte[] bytesInFile = new byte[(int) (lengthOfFile)];             //Stores the bytes in the file, I don't know how though

                    bis = new BufferedInputStream(new FileInputStream(deleteFile));   //Stream for temporary file
                    bis.read(bytesInFile, 0, bytesInFile.length);                   //Read in the file to stream

                    bos = new BufferedOutputStream(clientSocket.getOutputStream()); //Stream to socket
                    bos.write(bytesInFile, 0, bytesInFile.length);                  //Write file data to socket
                    bos.flush();                                                    //Release stream and close

                    bis.close();
                    bos.close();
                    deleteFile.delete();

                    updateFileLists(leftList, rightList);
                } catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
            Alert deletedAlert = new Alert(Alert.AlertType.INFORMATION, "File \'" + fileName + "\' deleted.");
            deletedAlert.show();
            updateFileLists(leftList, rightList);
        });
        SplitPane sp = new SplitPane();

        final StackPane sp1 = new StackPane();
        sp1.getChildren().addAll(leftList);
        final StackPane sp2 = new StackPane();
        sp2.getChildren().add(rightList);

        sp.getItems().addAll(sp1,sp2);
        sp.setMinHeight(465);
        sp.setDividerPosition(1, 0.5f);

        h.getChildren().addAll(downloadButton, uploadButton, deleteButton);
        bp.setTop(h);
        bp.setCenter(sp);
        root.getChildren().addAll(bp);
    }

    public static String[] getFiles()
    {
        File localDir = new File(folderPath);
        File[] files =  localDir.listFiles();
        String[] s = new String[files.length];
        int i = 0;
        for(File f : files)
        {
            s[i++] = f.getName();
        }
        return s;
    }

    public static void updateFileLists(ListView<String> leftList, ListView<String> rightList )
    {

        final ObservableList<String> left = FXCollections.observableArrayList(getFiles());
        final ObservableList<String> right = FXCollections.observableArrayList(FileServer.getServerFiles());
        leftList.setItems(left);
        rightList.setItems(right);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
