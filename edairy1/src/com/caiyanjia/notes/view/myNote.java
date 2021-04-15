package com.caiyanjia.notes.view;

import com.caiyanjia.notes.bean.Msg;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;


public class myNote extends Application {



    private String user_id = Msg.getUser_id();
//    public  static Msg model = new Msg();
    TreeItem<String> rootNode;



    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/myNote.fxml"));
            primaryStage.setTitle("myNote Window");
            primaryStage.setScene(new Scene(root));
            primaryStage.setUserData(user_id);

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        rootNode.setExpanded(true);


    }

    public static void main(String[] args) {

        Application.launch(args);
    }

    public myNote(String user_id){
        this.rootNode = new TreeItem<>("MyCompany Human Resources");
        this.user_id = user_id;
    }

    public myNote(){
        this.rootNode = new TreeItem<>("MyCompany Human Resources");
    }

}
