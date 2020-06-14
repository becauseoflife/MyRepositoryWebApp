package com.dao;

import java.util.List;

import com.mapper.OrderItemInfo;

/* 订单的产品列表数据表操作 */
public interface OrderItemDao {

	public boolean add(OrderItemInfo orderItemInfo);				// 插入
	public List<OrderItemInfo> findAllById(String orderID);			// 查找订单的商品列表
	
	public boolean updateSetSign(OrderItemInfo orderItem);					// 更新拣货标记
	
}
