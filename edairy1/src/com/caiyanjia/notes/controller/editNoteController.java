package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.bean.Msg;
import com.caiyanjia.notes.entity.Note;
import com.caiyanjia.notes.service.noteServer;
import com.caiyanjia.notes.util.JDBCUtils;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class editNoteController implements Initializable {

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

    @FXML
    private ChoiceBox<String> choice;
    public StringProperty oldLabel;//用于保存原来的数据标签

    public  static AppModel model = new AppModel();
    noteServer noteserver = new noteServer();
    Connection connection = JDBCUtils.getConnection();

    private final String user_id = Msg.getUser_id();
    private static String title ;
    private static String content;
    private static String author;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        model.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                content = newValue;
                contentText.setText(content);
            }
        });

        model.titleProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                title = newValue;
                lableText.setText(title);
//                oldLabel.setValue(title);
            }
        });
        model.authorProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //2
                author = newValue;
                authorText.setText(author);

            }
        });

        //**********************
        model.authorProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                authorText.setText(author);
                lableText.setText(title);
                contentText.setText(content);
            }
        });
        model.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                authorText.setText(author);
                lableText.setText(title);
                contentText.setText(content);
            }
        });

        model.titleProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                authorText.setText(author);
                lableText.setText(title);
                contentText.setText(content);
            }
        });

        authorText.setText(author);
        lableText.setText(title);
        contentText.setText(content);

        ObservableList<String> list = FXCollections.observableArrayList(noteserver.searchGroup(connection,user_id));
        list.add("知识库");
        choice.setItems(list);
        choice.setTooltip(new Tooltip("选择笔记的分组"));

//        设置默认选中项//!!!!!!!!!!!!!!!!!!!!!!!!!!
        if(noteserver.getGroup(connection, title, user_id) != null){
            String  default_group =(String) noteserver.getGroup(connection, title, user_id);
            choice.setValue(default_group);
        }else{
            choice.setValue("知识库");
        }



        choice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String group = choice.getItems().get((Integer) observable.getValue());
                noteserver.setGroup(connection,lableText.getText(),group);
            }
        });


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


    /**
     * 用于保存数据
     */

    public void saveNote() throws SQLException {
        BooleanProperty  booleanProperty =  permission.selectedProperty();
//        Connection connection = JDBCUtils.getConnection();
//        ConnectDao connect = new ConnectDao();


        //将原来那条数据删除，换成新的数据,不能这么做啊，这样的话我的分组就没有了

        Note note = new Note();
        note.setAuthor(authorText.getText());
        note.setContent(contentText.getText());
        note.setLabel(lableText.getText());
        note.setDate(new Date());
        note.setPermission(!booleanProperty.get());

        noteserver.changNote(connection,note,oldLabel.getValue(),user_id);
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
                backTo();
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
    public void backTo() throws Exception {

        Stage primaryStage = (Stage) submit.getScene().getWindow();
        primaryStage.hide();
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



}
