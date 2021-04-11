package com.caiyanjia.notes.dao.Impl;

import com.caiyanjia.notes.bean.Group;
import com.caiyanjia.notes.dao.Dao.BaseDAO;
import com.caiyanjia.notes.dao.Dao.groupDao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class groupDaoImpl extends BaseDAO implements groupDao {


    @Override
    public Boolean changName(Connection conn,String oldName, String newName) {
        String sql = "Update `group` set `name` = ? where `name` = ?";

        if(Update(conn,sql,newName,oldName)>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean insertGroup(Connection conn, Group group) {
        String sql = "insert into `group`(`name`,user_id) values(?,?)";

        if(Update(conn,sql,group.getName(),group.getUser_id())>0){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public List<String> searchGroup(Connection conn, String user_id) {
        String sql = "select `name` from `group` where user_id = ?";

        List<String> list = new ArrayList<>();
        for(Group p: Objects.requireNonNull(getForList(conn, Group.class, sql, user_id))){
            list.add(p.getName());
        }


        return list;
    }


    public Boolean deleteGroup(Connection conn,String oldgGroup,String user_id){
        String sql = "delete  from `group` where `name` = ? and user_id = ?";
         if(Update(conn,sql,oldgGroup,user_id)>0 ){
             return true;
         }else{
             return  false;
         }

    }


}
