package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.bean.Connect;
import com.caiyanjia.notes.bean.User;
import com.caiyanjia.notes.dao.Dao.ConnectDao;
import com.caiyanjia.notes.dao.Impl.userDaoImpl;
import com.caiyanjia.notes.util.JDBCUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ConcurrentModificationException;
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


    //将修改后的数据提交至数据库，
    @FXML
    void submit(ActionEvent event) throws Exception {

        userDaoImpl userdaoimpl = new userDaoImpl();
        User user = new User();
        if (ifidNUll() & ifNameNUll() & ifPasswordNUll()) {

            user.setId(id.getText());
            user.setName(name.getText());
            user.setPassword(password.getText());
            Connection conn = JDBCUtils.getConnection();

            userdaoimpl.update(conn, user);
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
        userDaoImpl userdaoimpl = new userDaoImpl();
        Connection connection = JDBCUtils.getConnection();

        ConnectDao connect = new ConnectDao();

        User user = userdaoimpl.judgeUser(connection,connect.getId(connection));

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
