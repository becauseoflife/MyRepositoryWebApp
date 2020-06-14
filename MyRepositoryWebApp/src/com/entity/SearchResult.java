package com.entity;

import java.util.List;

public class SearchResult {

	private String clothingID;		// 服装ID
	
	private int sum;				// 服装总数
	
	private List<String> position; 	// 服装位置

	
	public SearchResult(String clothingID, int sum, List<String> position) {
		super();
		this.clothingID = clothingID;
		this.sum = sum;
		this.position = position;
	}

	public String getClothingID() {
		return clothingID;
	}

	public void setClothingID(String clothingID) {
		this.clothingID = clothingID;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public List<String> getPosition() {
		return position;
	}

	public void setPosition(List<String> position) {
		this.position = position;
	}
		
}
