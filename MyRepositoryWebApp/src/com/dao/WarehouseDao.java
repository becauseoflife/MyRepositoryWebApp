package com.dao;

import java.util.List;

import com.entity.ClothingInfo;

/* �ֿ����ݿ����� */
public interface WarehouseDao {

	public List<ClothingInfo> queryListByClothingID(String clothingID);		//	��ѯ��װ�б�
	public ClothingInfo queryByClothingID(String clothingID);				//  ��ѯ��װ
	
}
