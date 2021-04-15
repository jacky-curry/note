package com.caiyanjia.notes.controller;


import com.caiyanjia.notes.entity.User;
import com.caiyanjia.notes.service.registerServer;
import com.caiyanjia.notes.view.Lodin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    registerServer registerserver = new registerServer();

    //rutrn_login 返回主界面
    @FXML
    void return_login(ActionEvent event) {
        gotologin();


    }


    public void submit(ActionEvent actionEvent) throws Exception {



        //这里有bug，会出现短路问题，解决办法用&，|就可以了

        if(ifIdisNUll()&ifNameISNotNull()&IfPasswordEqual()){

            if(registerserver.ifIdExist(rg_id.getText())){

                User user = new User();
                user.setId(rg_id.getText());
                user.setPassword(rg_password1.getText());
                user.setName(rg_name.getText());
                if(registerserver.insertUser(user)){
                    alert(actionEvent);
                }
            }else{
                id_mistake.setText("Id has already existed");
            }


        }


    }

    public Boolean ifIdisNUll(){
        if(rg_id.getText()!=null){
            return true;

        }else{
            id_mistake.setText("The password cannot be empty");
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
            new Lodin().start(new Stage());
            Stage primaryStage = (Stage) r_login.getScene().getWindow();
            primaryStage.hide();
        } catch (Exception ex) {
            Logger.getLogger(registerController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
