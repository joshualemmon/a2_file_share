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

    public  static String SERVER_ADDRESS    = "127.0.0.8";
    public  static int    SERVER_PORT       = 8080;

    private static String computerName      = "";
    private static String folderPath        = "";
    private static File uploadFile = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 500,500, Color.LIGHTGRAY);
        primaryStage.setTitle("Assignment 2 - File Sharer");
        primaryStage.setScene(scene);
        primaryStage.show();

        clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);

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

        ListView<String> leftList = new ListView<>();
        String[] localFiles = getFiles();
        final ObservableList<String> localNames = FXCollections.observableArrayList(localFiles);
        leftList.setItems(localNames);
        ListView rightList = new ListView();
        final ObservableList<String> serverNames = FXCollections.observableArrayList(FileServer.getServerFiles());
        rightList.setItems(serverNames);
        BorderPane bp = new BorderPane();
        HBox h = new HBox();

        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(event -> {
            String fileName = (String)rightList.getSelectionModel().getSelectedItem();
            Alert downloadAlert = new Alert(Alert.AlertType.CONFIRMATION, "File \'" + fileName + "\' will be downloaded.\nContinue?");
            Optional<ButtonType> result = downloadAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                Alert downloadedAlert = new Alert(Alert.AlertType.INFORMATION, "File downloaded.");
                downloadedAlert.show();
            }

        });
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(event -> {
            String fileName = leftList.getSelectionModel().getSelectedItem();
            uploadFile = new File(folderPath + "/" + fileName);
            Alert uploadAlert = new Alert(Alert.AlertType.CONFIRMATION, "File \'" + uploadFile.getName() + "\' will be uploaded.\nContinue?");
            Optional<ButtonType> result = uploadAlert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                try //This Code should be put into a client class
                {
                    long lengthOfFile = uploadFile.length();    //Counts the length of the file
                    byte[] bytesInFile = new byte[(int)(lengthOfFile)]; //Stores the bytes in the file, I don't know how though

                    bis = new BufferedInputStream(new FileInputStream(uploadFile)); //Stream for file
                    bis.read(bytesInFile, 0, bytesInFile.length); //Read in the file to stream
                    bos = new BufferedOutputStream(clientSocket.getOutputStream()); //Stream to socket
                    bos.write(bytesInFile, 0, bytesInFile.length); //Write file data to socket
                    bos.flush(); //Finalize

                    bis.close();
                    bos.close();
                    final ObservableList<String> serverFiles = FXCollections.observableArrayList(FileServer.getServerFiles());
                    rightList.setItems(serverFiles);
                } catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }

                Alert uploadedAlert = new Alert(Alert.AlertType.INFORMATION, "File \'" + uploadFile.getName() + "\' uploaded.");
                uploadedAlert.show();
            }
        });

        SplitPane sp = new SplitPane();

        final StackPane sp1 = new StackPane();
        sp1.getChildren().addAll(leftList);
        final StackPane sp2 = new StackPane();
        sp2.getChildren().add(rightList);

        sp.getItems().addAll(sp1,sp2);
        sp.setMinHeight(465);
        sp.setDividerPosition(1, 0.5f);

        h.getChildren().addAll(downloadButton, uploadButton);
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

    public static void main(String[] args) {
        launch(args);
    }
}
