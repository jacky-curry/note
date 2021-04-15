package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.bean.Msg;
import com.caiyanjia.notes.entity.User;
import com.caiyanjia.notes.service.userServer;
import com.caiyanjia.notes.util.JDBCUtils;
import com.caiyanjia.notes.view.Meau;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class informationController implements Initializable {
    @FXML
    private TextField password;

    @FXML
    private Button submitButton;

    @FXML
    private TextField name;

    @FXML
    private Button backButton;

    @FXML
    private TextField id;

    @FXML
    private Label nameTip;

    @FXML
    private Label idTip;

    @FXML
    private Label psTip;

    static String oldid;//用于保存修改前的id

    @FXML
    void back(ActionEvent event) throws Exception {


        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        new Meau().start(stage);

    }
    private final String user_id = Msg.getUser_id();
    userServer userserver = new userServer();
    Connection connection = JDBCUtils.getConnection();

    //将修改后的数据提交至数据库，
    @FXML
    void submit(ActionEvent event) throws Exception {
        User user = new User();
        if (ifidNUll() & ifNameNUll() & ifPasswordNUll()) {

            user.setId(id.getText());
            user.setName(name.getText());
            user.setPassword(password.getText());
            Connection conn = JDBCUtils.getConnection();

            userserver.savaImformation(conn,user);
            if (conn != null) {
                conn.close();
            }
            Stage primaryStage = (Stage) backButton.getScene().getWindow();
            primaryStage.hide();
            Stage stage = new Stage();
            new Meau().start(stage);


        }
    }


    private Boolean ifidNUll(){
        if(id.getText().isEmpty()){
            idTip.setText("The id cannot be empty");
            return  false;
        }else{
            idTip.setText("");
            return true;
        }
    }
    private Boolean ifPasswordNUll(){
        if(password.getText().isEmpty()){
            psTip.setText("The password cannot be empty");
            return  false;
        }else{
            psTip.setText("");
            return true;
        }
    }
    private Boolean ifNameNUll(){
        if(name.getText().isEmpty()){
            nameTip.setText("The nameTip cannot be empty");
            return  false;
        }else{
            nameTip.setText("");
            return true;
        }
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {



        User user = userserver.getUser(connection,user_id);
        id.setEditable(true);
        password.setEditable(true);
        name.setEditable(true);

        String user_id = user.getId();
        String pw = user.getPassword();
        String n = user.getName();

        id.setText(user_id);
        password.setText(pw);
        name.setText(n);
        id.setEditable(false);
    }
}
