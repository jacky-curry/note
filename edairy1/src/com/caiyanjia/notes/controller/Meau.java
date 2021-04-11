package com.caiyanjia.notes.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Meau extends Application {

    public static void main(String[] args) {

        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/mainPage.fxml"));
            primaryStage.setTitle("meau Window");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();



            //将fxml的文件读取
//            FXMLLoader loader =  new FXMLLoader(getClass().getResource("mainPage.fxml"));
//            //
//            loader.setController(this);
//            loader.setLocation(getClass().getResource("mainPage.fxml"));
////            //将窗口加载进舞台
//            Parent root = loader.load();
////            thisStage.setScene(new Scene(loader.load()));
//            primaryStage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
