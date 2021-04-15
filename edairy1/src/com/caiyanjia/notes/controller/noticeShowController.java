package com.caiyanjia.notes.controller;

import com.caiyanjia.notes.dao.Impl.administratorDaoImpl;
import com.caiyanjia.notes.dao.Impl.noticeDaoImpl;
import com.caiyanjia.notes.util.JDBCUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class noticeShowController implements Initializable {
    @FXML
    private TextArea notice_show;

//    noticeDaoImpl noticeDao = new noticeDaoImpl();
    Connection conn = JDBCUtils.getConnection();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        notice_show.setText(new administratorDaoImpl().getNotice(conn,"Admin"));
        notice_show.setEditable(false);
    }
}
