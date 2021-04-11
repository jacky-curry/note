package com.caiyanjia.notes.dao.Dao;

import java.sql.Connection;

public interface administratorDao {
    //提供一个方法可以用于记录选中user
    public Boolean set_user(Connection conn,String user_id);

    //提供一个方法可以获取用户user的值
    public String get_user(Connection conn);

    //提供一个方法清空user的值
    public void clearUser(Connection conn);

    //用于设置user为黑名单或者解除黑名单
    public Boolean blackList(Connection conn,Boolean ifBlack,String user_id);

    //用于管理员的登录
    public Boolean ifLodin(Connection conn, String id,String password);

}
