package com.caiyanjia.notes.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Administrator extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Administrator.fxml"));
        System.out.println(root);
        primaryStage.setTitle("Administrator view");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }

}
