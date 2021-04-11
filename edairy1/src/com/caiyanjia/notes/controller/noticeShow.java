package com.caiyanjia.notes.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class noticeShow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("../view/notice_show.fxml"));
        primaryStage.setTitle("notice view");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }

    public static void main(String[] args) throws Exception {

        Application.launch(args);

    }


}
