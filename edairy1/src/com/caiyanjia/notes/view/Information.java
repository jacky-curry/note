package com.caiyanjia.notes.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Information extends Application {

    private String user_id;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("com/caiyanjia/notes/view/userInformation.fxml")));
        primaryStage.setTitle("User Information view");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public Information(){ }

    public Information(String user_id){
        this.user_id = user_id;
    }


    public static void main(String[] args) throws Exception {
        Application.launch(args);
    }

}
