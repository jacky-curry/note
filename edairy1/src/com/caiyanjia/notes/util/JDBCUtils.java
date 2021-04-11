package com.caiyanjia.notes.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;











public class JDBCUtils {
//@Test
//public void test() {
//	Connection connection =   JDBCUtils.getConnection();
//	System.out.println(connection);
//
//	
//}
	/*
	 * 要正确的导包，不然真的是一些奇奇怪怪的错误，搞了我一小时
	 * 
	 */
	
	public static Connection getConnection()  {
		
		try {
			InputStream is1 = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
			
			Properties pros = new Properties();

			pros.load(is1);
			
			String user = pros.getProperty("user");
			String password = pros.getProperty("password");
			String url = pros.getProperty("url");
			String driverClass = pros.getProperty("driverClass");
			
			
			Connection conn = null;

			Class.forName(driverClass);
				
			conn = DriverManager.getConnection(url, user, password);

			return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public static void closeResoure(Connection conn, java.sql.PreparedStatement ps) {
		try {
			ps.close();
			if(conn!=null)
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void closeResoure(Connection conn, java.sql.PreparedStatement ps,ResultSet rs) {
		
		try {
			ps.close();
			if(conn != null)
				conn.close();
			if(rs != null)
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
//	@Test
//	public void getConnection5() throws Exception {
//		InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
//		
//		Properties pros = new Properties();
//		pros.load(is);
//		
//		String user = pros.getProperty("user");
//		String password = pros.getProperty("password");
//		String url = pros.getProperty("url");
//		String driverClass = pros.getProperty("driverClass");
//		
//		
//		Class.forName(driverClass);
//		
//		Connection conn = DriverManager.getConnection(url, user, password);
//		System.out.println(conn);
//		
//		
//	}
	
}
