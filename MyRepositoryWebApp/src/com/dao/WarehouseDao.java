package com.dao;

import java.util.List;

import com.entity.ClothingInfo;

/* �ֿ����ݿ����� */
public interface WarehouseDao {

	public List<ClothingInfo> queryListByClothingID(String clothingID);		//	��ѯ��װ�б�
	public ClothingInfo queryByClothingID(String clothingID);				//  ��ѯ��װ
	
	public boolean updateForNumber(String clothingID, String shelves, String location, int number);	// ���·�װ����
}
