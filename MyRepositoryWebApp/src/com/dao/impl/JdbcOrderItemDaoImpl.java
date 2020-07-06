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
		String sql = "INSERT INTO `order_item` (`order_id`, `clothingID`, `number`, `pick_sign`, `pick_time`) VALUES (?, ?, ?, ?, ?);";	
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, orderItemInfo.getOrder_id());
			pstm.setString(2, orderItemInfo.getClothingID());
			pstm.setInt(3, orderItemInfo.getNumber());
			pstm.setInt(4, orderItemInfo.getPick_sign());
			
			if(orderItemInfo.getPick_time() == null)
			{
				pstm.setDate(5, null);
			}
			else
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String pick_time = df.format(orderItemInfo.getPick_time());
				pstm.setString(5, pick_time);
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
		String sql = "SELECT * FROM `order_item` WHERE `order_id`= ?;";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, orderID);
			
			rs = pstm.executeQuery();
			while(rs.next()){
				Timestamp t = rs.getTimestamp("pick_time");
				
				OrderItemInfo o = OrderItemInfo.builder()
						.order_id(rs.getString("order_id"))
						.clothingID(rs.getString("clothingID"))
						.number(rs.getInt("number"))
						.pick_sign(rs.getInt("pick_sign"))
						.pick_time(t)
						.pick_user(rs.getString("pick_user"))
						.build();
				
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
		String sql = "UPDATE `order_item` "
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
			
			if(orderItem.getPick_time()!=null)
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = df.format(orderItem.getPick_time());
				//System.out.print(time);
				pstm.setString(2, time);
			}
			else
			{
				pstm.setString(2, null);
			}

			
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

	@Override
	public List<OrderItemInfo> findAllByPickSign(int pickSign) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<OrderItemInfo> list = new ArrayList<OrderItemInfo>();
		String sql = "SELECT * FROM `order_item` WHERE `pick_sign`= ?;";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, pickSign);
			
			rs = pstm.executeQuery();
			while(rs.next()){
				Timestamp t = rs.getTimestamp("pick_time");
				OrderItemInfo o = OrderItemInfo.builder()
						.order_id(rs.getString("order_id"))
						.clothingID(rs.getString("clothingID"))
						.number(rs.getInt("number"))
						.pick_sign(rs.getInt("pick_sign"))
						.pick_time(t)
						.pick_user(rs.getString("pick_user"))
						.build();
				
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
	public boolean updateSetPickUser(OrderItemInfo itemInfo) {
		// TODO Auto-generated method stub
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "UPDATE `order_item` "
				+ "SET "
					+ "`pick_user`= '" + itemInfo.getPick_user() +"'"
				+ "WHERE "
					+ "`order_id`= '"+itemInfo.getOrder_id()+"' "
				+ "AND"
					+ " `clothingID`='"+itemInfo.getClothingID()+"'";
		
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
		
		if (flag!=0) {
			return true;
		}
		return false;
	}

	@Override
	public int sumForNumber(String pick_user, int pick_sign) {
		// TODO Auto-generated method stub
		int result = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT sum(`number`) as `sum` FROM `order_item` where `pick_user`='"+pick_user+"' AND `pick_sign`="+pick_sign;
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			
			rs = pstm.executeQuery();
			
			if(rs.next())
				result = rs.getInt("sum");
			
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
		
		return result;
	}

	@Override
	public List<OrderItemInfo> findAllByPickUser(String pickUser, int pick_sign) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<OrderItemInfo> list = new ArrayList<OrderItemInfo>();
		String sql = "SELECT * FROM `order_item` where `pick_user`='"+pickUser+"' AND `pick_sign`="+pick_sign;
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			
			rs = pstm.executeQuery();
			
			while(rs.next()){
				Timestamp t = rs.getTimestamp("pick_time");
				OrderItemInfo o = OrderItemInfo.builder()
						.order_id(rs.getString("order_id"))
						.clothingID(rs.getString("clothingID"))
						.number(rs.getInt("number"))
						.pick_sign(rs.getInt("pick_sign"))
						.pick_time(t)
						.pick_user(rs.getString("pick_user"))
						.build();
				
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
