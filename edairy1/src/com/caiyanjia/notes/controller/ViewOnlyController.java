package com.caiyanjia.notes.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewOnlyController implements Initializable{



    @FXML
    private TextField lableText;

    @FXML
    private TextField post_date;

    @FXML
    private TextArea contentText;

    @FXML
    private TextField authorText;

    @FXML
    private Button return_meau;

    @FXML
    private TextField likeText;

    //定义一个静态类，在两个controller中传递数据，有点不懂原理
    public  static AppModel model = new AppModel();


    public static void setContentText(String text) {
        model.setText(text);
    }

    public static void setPost_date(String post_date) {
        model.setDate(post_date);
    }

    public static void setlabelText(String  label) {
        model.setTitle(label);
    }

    public static void setAuthorText(String authorText) {
        model.setAuthor(authorText);
    }

    public static void setLikeText(String likeText) {
        model.setLike(likeText);
    }

    public ViewOnlyController(){


    }

    public static void setText(String text)
    {
        model.setText(text);
    }


    @FXML
    void gotoMeau(ActionEvent event) throws Exception {
        gotoMainPage();
    }


    /**
     * 提交数据后的页面跳转
     * @throws Exception
     */
    public void gotoMainPage() throws Exception {

        Stage primaryStage = (Stage) return_meau.getScene().getWindow();
        primaryStage.hide();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //重写监听器，对界面中的text赋值
        model.textProperty().addListener((obs, oldText, newText) -> contentText.setText(newText));
        model.titleProperty().addListener((obs, oldText, newText) -> lableText.setText(newText));
        model.likeProperty().addListener((obs, oldText, newText) -> likeText.setText(newText));
        model.dateProperty().addListener((obs, oldText, newText) -> post_date.setText(newText));
        model.authorProperty().addListener((obs, oldText, newText) -> authorText.setText(newText));


        lableText.setEditable(false);
        contentText.setEditable(false);
        likeText.setEditable(false);
        post_date.setEditable(false);
        authorText.setEditable(false);
    }
}
