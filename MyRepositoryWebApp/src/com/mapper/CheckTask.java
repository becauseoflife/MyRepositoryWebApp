package com.mapper;

import java.util.Date;

public class CheckTask {

	private String id;		// id
	
	private Date start_time;
	
	private Date end_time;
	
	private String shelves;
	
	private String username;

	
	public CheckTask(){}
	
	public CheckTask(String id, Date start_time, Date end_time, String shelves, String username) {
		super();
		this.id = id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.shelves = shelves;
		this.username = username;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public String getShelves() {
		return shelves;
	}

	public void setShelves(String shelves) {
		this.shelves = shelves;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	// НЈдьеп
	public static CheckTask builder(){
		return new CheckTask();
	}
	
	public CheckTask id(String id){
		this.id = id;
		return this;
	}
	
	public CheckTask start_time(Date start_time){
		this.start_time = start_time;
		return this;
	}
	
	public CheckTask end_time(Date end_time){
		this.end_time = end_time;
		return this;
	}

	public CheckTask shelves(String shelves){
		this.shelves = shelves;
		return this;
	}
	
	public CheckTask username(String username){
		this.username = username;
		return this;
	}
	
	public CheckTask build(){
		return new CheckTask(id, start_time, end_time, shelves, username);
	}
	
	
}
