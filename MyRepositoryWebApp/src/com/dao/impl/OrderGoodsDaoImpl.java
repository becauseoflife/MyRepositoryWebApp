package com.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.dao.OrderGoodsDao;
import com.entity.OrderGoods;
import com.util.DBConn;

public class OrderGoodsDaoImpl implements OrderGoodsDao {

	@Override
	public boolean insert(OrderGoods orderGoods) {
		// TODO Auto-generated method stub
		
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "INSERT INTO `order_goods` (`order_id`, `clothingID`, `number`, `pick_sign`, `pick_time`) VALUES (?, ?, ?, ?, ?);";	
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, orderGoods.getOrder_id());
			pstm.setString(2, orderGoods.getClothingID());
			pstm.setInt(3, orderGoods.getNumber());
			pstm.setInt(4, orderGoods.getPick_sign());
			
			if(orderGoods.getPick_time() == null)
			{
				pstm.setDate(5, null);
			}
			else
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String pick_time = df.format(orderGoods.getPick_time());
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

}
