package com.caiyanjia.notes.dao.Impl;

import com.caiyanjia.notes.bean.User;
import com.caiyanjia.notes.dao.Dao.BaseDAO;
import com.caiyanjia.notes.dao.Dao.userDao;

import java.sql.Connection;
import java.util.List;

public class userDaoImpl extends BaseDAO implements userDao {

	@Override
	public void insert(Connection conn, User u) {
		String sqlString  = "insert into user(id,password,name,blacklist) values(?,?,?,?)";
		Update(conn, sqlString, u.getId(),u.getPassword(),u.getName(),"0");
		
	}

	@Override
	public void update(Connection conn, User u) {
		String sqlString = "update user set password = ?,name = ? where id = ?";

		Update(conn, sqlString,u.getPassword(),u.getName(),u.getId());
	}

	
	/*
	 * class java.lang.String cannot be cast to class com.caiyanjia.notes.entity.user
	 * String 类型不能转化为 实体类型
	 * 在getValue方法中获得的是一个String的特殊值的话，不能转化成user类型
	 */
	@Override
	public User getLodinUser(Connection conn, String id, String password) {
		String sqlString = "select id, password from user where id = ? and password = ?";
		User user = getInstance(conn, User.class, sqlString, id,password);
		return user;
	}

	@Override
	public User judgeUser(Connection conn, String id) {
		String sqlString = "select id,password,name,blacklist from user where id = ?";
		
		return getInstance(conn, User.class, sqlString, id);
	}

	@Override
	public List<User> getAllUser(Connection conn) {
		String sql = "select * from user";
		getForList(conn,User.class,sql);



		return getForList(conn,User.class,sql);
	}


}
