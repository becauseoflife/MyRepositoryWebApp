package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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



}
