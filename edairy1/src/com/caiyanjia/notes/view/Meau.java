package com.caiyanjia.notes.view;

import com.caiyanjia.notes.bean.Msg;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Meau extends Application {

    private String user_id;

    public static void main(String[] args) {

        Application.launch(args);
    }

    public Meau(String user_id){
        Msg.setUser_id(user_id);
        this.user_id = user_id;
    }
    public  Meau(){

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/mainPage.fxml"));
            primaryStage.setTitle("尊敬的" + user_id  + "用户你好");
            primaryStage.setScene(new Scene(root));
            primaryStage.setUserData(user_id);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
