package com.caiyanjia.notes.service;

import com.caiyanjia.notes.dao.Dao.userDao;
import com.caiyanjia.notes.dao.Impl.userDaoImpl;
import com.caiyanjia.notes.entity.User;
import com.caiyanjia.notes.util.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class userServer {
    userDaoImpl userdaoimpl = new userDaoImpl();
    Connection connection = JDBCUtils.getConnection();

    //判断是否是黑名单
    public Boolean ifIsBlackList(String user_id) throws SQLException {
        userDaoImpl userdaoimpl = new userDaoImpl();
        User user = userdaoimpl.judgeUser(connection, user_id);
        connection.close();
        return user.getBlacklist();
    }

    //保存修改后的信息
    public Boolean savaImformation(Connection conn,User user){
        return userdaoimpl.update(conn, user);
    }

    //获取user的信息
    public User getUser(Connection conn,String user_id){
        return userdaoimpl.judgeUser(connection,user_id);
    }






}
