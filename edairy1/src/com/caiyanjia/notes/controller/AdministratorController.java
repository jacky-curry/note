package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.bean.Connect;
import com.caiyanjia.notes.bean.Note;
import com.caiyanjia.notes.bean.User;
import com.caiyanjia.notes.dao.Dao.noticeDao;
import com.caiyanjia.notes.dao.Impl.administratorDaoImpl;
import com.caiyanjia.notes.dao.Impl.noticeDaoImpl;
import com.caiyanjia.notes.dao.Impl.userDaoImpl;
import com.caiyanjia.notes.util.JDBCUtils;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.List;
import java.util.ResourceBundle;

public class AdministratorController implements Initializable {

    @FXML
    private Button notice_submit_button;

    @FXML
    private TableView<userDate> user_date;

    @FXML
    private TextArea notice;
    @FXML
    private TableColumn<userDate, String> edit;

    @FXML
    private TableColumn<userDate, String> name;

    @FXML
    private TableColumn<userDate, String> user_id;

    @FXML
    private TableColumn<userDate, String> blackList;




    Connection conn = JDBCUtils.getConnection();
    noticeDaoImpl noticeDao = new noticeDaoImpl();

    public AdministratorController() {
    }

    @FXML
    void notice_submit(ActionEvent event) {

        if(notice.getText() != null){

            noticeDao.insert(conn,notice.getText());

        }

    }


    userDate userdate = new userDate();
    userDaoImpl userdaoimpl = new userDaoImpl();
    List userList = userdaoimpl.getAllUser(conn);
    final ObservableList<userDate> date = FXCollections.observableArrayList();
    administratorDaoImpl adimpl = new administratorDaoImpl();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        user_date.setEditable(true);
        notice.setText(noticeDao.getNotice(conn));
        ObservableList<TableColumn<userDate, ?>> observableList = user_date.getColumns();
        observableList.get(0).setCellValueFactory(new PropertyValueFactory("id"));
        observableList.get(1).setCellValueFactory(new PropertyValueFactory("name"));
        observableList.get(3).setCellValueFactory(new PropertyValueFactory("black"));
        user_date.setItems(date);
//
        for (int i = 0; i < userList.size(); i++) {
            userdate = userdate.userChange((User) userList.get(i));
            date.add(userdate);
        }






        blackList.setCellFactory((col)-> {
                    TableCell<userDate, String > cell = new TableCell<userDate, String >() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                                super.updateItem(item,empty);
                                CheckBox checkbox = new CheckBox("设置为黑名单");

                            checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                                public void changed(ObservableValue<? extends Boolean> ov,
                                                    Boolean old_val, Boolean new_val) {
                                    //得获取该行user_id并且传入checkbox的值
                                    userDate currentUser = date.get(getIndex());
                                    adimpl.blackList(conn,new_val,currentUser.getId());


                                    System.out.println(user_date.getSelectionModel().getSelectedCells());
                                }
                            });

                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(checkbox);
                            }

                        }
                    };
                    return cell;
                }
                );



        edit.setCellFactory((col)->{
                    TableCell<userDate, String> cell = new TableCell<userDate, String>(){
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button1 = new Button("查看详情");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");

                            button1.setOnMouseClicked((col) -> {

                                //获取list列表中的位置，进而获取列表对应的信息数据
                                userDate currentUser = date.get(getIndex());
                                //需要获得一个用户的user_id 并根据该用户去查找笔记还有分组

                                adimpl.set_user(conn,currentUser.getId());


                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/userNoteShow.fxml"));
                                //只能查看，不能修改
                                //获取该行信息，
                                try {
                                    AnchorPane login = (AnchorPane) loader.load();
                                   replaceSceneContent("../view/userNoteShow.fxml");
                                } catch (Exception e) {
                                    e.printStackTrace();
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
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        return (Initializable) loader.getController();
    }

    }

