package com.service.impl;

import java.util.List;

import com.dao.CheckRecordsDao;
import com.dao.CheckTaskDao;
import com.dao.impl.JdbcCheckRecordsDaoImpl;
import com.dao.impl.JdbcCheckTaskDaoImpl;
import com.daofactory.impl.JdbcCheckRecordsDaoFactory;
import com.daofactory.impl.JdbcCheckTaskDaoFactory;
import com.mapper.CheckTask;
import com.mapper.CheckTaskRecord;
import com.service.CheckTaskService;

public class CheckTaskServiceImpl implements CheckTaskService{

	private CheckTaskDao checkTaskDao = new JdbcCheckTaskDaoFactory().getCheckTaskDao();
	
	private CheckRecordsDao checkRecordsDao = new JdbcCheckRecordsDaoFactory().getCheckRecordsDao();
	
	
	@Override
	public boolean addCheckTask(CheckTask task) {
		// TODO Auto-generated method stub
		return checkTaskDao.add(task);
	}


	@Override
	public List<CheckTask> findAllTaskByUsername(String username) {
		// TODO Auto-generated method stub
		return checkTaskDao.findAllByUsername(username);
	}


	@Override
	public List<CheckTask> findAllTask() {
		// TODO Auto-generated method stub
		return checkTaskDao.findAll();
	}


	@Override
	public boolean deleteTaskById(String id) {
		// TODO Auto-generated method stub
		return checkTaskDao.deleteById(id);
	}


	@Override
	public List<CheckTask> findAllTaskIsValid(String username) {
		// TODO Auto-generated method stub
		return checkTaskDao.findAllTaskIsValid(username);
	}


	@Override
	public CheckTask findTaskById(String id) {
		// TODO Auto-generated method stub
		return checkTaskDao.findById(id);
	}


	@Override
	public boolean addTaskRecord(CheckTaskRecord taskRecord) {
		// TODO Auto-generated method stub
		return checkRecordsDao.add(taskRecord);
	}


	@Override
	public List<CheckTaskRecord> findAllTaskRecordById(String id) {
		// TODO Auto-generated method stub
		return checkRecordsDao.findAllById(id);
	}


	@Override
	public boolean updateTaskRecord(CheckTaskRecord taskRecord) {
		// TODO Auto-generated method stub
		return checkRecordsDao.update(taskRecord);
	}


	@Override
	public CheckTaskRecord findByIdAndPosition(String id, String position) {
		// TODO Auto-generated method stub
		return checkRecordsDao.findByIdAndPosition(id, position);
	}


	@Override
	public boolean deleteTaskRecordById(String id) {
		// TODO Auto-generated method stub
		return checkRecordsDao.deleteById(id);
	}

	
	
	
}
