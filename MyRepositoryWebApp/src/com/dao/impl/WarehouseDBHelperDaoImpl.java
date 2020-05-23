package com.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dao.WarehouseDBHelperDao;
import com.entity.ClothingInfo;
import com.util.DBConn;

public class WarehouseDBHelperDaoImpl implements WarehouseDBHelperDao {

	@Override
	public List<ClothingInfo> queryByClothingID(String clothingID) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<ClothingInfo> list = new ArrayList<ClothingInfo>();
		
		String sql = "select * from warehouse where `clothingID`='" + clothingID + "'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();
			while (rs.next()) {
				
				ClothingInfo c = new ClothingInfo();
				c.setShelves(rs.getString("shelves"));
				c.setLocation(rs.getString("location"));
				c.setClothingID(rs.getString("clothingID"));
				c.setNumber(rs.getInt("number"));
				
				list.add(c);
			}
			
			if (pstm != null) {
				pstm.close();
			}
			if (rs != null) {
				rs.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SQL”Ôæ‰÷¥––“Ï≥££∫" + sql);
			e.printStackTrace();
		}
		
		return list;

	}

}
