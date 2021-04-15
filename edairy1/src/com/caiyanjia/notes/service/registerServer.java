package com.caiyanjia.notes.service;

import com.caiyanjia.notes.dao.Impl.userDaoImpl;
import com.caiyanjia.notes.entity.User;
import com.caiyanjia.notes.util.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class registerServer {

    /**
     * 判断Id是否存在
     * @return 数据库中存在即返回false，不存在返回true
     */
    public Boolean ifIdExist(String rg_id) throws SQLException {

            Connection connection = JDBCUtils.getConnection();
            userDaoImpl userdaoimpl = new userDaoImpl();

            User getuser = userdaoimpl.judgeUser(connection,rg_id);
            connection.close();
            if(getuser != null){
                return false;
            }else{
                return true;
            }
    }

    public Boolean insertUser(User user){
        Connection connection = JDBCUtils.getConnection();
        userDaoImpl userdaoimpl = new userDaoImpl();
        if(userdaoimpl.insert(connection,user)){

            return true;
        }else{
            return false;
        }

    }


}
