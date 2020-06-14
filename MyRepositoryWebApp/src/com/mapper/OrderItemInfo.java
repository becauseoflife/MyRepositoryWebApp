package com.mapper;

import java.util.Date;

// ������Ϣ��
public class OrderItemInfo {

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
	
	
	public OrderItemInfo() {
		
	}

	public OrderItemInfo(String order_id, String clothingID, String shelves, String location, int number, int pick_sign,
			Date pick_time) {
		super();
		this.order_id = order_id;
		this.clothingID = clothingID;
		this.shelves = shelves;
		this.location = location;
		this.number = number;
		this.pick_sign = pick_sign;
		this.pick_time = pick_time;
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
	
	public OrderItemInfo shelves(String shelves){
		this.shelves = shelves;
		return this;
	}
	
	public OrderItemInfo location(String location){
		this.location = location;
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
	
	public OrderItemInfo build(){
		return new OrderItemInfo(order_id, clothingID, shelves, location, number, pick_sign, pick_time);
	}
}
