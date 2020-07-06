package com.entity;

import java.io.Serializable;

// �̵���
public class Check implements Serializable{

	private String clothingID;		// ��װID
	
	private String position;		// λ��
	
	private int number;				// ����
	
	private int checkNum;			// �̵�����
	
	private int surplus;			// ��ӯ
	
	private int loss;				// �̿�


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

	
	// ��д����
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
		
		return "λ�ã�" + position + " �̵㣺" + checkNum;
	}
	
}
