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

import com.dao.OrderItemDao;
import com.mapper.OrderItemInfo;
import com.util.DBConn;

public class JdbcOrderItemDaoImpl implements OrderItemDao {

	@Override
	public boolean add(OrderItemInfo orderItemInfo) {
		// TODO Auto-generated method stub
		
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "INSERT INTO `order_goods` (`order_id`, `clothingID`, `shelves`, `location`, `number`, `pick_sign`, `pick_time`) VALUES (?, ?, ?, ?, ?, ?, ?);";	
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, orderItemInfo.getOrder_id());
			pstm.setString(2, orderItemInfo.getClothingID());
			pstm.setString(3, orderItemInfo.getShelves());
			pstm.setString(4, orderItemInfo.getLocation());
			pstm.setInt(5, orderItemInfo.getNumber());
			pstm.setInt(6, orderItemInfo.getPick_sign());
			
			if(orderItemInfo.getPick_time() == null)
			{
				pstm.setDate(7, null);
			}
			else
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String pick_time = df.format(orderItemInfo.getPick_time());
				pstm.setString(7, pick_time);
			}

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
	public List<OrderItemInfo> findAllById(String orderID) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<OrderItemInfo> list = new ArrayList<OrderItemInfo>();
		String sql = "SELECT * FROM design_pattern.order_goods WHERE `order_id`= ?;";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, orderID);
			
			rs = pstm.executeQuery();
			while(rs.next()){
				OrderItemInfo o = new OrderItemInfo();
				o.setOrder_id(rs.getString("order_id"));
				o.setClothingID(rs.getString("clothingID"));
				o.setShelves(rs.getString("shelves"));
				o.setLocation(rs.getString("location"));
				o.setNumber(rs.getInt("number"));
				o.setPick_sign(rs.getInt("pick_sign"));
				
				Timestamp t = rs.getTimestamp("pick_time");
				o.setPick_time(t);
				
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
	public boolean updateSetSign(OrderItemInfo orderItem) {
		// TODO Auto-generated method stub
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "UPDATE `order_goods` "
				+ "SET "
					+ "`pick_sign`=? , `pick_time`=?  "
				+ "WHERE "
					+ "`order_id`= '"+orderItem.getOrder_id()+"' "
				+ "AND"
					+ " `clothingID`='"+orderItem.getClothingID()+"'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, orderItem.getPick_sign());
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df.format(orderItem.getPick_time());
			//System.out.print(time);
			pstm.setString(2, time);
			
			flag = pstm.executeUpdate();
			
			if (pstm != null) {
				pstm.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (flag!=0) {
			return true;
		}
		return false;
	}

}
