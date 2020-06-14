package com.service;

import java.util.List;

import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;

public interface WarehouseService {
	
	public List<ClothingInfo> findAllById(String clothingID);				// 查询服装
	
	public ClothingInfo findById(String clothingID);						// 查询服装
	
	public ClothingInfo findByPosition(String shelves, String location);	//  查询服装
	
	public boolean updateSetNumber(ClothingInfo c);							// 更新仓库服装数量
	
	public List<ClothingInfo> findAllWithEmpty();							// 查询仓库中为存放商品和存放商品数量为0的信息
	
	public boolean updateSetIdAndNumber(ClothingInfo c);					// 上架更新位置的服装和数量
}
