package com.caiyanjia.notes.dao.Impl;

import com.caiyanjia.notes.bean.Administrator;
import com.caiyanjia.notes.dao.Dao.BaseDAO;
import com.caiyanjia.notes.dao.Dao.administratorDao;

import java.sql.Connection;

public class administratorDaoImpl extends BaseDAO implements administratorDao {
    @Override
    public Boolean set_user(Connection conn, String user_id) {
        String sql  = "Update administrator set get_user = ? where id = ?";
        if (Update(conn,sql,user_id,"Admin")>0)
        {
            System.out.println("获得用户成功");
            return true;
        }else{
            System.out.println("获得用户失败");
            return false;
        }
    }

    @Override
    public String get_user(Connection conn) {
        String sql  = "select get_user from administrator where id = ?";

        return getValue(conn,sql,"Admin");
    }

    @Override
    public void clearUser(Connection conn) {
        String sql = "Update administrator set get_user = null where id = ?";
        Update(conn,sql,"Admin");
    }

    @Override
    public Boolean blackList(Connection conn, Boolean ifBlack, String user_id) {
        String sql  = "Update user set blacklist = ? where id = ?";
        if(Update(conn,sql,ifBlack,user_id)>0){
            System.out.println("设置黑名单成功");
            return true;
        }else{
            System.out.println("设置黑名单失败");
            return false;
        }
    }

    @Override
    public Boolean ifLodin(Connection conn, String id, String password) {
        String sql  = "Select * from administrator where id = ? and password = ?";
        Administrator administrator = getInstance(conn, Administrator.class,sql,id,password);
        System.out.println(administrator);
        if(administrator != null){

            return true;
        }else{
            return false;
        }


    }


}
