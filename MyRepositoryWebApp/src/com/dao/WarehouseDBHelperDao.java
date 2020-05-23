package com.dao;

import java.util.List;

import com.entity.ClothingInfo;

/* 仓库数据库表操作 */
public interface WarehouseDBHelperDao {

	public List<ClothingInfo> queryByClothingID(String clothingID);	//	查询
	
}
