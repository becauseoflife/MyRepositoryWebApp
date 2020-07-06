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

import com.dao.OrderInfoDao;
import com.mapper.OrderInfo;
import com.util.DBConn;

public class JdbcOrderInfoDaoImpl implements OrderInfoDao {

	@Override
	public boolean add(OrderInfo orderInfo) {
		// TODO Auto-generated method stub
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into `order`(`order_id`, `username`, `state`, `create_time`) VALUES (?, ?, ?, ?)";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, orderInfo.getOrder_id());
			pstm.setString(2, orderInfo.getUsername());
			pstm.setInt(3, orderInfo.getState());
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_time = df.format(orderInfo.getCreate_time());
			pstm.setString(4, create_time);
			
			flag = pstm.executeUpdate();
			
			if (pstm != null) {
				pstm.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ≤Â»Î≥…π¶
		if(flag != 0)
			return true;
		return false;
	}

	@Override
	public List<OrderInfo> findAllByState(int state) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		String sql = "SELECT * FROM `order` WHERE `state` = ?;";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, state);
			
			rs = pstm.executeQuery();
			while(rs.next())
			{
				OrderInfo o = new OrderInfo();
				o.setOrder_id(rs.getString("order_id"));
				o.setUsername(rs.getString("username"));
				o.setState(rs.getInt("state"));
				
				Timestamp t = rs.getTimestamp("create_time");
				o.setCreate_time(t);
				
				list.add(o);
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
		
		return list;
	}

	@Override
	public boolean updateSetState(String order_id, int state) {
		// TODO Auto-generated method stub
		
		int flag=0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "UPDATE `order` SET `state`=? where `order_id`='"+ order_id +"'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, state);
			
			flag = pstm.executeUpdate();
			
			if(pstm!=null){
				pstm.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag!=0)
			return true;
		return false;
	}

	@Override
	public OrderInfo findById(String orderID) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		OrderInfo orderInfo = null;
		String sql = "SELECT * FROM `order` WHERE `order_id` = '"+ orderID +"'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			if(rs.next())
			{
				orderInfo = new OrderInfo();
				orderInfo.setOrder_id(rs.getString("order_id"));
				orderInfo.setUsername(rs.getString("username"));
				orderInfo.setState(rs.getInt("state"));
				
				Timestamp t = rs.getTimestamp("create_time");
				orderInfo.setCreate_time(t);
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
		return orderInfo;
	}

	@Override
	public List<OrderInfo> findAll() {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<OrderInfo> list = new ArrayList<OrderInfo>();
		String sql = "SELECT * FROM `order`";
		
		try {
			conn = DBConn.getConnection();
		
			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();
			while(rs.next())
			{
				OrderInfo o = new OrderInfo();
				o.setOrder_id(rs.getString("order_id"));
				o.setUsername(rs.getString("username"));
				o.setState(rs.getInt("state"));
				
				Timestamp t = rs.getTimestamp("create_time");
				o.setCreate_time(t);
				
				list.add(o);
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
		
		return list;
	}



}
