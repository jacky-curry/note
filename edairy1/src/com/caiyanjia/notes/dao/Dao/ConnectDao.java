package com.caiyanjia.notes.dao.Dao;

import com.caiyanjia.notes.dao.Dao.BaseDAO;

import java.sql.Connection;

public class ConnectDao extends BaseDAO {

    /**
     * 登录后，保存用户id号
     * @param conn
     * @param id
     */
    public void insert(Connection conn,String id){
        //在每一次插入之前先清空数据
        exit(conn);

        String sql = "insert into connect(id) value(?) ";

        if(Update(conn, sql,id) > 0) {
            System.out.println("插入成功");
        }else{
            System.out.println("插入失败");
        }

    }

    /**
     * 登录后，查询用户id号
     * @param conn
     * @return 返回当前用户的id
     */
    public String  getId(Connection conn){
        String sql  = "select id from connect";
        return getValue(conn,sql);
    }

    /**
     * 退出登录时，清空connect表中所有数据
     * @param conn
     */
    public void exit(Connection conn){
        String sql = "delete from connect";
        if(Update(conn, sql) > 0) {
            System.out.println("退出成功");
        }else{
            System.out.println("退出失败");
        }
    }



}
