package com.mapper;

/* ��װ��Ϣʵ���� */
public class ClothingInfo {

	private String shelves;		// ����
	
	private String location;	// ��λ
	
	private String clothingID;  // ��װID
	
	private int number;			// ����

	
	
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


	public ClothingInfo(String shelves, String location, String clothingID, int number) {
		super();
		this.shelves = shelves;
		this.location = location;
		this.clothingID = clothingID;
		this.number = number;
	}

	public ClothingInfo() {
		super();
	}

	public static ClothingInfo builder(){
		return new ClothingInfo();
	}
	
	public ClothingInfo shelves(String shelves){
		this.shelves = shelves;
		return this;
	}
	
	public ClothingInfo location(String location){
		this.location = location;
		return this;
	}
	
	public ClothingInfo clothingID(String clothingID){
		this.clothingID = clothingID;
		return this;
	}
	
	public ClothingInfo number(int number){
		this.number = number;
		return this;
	}
	
	public ClothingInfo build(){
		return new ClothingInfo(shelves, location, clothingID, number);
	}
	
	// ��д����
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		int result = 17;
		result = 31 * result + (clothingID == null ? 0 : clothingID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(this==obj){
			return true;
		}
		if(obj instanceof ClothingInfo)
		{
			ClothingInfo c = (ClothingInfo)obj;
			if(this.clothingID.equals(c.getClothingID()))
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
		return this.shelves + "-" + this.location;
	}
	
	
	
}
