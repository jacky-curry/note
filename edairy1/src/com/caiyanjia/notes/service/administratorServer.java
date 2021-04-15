package com.caiyanjia.notes.service;

import com.caiyanjia.notes.dao.Impl.administratorDaoImpl;
import com.caiyanjia.notes.entity.User;

import java.sql.Connection;
import java.util.List;

public class administratorServer extends administratorDaoImpl {
    static private String user_id;

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        administratorServer.user_id = user_id;
    }

    //发布公告
    public void insertNotice(Connection conn, String notice) {
        String sql = "insert into `administrator`(notice) values(?) ";
        if(Update(conn,sql,notice)>0){
            System.out.println("插入公告成功");
        }else{
            System.out.println("插入公告失败");
        }
    }

    //获取公告
    public String getNotice(Connection conn){
        String sql = "select notice from administrator where id = ?";
        return getValue(conn,sql,"Admin");
    }


    //设置选择的user信息
    public void setUser(String user_id){
        setUser_id(user_id);
    }

    public String getUser(){
       return getUser_id();
    }

    public List<User> getAllUser(Connection conn) {
        String sql = "select * from user";
        getForList(conn,User.class,sql);



        return getForList(conn,User.class,sql);
    }


}
