package com.dao;

import java.util.List;

import com.mapper.ClothingInfo;

/* 仓库数据库表操作 */
public interface WarehouseDao {

	public List<ClothingInfo> findAllById(String clothingID);				//	查询服装列表
	public ClothingInfo findById(String clothingID);						//  查询服装
	public ClothingInfo findByPosition(String shelves, String location);	//  查询服装
	
	public boolean updateSetNumber(ClothingInfo c);							// 更新服装
	
	public List<ClothingInfo> findAllWithEmpty();							// 查询仓库中为存放商品和存放商品数量为0的信息
	public boolean updateSetIdAndNumber(ClothingInfo c);					// 上架更新位置的服装和数量
	
}
