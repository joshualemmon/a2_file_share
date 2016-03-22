package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {

    private static String computerName = "";
    private static String folderPath = "";

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
        }while(computerName.equals(""));

        argsPrompt.setHeaderText("Folder Path");
        argsPrompt.setContentText("Please input your local folder path");
        do
        {
            argsPrompt.showAndWait();
            folderPath = argsPrompt.getEditor().getText();
        }while(folderPath.equals(""));
        System.out.println(computerName + "    " + folderPath);
        BorderPane bp = new BorderPane();
        HBox h = new HBox();

        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {

            }
        });
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {

            }
        });
        ListView leftList = new ListView();
        ListView rightList = new ListView();

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



    public static void main(String[] args) {
        launch(args);
    }
}
