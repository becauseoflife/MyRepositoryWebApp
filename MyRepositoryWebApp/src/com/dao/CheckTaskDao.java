package com.dao;

import java.util.List;

import com.mapper.CheckTask;

public interface CheckTaskDao {

	public boolean add(CheckTask task);
	
	public List<CheckTask> findAllByUsername(String username);
	
	public List<CheckTask> findAll();
	
	public boolean deleteById(String id);		
	
	public List<CheckTask> findAllTaskIsValid(String username);
	
	public CheckTask findById(String id);
}
