package com.mapper;

import java.io.Serializable;
import java.util.Date;

import com.entity.Check;

public class CheckTaskRecord implements Serializable{
	
	private String id;
	
	private String clothingID;		// 服装ID
	
	private String position;		// 位置
	
	private int number;				// 数量
	
	private int checkNum;			// 盘点数量
	
	private int surplus;			// 盘盈
	
	private int loss;				// 盘亏
	
	private Date check_time;		// 盘点时间

	
	
	public CheckTaskRecord() {}

	public CheckTaskRecord(String id, String clothingID, String position, int number, int checkNum, int surplus,
			int loss, Date check_time) {
		super();
		this.id = id;
		this.clothingID = clothingID;
		this.position = position;
		this.number = number;
		this.checkNum = checkNum;
		this.surplus = surplus;
		this.loss = loss;
		this.check_time = check_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setPosition(String position) {
		this.position = position;
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

	public Date getCheck_time() {
		return check_time;
	}

	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}
	
	
	public static CheckTaskRecord builder(){
		return new CheckTaskRecord();
	}
	
	public CheckTaskRecord id(String id)
	{
		this.id = id;
		return this;
	}
	
	public CheckTaskRecord clothingID(String clothingID)
	{
		this.clothingID = clothingID;
		return this;
	}
	
	public CheckTaskRecord position(String position)
	{
		this.position = position;
		return this;
	}
	
	public CheckTaskRecord number(int number)
	{
		this.number = number;
		return this;
	}
	
	public CheckTaskRecord checkNum(int checkNum)
	{
		this.checkNum = checkNum;
		return this;
	}
	
	public CheckTaskRecord surplus(int surplus)
	{
		this.surplus = surplus;
		return this;
	}
	
	public CheckTaskRecord loss(int loss)
	{
		this.loss = loss;
		return this;
	}
	
	public CheckTaskRecord check_time(Date check_time)
	{
		this.check_time = check_time;
		return this;
	}
	
	public CheckTaskRecord build(){
		return new CheckTaskRecord(id, clothingID, position, number, checkNum, surplus, loss, check_time);
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
		if(obj instanceof CheckTaskRecord)
		{
			CheckTaskRecord c = (CheckTaskRecord)obj;
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
