package com.dao;

import com.entity.Order;

/* 订单数据库操作 */
public interface OrderDao {

	public boolean insert(Order order);		// 新建订单
	
}
