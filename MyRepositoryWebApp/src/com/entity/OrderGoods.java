package com.entity;

import java.util.Date;

// ������Ϣ��
public class OrderGoods {

	public static final int NOT_PICK = 0;		// δ���
	public static final int PICK = 1;			// �Ѽ��
	public static final int PICK_FAILED = 2;	// ���ʧ��
	
	private String order_id;		// ����ID
	
	private String clothingID;		// ��װID
	
	private String shelves;			// ����
	
	private String location;		// ��λ
	
	private int number;				// ����
	
	private int pick_sign;			// �����־
	
	private Date pick_time;			// �����ʱ��

	
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getClothingID() {
		return clothingID;
	}

	public void setClothingID(String clothingID) {
		this.clothingID = clothingID;
	}
	
	public String getShelves() {
		return shelves;
	}

	public void setShelves(String shelves) {
		this.shelves = shelves;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPick_sign() {
		return pick_sign;
	}

	public void setPick_sign(int pick_sign) {
		this.pick_sign = pick_sign;
	}

	public Date getPick_time() {
		return pick_time;
	}

	public void setPick_time(Date pick_time) {
		this.pick_time = pick_time;
	}
	
}
