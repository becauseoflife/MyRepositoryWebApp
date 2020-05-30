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

import com.dao.OrderDao;
import com.entity.Order;
import com.util.DBConn;

public class OrderDaoImpl implements OrderDao {

	@Override
	public boolean insert(Order order) {
		// TODO Auto-generated method stub
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "insert into `order`(`order_id`, `username`, `state`, `create_time`) VALUES (?, ?, ?, ?)";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, order.getOrder_id());
			pstm.setString(2, order.getUsername());
			pstm.setInt(3, order.getState());
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String create_time = df.format(order.getCreate_time());
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
	public List<Order> queryOrderListByState(int state) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<Order> list = new ArrayList<Order>();
		String sql = "SELECT * FROM `order` WHERE `state` = ?;";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, state);
			
			rs = pstm.executeQuery();
			while(rs.next())
			{
				Order o = new Order();
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
	public boolean updateForState(String order_id, int state) {
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



}
