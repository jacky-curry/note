package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.dao.Dao.ConnectDao;
import com.caiyanjia.notes.dao.Impl.administratorDaoImpl;
import com.caiyanjia.notes.dao.Impl.userDaoImpl;
import com.caiyanjia.notes.util.JDBCUtils;
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
import java.sql.SQLException;
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





    //构造器创建一个窗口
//    public loginController(){
        //创建一个舞台
//        thisStage = new Stage();
//        try {
//            //将fxml的文件读取
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("lodin.fxml"));
//            //
////            loader.setController(this);
//            //将窗口加载进舞台
//            thisStage.setScene(new Scene(loader.load()));
//
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }




    //登录按钮的作用
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


        ifLogin();
        //登录键的跳转
//        if(ifLogin()){
//
//            //将用户id保存到connect中
//            ConnectDao connectDao = new ConnectDao();
//            Connection conn = JDBCUtils.getConnection();
//            connectDao.insert(conn,id.getText());
//            if(conn!= null)
//            conn.close();
//
//
//            //跳转页面
//            tomeau();
//
//        }else {
//            tip.setText("id or password isn't correct");
//
//        }
    }

    /**
     * 判断是否登录成功的方法
     * @return  登录成功返回true，否则返回false
     *
     */
    public Boolean ifLogin() throws Exception {
        Connection connection = JDBCUtils.getConnection();

        userDaoImpl userDao = new userDaoImpl();
        administratorDaoImpl administratordao = new administratorDaoImpl();
        if(administratordao.ifLodin(connection,id.getText(),password.getText())){
            //登录成功跳转到管理员页面
            new Administrator().start(new Stage());
            return true;
        }

        if(userDao.getLodinUser(connection,id.getText(),password.getText()) != null){
            ConnectDao connectDao = new ConnectDao();
            Connection conn = JDBCUtils.getConnection();
            connectDao.insert(conn,id.getText());
            if(conn!= null)
                conn.close();


            //跳转页面
            tomeau();

            return true;
        }else{
            tip.setText("id or password isn't correct");
            return false;
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
                new Meau().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//    public void setApp(registerController application){
//        this.application = application;
//    }


//    @FXML
//    private void OUT_M(ActionEvent event) {
//        application.useroutmain();
//    }


}
