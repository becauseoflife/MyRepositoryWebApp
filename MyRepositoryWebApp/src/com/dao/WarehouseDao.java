package com.dao;

import java.util.List;

import com.entity.ClothingInfo;

/* 仓库数据库表操作 */
public interface WarehouseDao {

	public List<ClothingInfo> queryListByClothingID(String clothingID);		//	查询服装列表
	public ClothingInfo queryByClothingID(String clothingID);				//  查询服装
	
	public boolean updateForNumber(String clothingID, String shelves, String location, int number);	// 更新服装数量
}
