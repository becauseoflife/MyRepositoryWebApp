package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dao.UserDBHelperDao;
import com.entity.User;
import com.util.UserDBConn;

public class UserDBGHelperDaoImpl implements UserDBHelperDao{

	@Override
	public void insert(User user) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into user(`username`, `password`, `telphone`, `email`, `regdate`) VALUES (?, ?, ?, ?, ?)";
		
		try {
			conn = UserDBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, user.getUserName());
			pstm.setString(2, user.getPassword());
			pstm.setString(3, user.getTelphone());
			pstm.setString(4, user.getEmail());
			pstm.setDate(5, user.getRegDate());
			
			pstm.executeUpdate();
			
			if (pstm != null) {
				pstm.close();
			}
			
		} catch (Exception e) {
			System.out.println("插入数据操作异常");
			e.printStackTrace();
		} finally {
/*			if (conn != null) {
				conn.close();
			}*/
		}
		
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
			conn = UserDBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userName);
			
			rs = pstm.executeQuery();
			if(rs.next()){
				user = new User();
				user.setUserName(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setTelphone(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setRegDate(rs.getDate(5));
			}
			
			if (pstm != null) {
				pstm.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("查询操作异常：" + sql);
			e.printStackTrace();
		} finally {
/*			if (conn != null) {
				conn.close();
			}*/
		}
		
		return user;
	}




}
