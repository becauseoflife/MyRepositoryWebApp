package com.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dao.WarehouseDao;
import com.entity.ClothingInfo;
import com.util.DBConn;

public class WarehouseDaoImpl implements WarehouseDao {

	@Override
	public List<ClothingInfo> queryListByClothingID(String clothingID) {
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
			return list;
/*			System.out.println("SQL”Ôæ‰÷¥––“Ï≥££∫" + sql);
			e.printStackTrace();*/
		}
		
		return list;

	}

	@Override
	public ClothingInfo queryByClothingID(String clothingID) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ClothingInfo result = null;
		String sql = "select * from warehouse where `clothingID`='" + clothingID + "'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);

			rs = pstm.executeQuery();
			if (rs.next()) {
				result = new ClothingInfo();
				result.setShelves(rs.getString("shelves"));
				result.setLocation(rs.getString("location"));
				result.setClothingID(rs.getString("clothingID"));
				result.setNumber(rs.getInt("number"));
			}
			
			if (pstm != null) {
				pstm.close();
			}
			if (rs != null) {
				rs.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return result;
/*			System.out.println("SQL”Ôæ‰÷¥––“Ï≥££∫" + sql);
			e.printStackTrace();*/
		}
		return result;
	}

	@Override
	public boolean updateForNumber(String clothingID, String shelves, String location, int number) {
		// TODO Auto-generated method stub
		
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "update `warehouse` set `number`=? where "
				+ "`shelves`='"+shelves+"' AND"
				+ " `location`='"+location+"' AND "
				+ "clothingID='"+clothingID+"'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, number);
			
			flag = pstm.executeUpdate();
			
			if (pstm != null) {
				pstm.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag != 0)
			return true;
		return false;
	}

	@Override
	public ClothingInfo queryByLocation(String shelves, String location) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		ClothingInfo result = null;
		String sql = "select * from warehouse where `shelves`='" + shelves + "' AND `location`='" + location + "'" ;
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				result = new ClothingInfo();
				result.setShelves(rs.getString("shelves"));
				result.setLocation(rs.getString("location"));
				result.setClothingID(rs.getString("clothingID"));
				result.setNumber(rs.getInt("number"));
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

		return result;
	}

	@Override
	public List<ClothingInfo> queryListEmptyPosition() {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<ClothingInfo> list = new ArrayList<ClothingInfo>();
		String sql = "SELECT * FROM `warehouse` where `number` is null or `number`=0;";
		
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
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return list;
			//e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public boolean updateForPutOnGood(String clothingID, String shelves, String location, int number) {
		// TODO Auto-generated method stub
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "update `warehouse` set `clothingID`=?, `number`=? where "
				+ "`shelves`='"+shelves+"' AND"
				+ " `location`='"+location+"'";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, clothingID);
			pstm.setInt(2, number);
			
			flag = pstm.executeUpdate();
			
			if (pstm != null) {
				pstm.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag != 0)
			return true;
		return false;
	}

}
