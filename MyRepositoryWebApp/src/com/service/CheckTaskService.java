package com.service;

import java.util.List;

import com.entity.CheckTaskInfo;
import com.mapper.CheckTask;
import com.mapper.CheckTaskRecord;

public interface CheckTaskService {

	public boolean addCheckTask(CheckTask task);
	
	public List<CheckTask> findAllTaskByUsername(String username);
	
	public List<CheckTask> findAllTask();
	
	public boolean deleteTaskById(String id);
	
	public List<CheckTask> findAllTaskIsValid(String username);
	
	public CheckTask findTaskById(String id);
	
	
	public boolean addTaskRecord(CheckTaskRecord taskRecord);
	
	public List<CheckTaskRecord> findAllTaskRecordById(String id);
	
	public boolean updateTaskRecord(CheckTaskRecord taskRecord);
	
	public CheckTaskRecord findByIdAndPosition(String id, String position);
	
	public boolean deleteTaskRecordById(String id);
	
}
