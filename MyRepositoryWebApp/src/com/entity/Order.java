package com.entity;

import java.util.Date;

// ������
public class Order {
	// ����״ֵ̬
	public static final int UNRESOLVED = 0;			// δ����
	public static final int RESOLVED = 1;			// �Ѵ���
	
	private String order_id;		// ����ID
	
	private String username;		// �������û�
	
	private int state;				// ����״̬
	
	private Date create_time;		// ��������ʱ��

	
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
