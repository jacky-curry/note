package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.bean.Group;
import com.caiyanjia.notes.bean.Note;
import com.caiyanjia.notes.dao.Dao.ConnectDao;
import com.caiyanjia.notes.dao.Impl.groupDaoImpl;
import com.caiyanjia.notes.dao.Impl.noteDaoImpl;
import com.caiyanjia.notes.util.JDBCUtils;
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

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class myNoteController implements Initializable {

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
    private TableColumn<noteDate, String> delete;

    @FXML
    private TableColumn<noteDate, String> content;

    @FXML
    private Button back;

    @FXML
    private VBox box;



    @FXML
    void backto(ActionEvent event) throws Exception {
        Stage stage = new Stage();
        new Meau().start(stage);
        Stage primaryStage = (Stage) back.getScene().getWindow();
        primaryStage.hide();
    }

    @FXML
    void refresh(ActionEvent event) {//显式所有分组的数据
        if(myTable.getItems().size() != noteList.size()){
        for (int i = 0; i < noteList.size(); i++) {
            date.add(i, getNoteDate(noteList.get(i)));
        }

            myTable.setItems(date);
        }
    }



    final ObservableList<noteDate> date
            = FXCollections.observableArrayList();

    TreeItem<String> root = new TreeItem<String>("知识库");
    TreeView<String> treeView = new TreeView<String>(root);
    noteDaoImpl notedaoimpl = new noteDaoImpl();
    Connection conn = JDBCUtils.getConnection();
    ConnectDao connectdao = new ConnectDao();
    String user_id = connectdao.getId(conn);
    groupDaoImpl groupdaoimpl = new groupDaoImpl();
    final String[] oldName = {null};
    List<Note> noteList = notedaoimpl.getIdNote(conn, connectdao.getId(conn));

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

        delete.setCellFactory((col) -> {

                    //UserLoad换成你自己的实体名称
                    TableCell<noteDate, String> cell = new TableCell<noteDate, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            Button button1 = new Button("删除");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");

                            button1.setOnMouseClicked((col) -> {

                                //获取list列表中的位置，进而获取列表对应的信息数据
                                noteDate noteLoad = (noteDate) date.get(getIndex());
                                notedaoimpl.noteDelete(conn,noteLoad.getTitle(),user_id);
                                date.remove(date.get(getIndex()));

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
//
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

                                System.out.println(noteLoad);
                                System.out.println(noteLoad.getAuthor());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/editNote.fxml"));
                                //获取该行信息，
                                try {
                                    AnchorPane login = (AnchorPane) loader.load();
                                    replaceSceneContent("../view/editNote.fxml");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                editNoteController.setText(noteLoad.getContent());
                                editNoteController.setAuthor(noteLoad.getAuthor());
                                editNoteController.setTitle(noteLoad.getTitle());

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


        root.setExpanded(true);
        box.getChildren().add(treeView);



        //***设置右键内容
        ContextMenu menu = new ContextMenu();
        MenuItem addItem = new MenuItem("新建分组");
        addItem.setOnAction(e -> {
            try {
                createItem();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });


        MenuItem deleteItem = new MenuItem("删除分组");
        deleteItem.setOnAction(e -> {
                //在group表中找到当前的分组，并删除，之后返回是否删除成功
                    oldName[0] = treeView.getSelectionModel().getSelectedItems().get(0).getValue();
                    if(groupdaoimpl.deleteGroup(conn,oldName[0],user_id)) {
                        //如果分组删除成功的话，在note表里的该组设置为默认0
                        notedaoimpl.deleteGroup(conn, oldName[0], user_id);
                        System.out.println("删除分组成功");
                        //没法刷新啊

                    }



        });

        MenuItem searchItem = new MenuItem("查看分组中笔记");
        searchItem.setOnAction(e -> {
            System.out.println("进入查看");
            //新建一个数据源，包含当前组的所有数组，并且添加到tableview中
            final ObservableList<noteDate> groupDate
                    = FXCollections.observableArrayList();

            List<Note> notefromgroup = notedaoimpl.getNoteFromGroup(conn,oldName[0],user_id);

            for (int i = 0; i < notefromgroup.size(); i++) {
//                date.clear();
                //将小组中的数据加载进来，并显式
                date.add(i, getNoteDate(notefromgroup.get(i)));
                date.remove(notefromgroup.size(),date.size());
            }

            myTable.refresh();
        });

        //就是创建了ContextMenu，然后往Menu中增加Item菜单项，同时实现Item菜单项的事件。
        menu.getItems().add(addItem);
        menu.getItems().add(deleteItem);
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

    //get
//    private final Node rootIcon = new ImageView(
//            new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("file.png")));


    /**
     * 实现数据库和treview的新建功能
     * @throws InterruptedException
     */
    public void createItem() throws InterruptedException {

            TreeItem<String> newItem = new TreeItem<String>();
//        Node rootIcon = new ImageView(
//                new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("file.png")));
//            newItem.setGraphic(rootIcon);
            newItem.setValue("Item " + treeView.getExpandedItemCount());

            //加载进数据库里
        ConnectDao connect = new ConnectDao();
        Group group = new Group();
        groupDaoImpl groupdaoimpl = new groupDaoImpl();
        Connection  connection = JDBCUtils.getConnection();
        group.setUser_id(connect.getId(connection));


        String line = String.valueOf(treeView.getExpandedItemCount());
        group.setName("Item " + line);

        groupdaoimpl.insertGroup(connection,group);

            treeView.getRoot().getChildren().add(newItem);

            treeView.requestFocus();

            treeView.getSelectionModel().select(newItem);

            treeView.edit(newItem);
    }


    private Stage stage = new Stage();

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


    /**
     * @param
     * @return 将noteDate类型的数组返回
     */
    public noteDate getNoteDate(Note note) {

        noteDate notedate = new noteDate();
        notedate.setTitle(note.getLabel());
        notedate.setAuthor(note.getAuthor());
        notedate.setLike(String.valueOf(note.getLike()));
        notedate.setContent(note.getContent());
        notedate.setPost_date(String.valueOf(note.getDate()));

        return notedate;

    }


    public TableColumn<?, ?> getEdit() {
        return edit;
    }

    public void setEdit(TableColumn<noteDate, String> edit) {
        this.edit = edit;
    }

    public TableColumn<?, ?> getLike() {
        return like;
    }

    public void setLike(TableColumn<noteDate, Integer> like) {
        this.like = like;
    }

    public TableColumn<?, ?> getAuthor() {
        return author;
    }

    public void setAuthor(TableColumn<noteDate, String> author) {
        this.author = author;
    }

    public TableColumn<?, ?> getPost_date() {
        return post_date;
    }

    public void setPost_date(TableColumn<noteDate, String> post_date) {
        this.post_date = post_date;
    }

    public TableView<?> getMyTable() {
        return myTable;
    }

    public void setMyTable(TableView<?> myTable) {
        this.myTable = myTable;
    }

    public TableColumn<?, ?> getTitle() {
        return title;
    }

    public void setTitle(TableColumn<noteDate, String> title) {
        this.title = title;
    }

    public TableColumn<?, ?> getDelete() {
        return delete;
    }

    public void setDelete(TableColumn<noteDate, String> delete) {
        this.delete = delete;
    }

    public TableColumn<?, ?> getContent() {
        return content;
    }

    public void setContent(TableColumn<noteDate, String> content) {

        this.content = content;
    }


}
