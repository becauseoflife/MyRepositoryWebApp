package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.dao.UserDao;
import com.mapper.UserInfo;
import com.util.DBConn;

public class JdbcUserDaoImpl implements UserDao{

	@Override
	public boolean add(UserInfo userInfo) throws SQLException {
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into user(`username`, `password`, `telephone`, `email`, `regdate`) VALUES (?, ?, ?, ?, ?)";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userInfo.getUserName());
			pstm.setString(2, userInfo.getPassword());
			pstm.setString(3, userInfo.getTelephone());
			pstm.setString(4, userInfo.getEmail());
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String registerDate  = df.format(userInfo.getRegDate());
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
	public UserInfo findByName(String userName) throws SQLException {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		UserInfo userInfo = null;
		String sql = "select * from user where username=?";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, userName);
			
			rs = pstm.executeQuery();
			if(rs.next()){
				userInfo = new UserInfo();
				userInfo.setUserName(rs.getString("username"));
				userInfo.setPassword(rs.getString("password"));
				userInfo.setTelephone(rs.getString("telephone"));
				userInfo.setEmail(rs.getString("email"));
				
				Timestamp t = rs.getTimestamp("regdate");
				userInfo.setRegDate(t);
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

		return userInfo;
	}

	@Override
	public List<UserInfo> findAll() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<UserInfo> list = new ArrayList<UserInfo>();
		String sql = "select * from user";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				Timestamp t = rs.getTimestamp("regdate");

				UserInfo u = UserInfo.builder()
									.userName(rs.getString("username"))
									.password(rs.getString("password"))
									.telephone(rs.getString("telephone"))
									.email(rs.getString("email"))
									.regDate(t)
									.build();

				list.add(u);
			}
			if (pstm != null) {
				pstm.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return list;
		}
		
		return list;
	}

	@Override
	public boolean updateByName(String username, UserInfo user) {
		// TODO Auto-generated method stub
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "update `user` set "
				+ "`username`='" + user.getUserName() +"',"
				+ "`password`='" + user.getPassword() +"',"
				+ "`telephone`='"+ user.getTelephone() +"',"
				+ "`email`='"	 + user.getEmail() +"'"
				+ "where"
				+ "`username`='"+ username +"'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);

			flag = pstm.executeUpdate();
			
			if (pstm != null) {
				pstm.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQL语句：" + sql);
			e.printStackTrace();
		}
		if(flag==1)
			return true;
		return false;
	}

	@Override
	public boolean deleteByName(String username) {
		// TODO Auto-generated method stub
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql="delete from `user` where `username`='" + username + "'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			
			flag = pstm.executeUpdate();
			
			if (pstm != null) {
				pstm.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(flag==1)
			return true;
		return false;
	}




}
