package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.bean.Msg;
import com.caiyanjia.notes.entity.Note;
import com.caiyanjia.notes.service.noteServer;
import com.caiyanjia.notes.service.userServer;
import com.caiyanjia.notes.util.JDBCUtils;
import com.caiyanjia.notes.view.Information;
import com.caiyanjia.notes.view.NewNote;
import com.caiyanjia.notes.view.ViewOnly;
import com.caiyanjia.notes.view.myNote;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class meauController extends ClassLoader implements Initializable {

    @FXML
    private Button myInformation;


    @FXML
    private TableColumn<noteDate, String> edit;

    @FXML
    private TableColumn<noteDate, String> author;

    @FXML
    private TableColumn<noteDate, String> post_date;

    @FXML
    private Button createNote;

    @FXML
    private TableView<noteDate> myTable;

    @FXML
    private TableColumn<noteDate, String> title;

    @FXML
    private Button MyNote;

    @FXML
    private TableColumn<noteDate, String> content;

    @FXML
    private Button notice;

    @FXML
    private Label Tip;
    @FXML
    private Button search_button;

    @FXML
            private TextField Find;
    @FXML
    private Button refresh_button;


    @FXML
    private Label black;

    @FXML
    private TableColumn<noteDate,String> like_button;

    @FXML
    private TableColumn<noteDate, Integer> like;


    private final String user_id = Msg.getUser_id();//做完登录信息传过来的user_id，赋值的话只能在监听器里面



    Connection connection = JDBCUtils.getConnection();

    final ObservableList<noteDate> date = FXCollections.observableArrayList();

    public meauController() {

    }

    @FXML
    void myInformationShow(ActionEvent event) throws Exception {

       Stage stage = (Stage) myInformation.getScene().getWindow();
        stage.hide();
        Stage primaryStage  = new Stage();
        new Information(user_id).start(primaryStage);
    }

    @FXML
    void notice_show(ActionEvent event) throws Exception {
        new noticeShow().start(new Stage());
    }



    //用于数据转换成可在table view上展示的数据
    public noteDate noteChange(Note n) {
        noteDate notedate = new noteDate();
        notedate.setTitle(n.getLabel());
        notedate.setAuthor(n.getAuthor());
        notedate.setLike(String.valueOf(n.getLike()));
        notedate.setContent(n.getContent());
        notedate.setPost_date(String.valueOf(n.getDate()));

        return notedate;
    }


    noteServer noteserver = new noteServer();
    userServer userserver = new userServer();

    List noteList = noteserver.getAllnote(connection);

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //用于存储数据，要展现的数据添加到这里，作为数据源

        //设置表中的列对应的变量
        title.setCellValueFactory(new PropertyValueFactory<noteDate, String>("title"));
        author.setCellValueFactory(new PropertyValueFactory<noteDate, String>("author"));
        post_date.setCellValueFactory(new PropertyValueFactory<noteDate, String>("post_date"));
        like.setCellValueFactory(new PropertyValueFactory<noteDate, Integer>("like"));

        //添加数据源
        myTable.setItems(date);

        for (int i = 0; i < noteList.size(); i++) {
            noteDate n = noteChange((Note) noteList.get(i));//将note中的数据读进notedate中
            date.add(n);
        }


        //添加按钮进列表
        edit.setCellFactory((col) -> {

                    //UserLoad换成你自己的实体名称
                    TableCell<noteDate, String> cell = new TableCell<noteDate, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button1 = new Button("查看详情");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");

                            button1.setOnMouseClicked((col) -> {

                                //获取list列表中的位置，进而获取列表对应的信息数据
                                noteDate noteLoad = date.get(getIndex());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ViewOnly.fxml"));
                                //只能查看，不能修改
                                //获取该行信息，
                                try {
                                    AnchorPane login = (AnchorPane) loader.load();
                                    new ViewOnly().start(new Stage());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                ViewOnlyController.model.setText(noteLoad.getContent());
                                ViewOnlyController.model.setAuthor(noteLoad.getAuthor());
                                ViewOnlyController.model.setLike(noteLoad.getLike());
                                ViewOnlyController.model.setDate(noteLoad.getPost_date());
                                ViewOnlyController.model.setTitle(noteLoad.getTitle());


                            });

                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button1);
                            }
                        }
                    };
                    return cell;
                }
        );

        like_button.setCellFactory((col) -> {

                    TableCell<noteDate, String> cell = new TableCell<noteDate, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            ToggleButton button1 = new RadioButton("点赞");
                            button1.getStyleClass().remove("radio-button");
                            button1.getStyleClass().add("toggle-button");
                            button1.setOnMouseClicked((col) -> {
                                noteDate noteLoad = date.get(getIndex());


                                if(button1.isSelected()){
                                    if(!noteserver.ifThumbUp(connection,user_id,noteLoad.getTitle())) {

                                        if(noteserver.thumbUp(connection,user_id,noteLoad.getTitle())){
                                            noteserver.likeChange(1,noteLoad.getTitle());
                                            System.out.println("点赞成功");
                                            refresh(new ActionEvent());
                                        }
                                    }else{
                                        System.out.println("已经点赞，点赞失败");
                                    }

                                }else{
                                    if(noteserver.cancelThumbUp(connection,user_id,noteLoad.getTitle())){

                                        noteserver.likeChange(0,noteLoad.getTitle());
                                        refresh(new ActionEvent());
                                        System.out.println("取消点赞成功");
                                    }
                                }


                            });

                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {

                                this.setGraphic(button1);
                            }
                        }
                    };
                    return cell;
                }
        );

    }





//        System.out.println(user_id);


    @FXML
    void gotoNewNote(ActionEvent event) throws Exception {

        if(!userserver.ifIsBlackList(user_id)){

            Stage stage = new Stage();
            new NewNote().start(stage);
            Stage primaryStage = (Stage) createNote.getScene().getWindow();
            primaryStage.hide();
        }else{
            black.setText("黑名单用户，不可发布笔记");
        }
    }


    /**
     * 注销程序
     * @param event
     */
    @FXML
    void exit(ActionEvent event) throws SQLException {
        Stage primaryStage = (Stage) createNote.getScene().getWindow();
        primaryStage.hide();
    }



    private void replaceSceneContent(String fxml) throws Exception {
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
        Stage stage = new Stage();
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        loader.getController();
    }


    public void gotoMyNote() {
        Platform.runLater(()->{
            //获取按钮所在的窗口
            Stage primaryStage = (Stage) MyNote.getScene().getWindow();
            //当前窗口隐藏
            primaryStage.hide();
            //加载myNote
            try {
                new myNote(user_id).start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void search(String text){
        //两种查询，一种根据笔记名称查询，一种根据作者查询
        //第一种根据笔记名称查询

            System.out.println(text);
            List<Note> noteList = new ArrayList<>();
            noteList  = noteserver.searchByLabel(connection,text);
            if (noteList.size() != 0){
                Tip.setText("");
                for(int i = 0;i<noteList.size();i++){

                    date.remove(0,date.size());//为什么这里是0
                    date.add(i,noteChange(noteList.get(i)));
                }

            }else {
                Tip.setText("");
                noteList = noteserver.searchByAuthor(connection, text);
                System.out.println(noteList);
                if (noteList.size() != 0) {
                    for (int i = 0; i < noteList.size(); i++) {

                        date.remove(1, date.size());//为什么这里是1
                        date.add(i, noteChange(noteList.get(i)));
                    }
                }else {
                    Tip.setText("Not such field");
                }
            }



    }

    @FXML
    void searchNote(ActionEvent event) {
        search(Find.getText());
    }

    @FXML
    void refresh(ActionEvent event) {
        date.clear();
        for (int i = 0; i < noteList.size(); i++) {
            noteList = noteserver.getAllnote(connection);
            noteDate n = noteChange((Note) noteList.get(i));//将note中的数据读进notedate中

            date.add(n);
        }

    }


}




