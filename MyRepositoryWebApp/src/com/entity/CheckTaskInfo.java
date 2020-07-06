package com.entity;

import java.util.List;

import com.mapper.CheckTask;
import com.mapper.CheckTaskRecord;

public class CheckTaskInfo {

	private CheckTask checkTask;
	
	private List<CheckTaskRecord> taskRecords;

	public CheckTaskInfo(CheckTask checkTask, List<CheckTaskRecord> taskRecords) {
		super();
		this.checkTask = checkTask;
		this.taskRecords = taskRecords;
	}

	public CheckTask getCheckTask() {
		return checkTask;
	}

	public void setCheckTask(CheckTask checkTask) {
		this.checkTask = checkTask;
	}

	public List<CheckTaskRecord> getTaskRecords() {
		return taskRecords;
	}

	public void setTaskRecords(List<CheckTaskRecord> taskRecords) {
		this.taskRecords = taskRecords;
	}
	
	
	
}
