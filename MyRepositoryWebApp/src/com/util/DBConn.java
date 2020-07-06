package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* ���ݿ�������(����ģʽ) */
public class DBConn {

	private static final String driver = "com.mysql.cj.jdbc.Driver";  // ���ݿ�����
	// �������ݿ��URL��ַ
	private static final String url = "jdbc:mysql://localhost:3306/design_pattern?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
	private static final String username = "root";	// ���ݿ���û���
	private static final String password = "";		// ���ݿ������
	
	// ��̬˽�г�Ա����
	private static Connection conn = null;
	
	// ��̬����鸺���������
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
	
	// ˽�й��캯��
	private DBConn(){};
	
	// ��̬���й�������������Ψһʵ��
	public static Connection getConnection() throws SQLException{
		if(conn == null){
			conn = DriverManager.getConnection(url, username, password);
			return conn;
		}
		return conn;
	}
	
	// ���Դ���
/*	public static void main(String[] args) throws SQLException {
		Connection conn = DBHelper.getConnection();
		if (conn != null) {
			System.out.println("���ӳɹ�");
		}else {
			System.out.println("����ʧ��");
		}
	}*/
	
}
