package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.bean.Note;
import com.caiyanjia.notes.dao.Dao.ConnectDao;
import com.caiyanjia.notes.dao.Impl.administratorDaoImpl;
import com.caiyanjia.notes.dao.Impl.groupDaoImpl;
import com.caiyanjia.notes.dao.Impl.noteDaoImpl;
import com.caiyanjia.notes.util.JDBCUtils;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.swing.text.View;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class userNoteController implements Initializable {
    @FXML
    private TableColumn<noteDate, String> edit;

    @FXML
    private TableColumn<noteDate, Integer> like;

    @FXML
    private TableColumn<noteDate, String> author;

    @FXML
    private TableColumn<noteDate, String> post_date;

    @FXML
    private TableView myTable;

    @FXML
    private TableColumn<noteDate, String> title;

    @FXML
    private TableColumn<?, ?> delete;

    @FXML
    private TableColumn<noteDate, String> content;

    @FXML
    private Button back;

    @FXML
    private VBox box;


    @FXML
    void eb13a1(ActionEvent event) {

    }

    @FXML
    void backto(ActionEvent event) {
        back.getScene().getWindow().hide();
    }

    @FXML
    void refresh(ActionEvent event) {

    }

    final ObservableList<noteDate> date
            = FXCollections.observableArrayList();

    TreeItem<String> root = new TreeItem<String>("根目录");
    TreeView<String> treeView = new TreeView<String>(root);
    noteDaoImpl notedaoimpl = new noteDaoImpl();
    Connection conn = JDBCUtils.getConnection();
    ConnectDao connectdao = new ConnectDao();
    administratorDaoImpl administratorDao = new administratorDaoImpl();
    String user_id = administratorDao.get_user(conn);//这个根据上一个界面获取的user_id决定
    groupDaoImpl groupdaoimpl = new groupDaoImpl();
    final String[] oldName = {null};
    List<Note> noteList = notedaoimpl.getIdNote(conn, user_id);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        author.setCellValueFactory(new PropertyValueFactory<noteDate, String>("author"));
        title.setCellValueFactory(new PropertyValueFactory<noteDate, String>("title"));
        post_date.setCellValueFactory(new PropertyValueFactory<noteDate, String>("post_date"));
        content.setCellValueFactory(new PropertyValueFactory<noteDate, String>("content"));
        like.setCellValueFactory(new PropertyValueFactory<noteDate, Integer>("like"));

        for (int i = 0; i < noteList.size(); i++) {
            date.add(i, getNoteDate(noteList.get(i)));
        }

        myTable.setItems(date);


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
                                noteDate noteLoad = (noteDate) date.get(getIndex());

                                //获取该行信息，
                                try {
                                    new ViewOnly().start(new Stage());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                ViewOnlyController.setContentText(noteLoad.getContent());
                                ViewOnlyController.setAuthorText(noteLoad.getAuthor());
                                ViewOnlyController.setlabelText(noteLoad.getTitle());
                                ViewOnlyController.setPost_date(noteLoad.getPost_date());
                                ViewOnlyController.setLikeText(noteLoad.getLike());

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

        //****************************以下是分组*****************



        ContextMenu menu = new ContextMenu();
        root.setExpanded(true);
        box.getChildren().add(treeView);

       //设置右键查看分组中笔记

        MenuItem searchItem = new MenuItem("查看分组中笔记");
        searchItem.setOnAction(e -> {
            System.out.println("进入查看");
            //新建一个数据源，包含当前组的所有数组，并且添加到tableview中
            final ObservableList<noteDate> groupDate
                    = FXCollections.observableArrayList();

            List<Note> notefromgroup = notedaoimpl.getNoteFromGroup(conn,oldName[0],user_id);
//            List<noteDate> groupList = new ArrayList<>();

            for (int i = 0; i < notefromgroup.size(); i++) {
//                date.clear();
                //将小组中的数据加载进来，并显式
                date.add(i, getNoteDate(notefromgroup.get(i)));
                date.remove(notefromgroup.size(),date.size());
            }
//            myTable.setItems(groupDate);


            myTable.refresh();
        });

        //就是创建了ContextMenu，然后往Menu中增加Item菜单项，同时实现Item菜单项的事件。
        menu.getItems().add(searchItem);

        treeView.setContextMenu(menu);
        treeView.setEditable(true);//设置成可编辑
        treeView.setCellFactory(TextFieldTreeCell.forTreeView(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        }));


        //每次修改名字后，都会对数据库里的group表进行修改


        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observableValue, TreeItem<String> oldItem, TreeItem<String> newItem) {
                oldName[0] = newItem.getValue();
                System.out.println(oldName[0]);
            }
        });

        //在改变分组名字后，对group中的数据进行修改,同时应该得对note中得分组也修改
        root.addEventHandler(TreeItem.<String>valueChangedEvent(), new EventHandler<TreeItem.TreeModificationEvent<String>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<String> event) {
                groupDaoImpl groupdaoimpl = new groupDaoImpl();
                groupdaoimpl.changName(conn,oldName[0],event.getNewValue());


            }
        });



        //设置分组中的笔记
        initGroup();
    }

    public void initGroup(){
        //设置默认数据中已有的分组
        List<Note> labelList = new ArrayList<>();

        List<String> groupList = groupdaoimpl.searchGroup(conn,user_id);
        labelList =  notedaoimpl.getNoteFromGroup(conn,"0",user_id);
        for( int i = 0;i < labelList.size();i++){
            TreeItem<String> node = new TreeItem<String>(labelList.get(i).getLabel());
            root.getChildren().add(i,node);
            Node rootIcon = new ImageView(
                    new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("file.png")));
            node.setGraphic(rootIcon);
        }


        for (int i = 0; i < groupList.size(); i++) {


            TreeItem<String> parent= new TreeItem<String>(groupList.get(i));
            root.getChildren().add(parent);

            String parent_node  = parent.getValue();

            labelList = notedaoimpl.getNoteFromGroup(conn,parent_node,user_id);

            for(int j = 0;j < labelList.size() ; j++){

                TreeItem<String> node = new TreeItem<String>(labelList.get(j).getLabel());
                parent.getChildren().add(j,node);
                Node rootIcon = new ImageView(
                        new Image(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("file.png"))));
                node.setGraphic(rootIcon);

            }

        }
    }

    public noteDate getNoteDate(Note note) {

        noteDate notedate = new noteDate();
        notedate.setTitle(note.getLabel());
        notedate.setAuthor(note.getAuthor());
        notedate.setLike(String.valueOf(note.getLike()));
        notedate.setContent(note.getContent());
        notedate.setPost_date(String.valueOf(note.getDate()));

        return notedate;


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


