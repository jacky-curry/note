package com.caiyanjia.notes.dao.Dao;

import com.caiyanjia.notes.bean.Group;

import java.sql.Connection;
import java.util.List;

public interface groupDao {

    public Boolean changName(Connection conn,String oldName,String newName);


    public Boolean insertGroup(Connection conn,Group group);


    public List<String> searchGroup(Connection conn,String user_id);

    /**
     * 用于删除功能
     * @param conn
     * @param oldgGroup
     * @param user_id
     * @return
     */
    public Boolean deleteGroup(Connection conn,String oldgGroup,String user_id);


}
