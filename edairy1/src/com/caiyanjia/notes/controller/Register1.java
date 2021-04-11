package com.caiyanjia.notes.controller;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Register1 extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("com/caiyanjia/notes/view/Register.fxml")));
        primaryStage.setTitle("DangDang's Management System--Register interface");
        primaryStage.setScene(new Scene(root, 580, 400));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


}
