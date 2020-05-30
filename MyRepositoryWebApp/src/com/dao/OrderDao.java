package com.dao;

import java.util.List;

import com.entity.Order;

/* 订单数据库操作 */
public interface OrderDao {

	public boolean insert(Order order);							// 新建订单
	public List<Order> queryOrderListByState(int state);		// 获取未处理的订单
	public boolean updateForState(String order_id, int state);	// 更新处理订单状态
	
}
