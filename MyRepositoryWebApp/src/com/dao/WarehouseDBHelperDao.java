package com.dao;

import java.util.List;

import com.entity.ClothingInfo;

/* �ֿ����ݿ����� */
public interface WarehouseDBHelperDao {

	public List<ClothingInfo> queryByClothingID(String clothingID);	//	��ѯ
	
}
