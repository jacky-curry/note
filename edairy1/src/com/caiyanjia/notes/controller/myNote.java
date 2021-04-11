package com.caiyanjia.notes.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;


public class myNote extends Application {




    TreeItem<String> rootNode;


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/myNote.fxml"));
            primaryStage.setTitle("myNote Window");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        rootNode.setExpanded(true);


    }

    public static void main(String[] args) {

        Application.launch(args);
    }


    public myNote(){
        this.rootNode = new TreeItem<>("MyCompany Human Resources");
    }

}
