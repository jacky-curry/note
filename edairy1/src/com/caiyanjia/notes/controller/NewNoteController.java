package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.dao.Dao.ConnectDao;
import com.caiyanjia.notes.dao.Impl.noteDaoImpl;
import com.caiyanjia.notes.util.JDBCUtils;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class NewNoteController implements Initializable {

    @FXML
    private Button submit;

    @FXML
    private TextField lableText;

    @FXML
    private TextArea contentText;

    @FXML
    private CheckBox permission;

    @FXML
    private TextField authorText;

    @FXML
    private TextField TimeShow;

    @FXML
    private Label reminder;

    @FXML
    private Button return_meau;

    public  static AppModel model = new AppModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //重写监听器，对界面中的text赋值
        model.textProperty().addListener((obs, oldText, newText) -> contentText.setText(newText));
        model.titleProperty().addListener((obs, oldText, newText) -> lableText.setText(newText));
        model.authorProperty().addListener((obs, oldText, newText) -> authorText.setText(newText));

    }





    @FXML
    void submitAll(ActionEvent event) throws Exception {
        if(ifAuthorNull() & ifContentNull() & ifLableNull() ){
            saveNote();
            alert(event);
        }else{
            reminder.setText("Something is empty");
        }
    }


    /**
     * 判断内容、标题、作者、权限是否为空
     * @return 空的话，返回false ，否则为true
     */
    public Boolean ifContentNull(){
        if(contentText.getText().isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public Boolean ifLableNull(){
        if(lableText.getText().isEmpty()){
            return false;
        }else{
            return  true;
        }
    }

    public Boolean ifAuthorNull(){
        if(authorText.getText().isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    public void ifPermissionNull(){
//        permission.selectedProperty().addListener(new ChangeListener<Boolean>() {
//
//            @Override
//            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//
//            }
//        });

    }






    /**
     * 用于保存数据
     */
    public void saveNote() throws SQLException {
        BooleanProperty  booleanProperty =  permission.selectedProperty();
        noteDaoImpl notedaoimpl = new noteDaoImpl();
        Connection connection = JDBCUtils.getConnection();
        ConnectDao connect = new ConnectDao();
        notedaoimpl.noteInsert(connection,lableText.getText(),authorText.getText(),contentText.getText(),
                new Date(),!booleanProperty.get(),connect.getId(connection));
        if(connection != null)
        connection.close();

    }

    /**
     * 提交数据后弹出提醒提交成功
     * @param event
     * @throws IOException
     */
    public void alert(ActionEvent event) throws Exception {
        String info="Submit successfully";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("Yes", ButtonBar.ButtonData.YES));
        alert.setHeaderText(null);
        alert.setTitle("reminder");
        alert.setOnCloseRequest(e -> {
            //如果点了Yes按钮，再进行跳转
            try {
                gotoMainPage();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });
        alert.show();
    }

    /**
     * 提交数据后的页面跳转
     * @throws Exception
     */
    public void gotoMainPage() throws Exception {
//        replaceSceneContent("mainPage.fxml");
        Stage stage = new Stage();
        new Meau().start(stage);
        Stage primaryStage = (Stage) submit.getScene().getWindow();
        primaryStage.hide();
    }

    @FXML
    void gotoMeau(ActionEvent event) throws Exception {
        gotoMainPage();
    }

    public static void setText(String text)
    {
        model.setText(text);
    }
    public static void setAuthor(String text)
    {
        model.setAuthor(text);
    }
    public static void setTitle(String text)
    {
        model.setTitle(text);
    }




    /**
     * 场景切换通用方法
     * @param fxml
     * @return
     * @throws Exception
     */
    private final Stage stage = new Stage();
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


}
