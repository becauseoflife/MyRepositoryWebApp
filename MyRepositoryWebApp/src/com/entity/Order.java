package com.entity;

import java.util.Date;

// 订单类
public class Order {
	// 订单状态值
	public static final int UNRESOLVED = 0;			// 未处理
	public static final int RESOLVED = 1;			// 已处理
	
	private String order_id;		// 订单ID
	
	private String username;		// 创建的用户
	
	private int state;				// 订单状态
	
	private Date create_time;		// 订单创建时间

	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
}
