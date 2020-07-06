package com.entity;

import java.io.Serializable;

// 盘点类
public class Check implements Serializable{

	private String clothingID;		// 服装ID
	
	private String position;		// 位置
	
	private int number;				// 数量
	
	private int checkNum;			// 盘点数量
	
	private int surplus;			// 盘盈
	
	private int loss;				// 盘亏


	public Check(String clothingID, String location, int number, int checkNum, int surplus, int loss) {
		super();
		this.clothingID = clothingID;
		this.position = location;
		this.number = number;
		this.checkNum = checkNum;
		this.surplus = surplus;
		this.loss = loss;
	}

	public String getClothingID() {
		return clothingID;
	}

	public void setClothingID(String clothingID) {
		this.clothingID = clothingID;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String location) {
		this.position = location;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCheckNum() {
		return checkNum;
	}

	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}

	public int getSurplus() {
		return surplus;
	}

	public void setSurplus(int surplus) {
		this.surplus = surplus;
	}

	public int getLoss() {
		return loss;
	}

	public void setLoss(int loss) {
		this.loss = loss;
	}

	
	// 重写方法
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		result = 31 * result + (position == null ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj)
		{
			return true;
		}
		if(obj instanceof Check)
		{
			Check c = (Check)obj;
			if(this.position.equals(c.getPosition()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return "位置：" + position + " 盘点：" + checkNum;
	}
	
}
