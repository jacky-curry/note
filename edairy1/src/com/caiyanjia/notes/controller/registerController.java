package com.caiyanjia.notes.controller;


import com.caiyanjia.notes.bean.User;
import com.caiyanjia.notes.dao.Impl.userDaoImpl;
import com.caiyanjia.notes.util.JDBCUtils;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 注册类
 * 用于加载register界面以及界面的窗口监控
 */
public class registerController implements Initializable {

    public TextField rg_id;
    public TextField rg_password1;
    public TextField rg_password2;
    public TextField rg_name;
    public Button submit_b;
    public Label id_mistake;
    public Label password_mistake;
    public Label name_mistake;

    @FXML
    private Button r_login;

    private Stage stage = new Stage();


    //rutrn_login 返回主界面
    @FXML
    void return_login(ActionEvent event) {
        gotologin();


    }


    public void submit(ActionEvent actionEvent) throws Exception {


        //这里有bug，会出现短路问题，解决办法用&，|就可以了


        if(ifIdExist()&ifNameISNotNull()&IfPasswordEqual()){
            userDaoImpl userdaoimpl = new userDaoImpl();
            User user = new User();
            user.setId(rg_id.getText());
            user.setPassword(rg_password1.getText());
            user.setName(rg_name.getText());
            Connection conn  = JDBCUtils.getConnection();
            userdaoimpl.insert(conn,user);
            conn.close();

            alert(actionEvent);
        }


    }
//***********************************************************************

    /**
     * 判断Id是否存在
     * @return 数据库中存在即返回false，不存在返回true,如果没有输入，则也返回false
     */
    public Boolean ifIdExist() throws SQLException {

        if(!rg_id.getText().isEmpty()){
            id_mistake.setText("");
            Connection connection = JDBCUtils.getConnection();
            userDaoImpl userdaoimpl = new userDaoImpl();

            User getuser = userdaoimpl.judgeUser(connection,rg_id.getText() );
            connection.close();
            if(getuser != null){
                id_mistake.setText("Id has existed");
                return false;
            }else{
                return true;
            }
        }else{
            id_mistake.setText("The Id cannot be empty");
            return false;
        }

    }

    /**
     * 判断密码输入是否相同,且不为空
     * @return  相同则返回true，否则返回false
     */
    public Boolean IfPasswordEqual(){
        password_mistake.setText("");
        if(!rg_password1.getText().isEmpty()& !rg_password2.getText().isEmpty()){
            if(!rg_password1.equals(rg_password2)){
                return true;
            }else{
                password_mistake.setText("The two passwords were entered differently");
                return false;
            }
        }else{
            password_mistake.setText("The password cannot be empty");
            return false;
        }

    }

    /**
     * 判断name是否为空
     * @return 空返回false，否则为true
     */
    public Boolean ifNameISNotNull(){
        name_mistake.setText("");
        if(!rg_name.getText().isEmpty()){
            return true;
        }else{
            name_mistake.setText("The name cannot be empty");
            return  false;
        }
    }
    //*************************************************************

    public void gotologin(){
        try {
//            replaceSceneContent("com/caiyanjia/notes/view/lodin.fxml");
            new Lodin().start(new Stage());
            Stage primaryStage = (Stage) r_login.getScene().getWindow();
            primaryStage.hide();
        } catch (Exception ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public void gotomain(){
//        try {
//            MainController main = (MainController) replaceSceneContent("FXML_MAIN.fxml");
//            main.setApp(this);
//        } catch (Exception ex) {
//            Logger.getLogger(FXMLTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    public void userlogin(String account,String password){
//        if(Check.checkreturn(account,password)){
//            gotomain();
//        }
//    }



    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = registerController.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(registerController.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        return (Initializable) loader.getController();
    }

    public void alert(ActionEvent event) throws IOException {
        String info="registered successfully";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
        alert.setHeaderText(null);
        alert.setTitle("提示");
        alert.setOnCloseRequest(e -> {
            //如果点了Yes按钮，再进行跳转
            try {
                gotologin();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });
        alert.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
