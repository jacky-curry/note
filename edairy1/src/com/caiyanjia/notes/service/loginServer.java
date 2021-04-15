package com.caiyanjia.notes.service;

import com.caiyanjia.notes.bean.Msg;
import com.caiyanjia.notes.view.Administrator;
import com.caiyanjia.notes.dao.Impl.administratorDaoImpl;
import com.caiyanjia.notes.dao.Impl.userDaoImpl;
import com.caiyanjia.notes.util.JDBCUtils;
import javafx.stage.Stage;

import java.sql.Connection;

public class loginServer {
    //需要在这里面写方法供controller调用
    /**
     * 判断是否登录成功的方法
     * @return  登录成功返回true，否则返回false
     *
     */
    private final String user_id = Msg.getUser_id();
    public Boolean ifLogin(String id,String password) throws Exception {
        Connection connection = JDBCUtils.getConnection();
        userDaoImpl userDao = new userDaoImpl();


        administratorDaoImpl administratordao = new administratorDaoImpl();
        if(administratordao.ifLodin(connection,id,password)){
            //登录成功跳转到管理员页面
            new Administrator().start(new Stage());
            return false;
        }

        if(userDao.getLodinUser(connection,id,password) != null){
            Msg.setUser_id(user_id);
            if(connection!= null)
                connection.close();
            return true;
        }else{
            return false;
        }
    }



}
