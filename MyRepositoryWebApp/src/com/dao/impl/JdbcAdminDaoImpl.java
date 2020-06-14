package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dao.AdminDao;
import com.mapper.Admin;
import com.util.DBConn;

public class JdbcAdminDaoImpl implements AdminDao {

	@Override
	public Admin findByName(String username) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Admin admin = null;
		String sql = "select * from `admin` where `username`='" + username +"'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
	
			rs = pstm.executeQuery();
			
			if(rs.next())
			{
				admin = new Admin();
				admin.setUsername(rs.getString("username"));
				admin.setPassword(rs.getString("password"));
			}
			
			if (pstm != null) {
				pstm.close();
			}
			if (rs != null) {
				rs.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return admin;
	}

}
