package com.dao;

import java.util.List;

import com.mapper.OrderInfo;

/* 订单数据库操作 */
public interface OrderInfoDao {

	public boolean add(OrderInfo orderInfo);					// 新建订单
	public OrderInfo findById(String orderID);					// 获得订单详情
	public List<OrderInfo> findAllByState(int state);			// 获取未处理的订单
	public boolean updateSetState(String order_id, int state);	// 更新处理订单状态
	
	public  List<OrderInfo> findAll();		// 查找所有订单
}
