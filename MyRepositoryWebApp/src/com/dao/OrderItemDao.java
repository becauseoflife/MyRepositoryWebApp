package com.dao;

import java.util.List;

import com.mapper.OrderItemInfo;

/* 订单的产品列表数据表操作 */
public interface OrderItemDao {

	public boolean add(OrderItemInfo orderItemInfo);				// 插入
	public List<OrderItemInfo> findAllById(String orderID);			// 查找订单的商品列表
	
	public boolean updateSetSign(OrderItemInfo orderItem);			// 更新拣货标记
	
	
	public List<OrderItemInfo> findAllByPickSign(int pickSign);		// 从拣货标志获取商品
	
	public boolean updateSetPickUser(OrderItemInfo itemInfo);		// 分配拣货人员
	
	
	public int sumForNumber(String pick_user, int pick_sign);		// 分配人员的数量
	
	public List<OrderItemInfo> findAllByPickUser(String pickUser, int pick_sign);	// 获取拣货任务
	
}
