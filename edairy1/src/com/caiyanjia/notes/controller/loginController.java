package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.service.loginServer;
import com.caiyanjia.notes.service.noteServer;
import com.caiyanjia.notes.util.JDBCUtils;
import com.caiyanjia.notes.view.Meau;
import com.caiyanjia.notes.view.Register1;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class loginController implements Initializable{
    @FXML
    private Button login;
    @FXML
    private Button register;
    @FXML
    private PasswordField password;

    @FXML
    private TextField id;
    @FXML
            private Label id_mistake;
    @FXML
            private Label pw_mistake;

    @FXML
    private Label tip;


    @FXML
    public void onButtonClick(ActionEvent event) throws Exception {
        if(id.getText().isEmpty()){
            id_mistake.setText("id is empty");

        }else{
            id_mistake.setText("");
        }
        if(password.getText().isEmpty()){
            pw_mistake.setText("password is empty");
        }else{
            pw_mistake.setText("");
        }
        loginServer loginserver = new loginServer();

        if(loginserver.ifLogin(id.getText(),password.getText())){
            tomeau();
        }else{
            tip.setText("账号或密码错误");
        }
    }




    /**
     * 注册按钮事件
     *
     */
    //转跳注册界面
    @FXML
    public void toSign() {
        Platform.runLater(()->{
            //获取按钮所在的窗口
            Stage primaryStage = (Stage) register.getScene().getWindow();
            //当前窗口隐藏
            primaryStage.hide();
            //加载注册窗口
            try {
                new Register1().start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 登录按钮跳转到主界面
     *
     */

    //转跳主界面
    public void tomeau() {
        Platform.runLater(()->{
            //获取按钮所在的窗口
            Stage primaryStage = (Stage) login.getScene().getWindow();
            //当前窗口隐藏
            primaryStage.hide();
            //加载注册窗口
            try {
                new Meau(id.getText()).start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noteServer noteserver = new noteServer();
        Connection conn = JDBCUtils.getConnection();
        if(noteserver.getroot(conn,"知识库")!=null){
            noteserver.insert(conn,"知识库","0");
        }
    }


}
