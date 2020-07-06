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

import com.dao.CheckRecordsDao;
import com.mapper.CheckTaskRecord;
import com.util.DBConn;

public class JdbcCheckRecordsDaoImpl implements CheckRecordsDao{

	@Override
	public boolean add(CheckTaskRecord taskRecord) {
		// TODO Auto-generated method stub
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "INSERT INTO `check_task_records`"
				+ "(`id`, `clothingID`, `position`, `number`, `check_num`, `surplus`, `loss`, `check_time`)"
				+ " VALUES "
				+ "(?, ?, ?, ?, ?, ?, ?, ?);";
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String checkTime  = df.format(taskRecord.getCheck_time());
			
			pstm.setString(1, taskRecord.getId());
			pstm.setString(2, taskRecord.getClothingID());
			pstm.setString(3, taskRecord.getPosition());
			pstm.setInt(4, taskRecord.getNumber());
			pstm.setInt(5, taskRecord.getCheckNum());
			pstm.setInt(6, taskRecord.getSurplus());
			pstm.setInt(7, taskRecord.getLoss());
			pstm.setString(8, checkTime);
			
			flag = pstm.executeUpdate();
			
			if (pstm != null) {
				pstm.close();
			}
			
		} catch (Exception e) {
			return false;
		} 
		
		// ≤Â»Î≥…π¶
		if(flag != 0)
			return true;	
		return false;

	}

	@Override
	public boolean update(CheckTaskRecord taskRecord) {
		// TODO Auto-generated method stub
		
		int flag = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "update `check_task_records` set "
				+ "`clothingID`=?, `number`=?, `check_num`=?, `surplus`=?, `loss`=?, `check_time`=?"
				+ "where `id`=? AND `position`=?";
		
		try {
			conn = DBConn.getConnection();
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String checkTime  = df.format(taskRecord.getCheck_time());
			
			pstm = conn.prepareStatement(sql);

			pstm.setString(1, taskRecord.getClothingID());
			pstm.setInt(2, taskRecord.getNumber());
			pstm.setInt(3, taskRecord.getCheckNum());
			pstm.setInt(4, taskRecord.getSurplus());
			pstm.setInt(5, taskRecord.getLoss());
			pstm.setString(6, checkTime);
			
			pstm.setString(7, taskRecord.getId());
			pstm.setString(8, taskRecord.getPosition());
			
			flag = pstm.executeUpdate();
			
			if (pstm != null) {
				pstm.close();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("SQL”Ôæ‰£∫" + sql);
			e.printStackTrace();
		}
		if(flag==1)
			return true;
		return false;
	}

	@Override
	public List<CheckTaskRecord> findAllById(String id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		List<CheckTaskRecord> list = new ArrayList<CheckTaskRecord>();
		String sql = "SELECT * FROM `check_task_records` where `id`=?;";
		
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			
			rs = pstm.executeQuery();
			while(rs.next())
			{
				Timestamp timestamp = rs.getTimestamp("check_time");
				
				CheckTaskRecord taskRecord = CheckTaskRecord.builder()
						.id(id)
						.clothingID(rs.getString("clothingID"))
						.position(rs.getString("position"))
						.number(rs.getInt("number"))
						.checkNum(rs.getInt("check_num"))
						.surplus(rs.getInt("surplus"))
						.loss(rs.getInt("loss"))
						.check_time(timestamp)
						.build();
				
/*				taskRecord.setId(id);
				taskRecord.setClothingID(rs.getString("clothingID"));
				taskRecord.setPosition(rs.getString("position"));
				taskRecord.setNumber(rs.getInt("number"));
				taskRecord.setCheckNum(rs.getInt("check_num"));
				taskRecord.setSurplus(rs.getInt("surplus"));
				taskRecord.setLoss(rs.getInt("loss"));
				

				taskRecord.setCheck_time(timestamp);*/
				
				list.add(taskRecord);
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
			return list;
		}
		
		return list;
	}

	@Override
	public CheckTaskRecord findByIdAndPosition(String id, String position) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CheckTaskRecord record = null;
		String sql = "SELECT * FROM `check_task_records` where `id`=? AND `position`=?;";
		
		
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			pstm.setString(2, position);
			
			rs = pstm.executeQuery();
			if(rs.next())
			{
				Timestamp timestamp = rs.getTimestamp("check_time");
				
				record =  CheckTaskRecord.builder()
						.id(id)
						.clothingID(rs.getString("clothingID"))
						.position(rs.getString("position"))
						.number(rs.getInt("number"))
						.checkNum(rs.getInt("check_num"))
						.surplus(rs.getInt("surplus"))
						.loss(rs.getInt("loss"))
						.check_time(timestamp)
						.build();
				
/*				record.setId(id);
				record.setClothingID(rs.getString("clothingID"));
				record.setPosition(rs.getString("position"));
				record.setNumber(rs.getInt("number"));
				record.setCheckNum(rs.getInt("check_num"));
				record.setSurplus(rs.getInt("surplus"));
				record.setLoss(rs.getInt("loss"));
				

				record.setCheck_time(timestamp);*/
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
			return record;
		}
		
		return record;
	}

	@Override
	public boolean deleteById(String id) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement pstm = null;
		String sql = "delete * FROM `check_task_records` where `id`=?;";
		try {
			conn = DBConn.getConnection();
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, id);
			
			pstm.executeQuery();

			if (pstm != null) {
				pstm.close();
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
