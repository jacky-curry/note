package com.caiyanjia.notes.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewOnly extends Application {
    public static void main(String[] args) {

        Application.launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../view/ViewOnly.fxml"));
        primaryStage.setTitle("Note view");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
