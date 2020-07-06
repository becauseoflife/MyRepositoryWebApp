package com.dao;

import java.util.List;

import com.mapper.CheckTaskRecord;

public interface CheckRecordsDao {

	public boolean add(CheckTaskRecord taskRecord);
	
	public boolean update(CheckTaskRecord taskRecord);
	
	public List<CheckTaskRecord> findAllById(String id);	// 
	
	public CheckTaskRecord findByIdAndPosition(String id, String position);
	
	public boolean deleteById(String id);
	
}
