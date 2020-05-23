package com.entity;

/* 服装信息实体类 */
public class ClothingInfo {

	private String shelves;		// 货架
	
	private String location;	// 货位
	
	private String clothingID;  // 服装ID
	
	private int number;			// 数量

	
	
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
	
}
