package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dao.CheckTaskDao;
import com.mapper.CheckTask;
import com.mapper.UserInfo;
import com.util.DBConn;

public class JdbcCheckTaskDaoImpl implements CheckTaskDao{

	@Override
	public boolean add(CheckTask task) {
		// TODO Auto-generated method stub
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "INSERT INTO `check_task` (`start_time`, `end_time`, `shelves`, `username`) VALUES (?, ?, ?, ?);";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String startTime  = df.format(task.getStart_time());
			String endTime = df.format(task.getEnd_time());
			
			pstm.setString(1, startTime);
			pstm.setString(2, endTime);
			pstm.setString(3, task.getShelves());
			pstm.setString(4, task.getUsername());
			
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
	public List<CheckTask> findAllByUsername(String username) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<CheckTask> list = new ArrayList<CheckTask>();
		String sql = "select * from `check_task` where `username`='" + username +"'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				Timestamp start_time = rs.getTimestamp("start_time");
				Timestamp end_time = rs.getTimestamp("end_time");

				CheckTask task = CheckTask.builder()
						.id(rs.getString("id"))
						.start_time(start_time)
						.end_time(end_time)
						.shelves(rs.getString("shelves"))
						.username(rs.getString("username"))
						.build();
				
/*				task.setId(rs.getString("id"));
				task.setStart_time(start_time);
				task.setEnd_time(end_time);
				task.setShelves(rs.getString("shelves"));
				task.setUsername(rs.getString("username"));*/

				list.add(task);
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
	public List<CheckTask> findAll() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<CheckTask> list = new ArrayList<CheckTask>();
		String sql = "select * from `check_task`";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				Timestamp start_time = rs.getTimestamp("start_time");
				Timestamp end_time = rs.getTimestamp("end_time");

				CheckTask task = CheckTask.builder()
						.id(rs.getString("id"))
						.start_time(start_time)
						.end_time(end_time)
						.shelves(rs.getString("shelves"))
						.username(rs.getString("username"))
						.build();
				
/*				task.setId(rs.getString("id"));
				task.setStart_time(start_time);
				task.setEnd_time(end_time);
				task.setShelves(rs.getString("shelves"));
				task.setUsername(rs.getString("username"));*/

				list.add(task);
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
	public boolean deleteById(String id) {
		// TODO Auto-generated method stub
		
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql="delete from `check_task` where `id`=" + id;
		
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

	@Override
	public List<CheckTask> findAllTaskIsValid(String username) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<CheckTask> list = new ArrayList<CheckTask>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowDate = df.format(new Date());
		
		String sql = "select * from `check_task` where `start_time` <= ? AND `end_time` >= ? AND `username`=?";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, nowDate);
			pstm.setString(2, nowDate);
			pstm.setString(3, username);
			
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				
				Timestamp start_time = rs.getTimestamp("start_time");
				Timestamp end_time = rs.getTimestamp("end_time");
				
				CheckTask task = CheckTask.builder()
						.id(rs.getString("id"))
						.start_time(start_time)
						.end_time(end_time)
						.shelves(rs.getString("shelves"))
						.username(rs.getString("username"))
						.build();
				
/*				task.setId(rs.getString("id"));
				task.setStart_time(start_time);
				task.setEnd_time(end_time);
				task.setShelves(rs.getString("shelves"));
				task.setUsername(rs.getString("username"));*/

				list.add(task);
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
	public CheckTask findById(String id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CheckTask task = null;
		String sql = "select * from `check_task` where `id`=?";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			
			pstm.setString(1, id);

			
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				
				Timestamp start_time = rs.getTimestamp("start_time");
				Timestamp end_time = rs.getTimestamp("end_time");
				
				task = CheckTask.builder()
						.id(rs.getString("id"))
						.start_time(start_time)
						.end_time(end_time)
						.shelves(rs.getString("shelves"))
						.username(rs.getString("username"))
						.build();
				
/*				task.setId(rs.getString("id"));
				task.setStart_time(start_time);
				task.setEnd_time(end_time);
				task.setShelves(rs.getString("shelves"));
				task.setUsername(rs.getString("username"));*/

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
			return task;
		}
		
		return task;
	}

}
