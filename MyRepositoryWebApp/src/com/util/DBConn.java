package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* 数据库连接类(单例模式) */
public class DBConn {

	private static final String driver = "com.mysql.cj.jdbc.Driver";  // 数据库驱动
	// 连接数据库的URL地址
	private static final String url = "jdbc:mysql://localhost:3306/design_pattern?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
	private static final String username = "root";	// 数据库的用户名
	private static final String password = "";		// 数据库的密码
	
	// 静态私有成员变量
	private static Connection conn = null;
	
	// 静态代码块负责加载驱动
	static 
	{
		try
		{
			Class.forName(driver);
		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	// 私有构造函数
	private DBConn(){};
	
	// 静态共有工厂方法，返回唯一实例
	public static Connection getConnection() throws SQLException{
		if(conn == null){
			conn = DriverManager.getConnection(url, username, password);
			return conn;
		}
		return conn;
	}
	
	// 测试代码
/*	public static void main(String[] args) throws SQLException {
		Connection conn = DBHelper.getConnection();
		if (conn != null) {
			System.out.println("连接成功");
		}else {
			System.out.println("连接失败");
		}
	}*/
	
}
