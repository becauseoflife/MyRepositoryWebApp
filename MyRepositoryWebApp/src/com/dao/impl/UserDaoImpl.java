package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.dao.UserDao;
import com.entity.User;
import com.util.DBConn;

public class UserDaoImpl implements UserDao{

	@Override
	public boolean insert(User user) throws SQLException {
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into user(`username`, `password`, `telephone`, `email`, `regdate`) VALUES (?, ?, ?, ?, ?)";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, user.getUserName());
			pstm.setString(2, user.getPassword());
			pstm.setString(3, user.getTelephone());
			pstm.setString(4, user.getEmail());
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String registerDate  = df.format(user.getRegDate());
			pstm.setString(5, registerDate);
			
			flag = pstm.executeUpdate();
			
			if (pstm != null) {
				pstm.close();
			}
			
		} catch (Exception e) {
			System.out.println("插入数据操作异常");
			e.printStackTrace();
		} 
		
		// 插入成功
		if(flag != 0)
			return true;
		
		return false;
		
	}

	@Override
	public User querryByUserName(String userName) throws SQLException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		User user = null;
		String sql = "select * from user where username=?";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userName);
			
			rs = pstm.executeQuery();
			if(rs.next()){
				user = new User();
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setTelephone(rs.getString("telephone"));
				user.setEmail(rs.getString("email"));
				
				Timestamp t = rs.getTimestamp("regdate");
				user.setRegDate(t);
			}
			
			if (pstm != null) {
				pstm.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("查询操作异常：" + sql);
			e.printStackTrace();
		} 

		return user;
	}




}
