package com.mapper;

import java.util.Date;

// ������Ϣ��
public class OrderItemInfo {

	public static final int NOT_PICK = 0;		// δ���
	public static final int PICK = 1;			// �Ѽ��

	public static final int RETURN_PICK = 2;		// �˵����Ѽ��
	public static final int RETURN_NOT_PICK = 3;	// �˵���δ���
	
	
	private String order_id;		// ����ID
	
	private String clothingID;		// ��װID
	
	private int number;				// ����
	
	private int pick_sign;			// �����־
	
	private Date pick_time;			// �����ʱ��
	
	private String pick_user;		// �������Ա

	
	
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
	
	public String getPick_user() {
		return pick_user;
	}

	public void setPick_user(String pick_user) {
		this.pick_user = pick_user;
	}

	public OrderItemInfo() {
		
	}

	public OrderItemInfo(String order_id, String clothingID, int number, int pick_sign, Date pick_time,
			String pick_user) {
		super();
		this.order_id = order_id;
		this.clothingID = clothingID;
		this.number = number;
		this.pick_sign = pick_sign;
		this.pick_time = pick_time;
		this.pick_user = pick_user;
	}

	public static OrderItemInfo builder(){
		return new OrderItemInfo();
	}
	
	public OrderItemInfo order_id(String order_id){
		this.order_id = order_id;
		return this;
	}
	
	public OrderItemInfo clothingID(String clothingID){
		this.clothingID = clothingID;
		return this;
	}
	
	public OrderItemInfo number(int number){
		this.number = number;
		return this;
	}
	
	public OrderItemInfo pick_sign(int pick_sign){
		this.pick_sign = pick_sign;
		return this;
	}
	
	public OrderItemInfo pick_time(Date pick_time){
		this.pick_time = pick_time;
		return this;
	}
	
	public OrderItemInfo pick_user(String pick_user){
		this.pick_user = pick_user;
		return this;
	}
	
	public OrderItemInfo build(){
		return new OrderItemInfo(order_id, clothingID, number, pick_sign, pick_time, pick_user);
	}
}
