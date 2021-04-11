package com.caiyanjia.notes.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewNote extends Application {

    private static final Logger logger = Logger.getLogger(NewNote.class.getName());
    private Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("../view/NewNote.fxml"));

//            replaceSceneContent("D:\\java\\edairy1\\src\\com\\caiyanjia\\notes\\controller\\lodin.fxml");

            DateFormat df = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

            EventHandler<ActionEvent> eventHandler = e -> {
                primaryStage.setTitle(df.format(new Date()));
            };
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
            primaryStage.setTitle("My Note");
            primaryStage.setScene(new Scene(root));



            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }








    /**
     * 替换场景
     * @param fxml
     * @return
     * @throws Exception
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        InputStream in = NewNote.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(NewNote.class.getResource(fxml));
        try {
            AnchorPane page = (AnchorPane) loader.load(in);
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.sizeToScene();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "页面加载异常！");
        } finally {
            if(in != null)
            in.close();
        }
        return (Initializable) loader.getController();
    }


}
