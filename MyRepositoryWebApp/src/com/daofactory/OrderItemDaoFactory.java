package com.daofactory;

import com.dao.OrderItemDao;

/* 工厂方法模式：产生订单货品数据操作类 */
public interface OrderItemDaoFactory {

	public OrderItemDao getOrderGoodsDao();
	
}
