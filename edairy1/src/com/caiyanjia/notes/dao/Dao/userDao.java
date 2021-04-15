package com.caiyanjia.notes.dao.Dao;

import com.caiyanjia.notes.entity.User;

import java.sql.Connection;
import java.util.List;

public interface userDao {
	/**
	 * 
	 * @Title: insert
	 * @Description: 用于注册新的user账户
	 * @param conn
	 * @param user
	 * @author author
	 * @date 2021-03-27 10:58:05
	 */
	public Boolean insert(Connection conn, User user);
	/**
	 * 
	 * @Title: update
	 * @Description: 修改密码
	 * @param conn
	 * @param user
	 * @author author
	 * @date 2021-03-27 10:58:32
	 */
	public Boolean update(Connection conn, User user);

	/**
	 * 
	 * @Title: getLodinUser
	 * @Description: 登录时，查询账号是否存在
	 * @param conn
	 * @param id
	 * @param password
	 * @return	账号密码正确的话返回一个user的对象，否则则返回null
	 * @author author
	 * @date 2021-03-28 10:19:50
	 */
	public User getLodinUser(Connection conn, String id, String password);
	/**
	 * 
	 * @Title: judgeUser
	 * @Description: 注册时，判断是否已经有了账号sss
	 * @param conn
	 * @param id
	 * @return
	 * @author author
	 * @date 2021-03-28 10:35:41
	 */
	public User judgeUser(Connection conn, String id);
	/**
	 * 获取所有用户信息
	 */
	public List<User> getAllUser(Connection conn);

	
}
