package com.caiyanjia.notes.view;

import com.caiyanjia.notes.bean.Msg;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class NewNote extends Application {

    private static final Logger logger = Logger.getLogger(NewNote.class.getName());
    private final String user_id = Msg.getUser_id();

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {

        try {
            Parent root = FXMLLoader.load(getClass()
                    .getResource("../view/NewNote.fxml"));


            DateFormat df = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

            EventHandler<ActionEvent> eventHandler = e -> {
                primaryStage.setTitle(df.format(new Date()));
            };
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
            primaryStage.setTitle("My Note");
            primaryStage.setScene(new Scene(root));
            primaryStage.setUserData(user_id);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
