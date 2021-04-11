package com.caiyanjia.notes.dao.Dao;

import java.sql.Connection;

public interface noticeDao {

    /**
     * 写公告
     */
    public void insert(Connection conn,String notice);

    /**
     * 读公告
     */
    public String getNotice(Connection conn);



}
