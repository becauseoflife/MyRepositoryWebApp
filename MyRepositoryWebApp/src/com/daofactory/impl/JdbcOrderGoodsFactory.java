package com.daofactory.impl;

import com.dao.OrderItemDao;
import com.dao.impl.JdbcOrderItemDaoImpl;
import com.daofactory.OrderItemDaoFactory;

/* 工厂方法模式：产生SQL 订单货品操作类 */
public class JdbcOrderGoodsFactory implements OrderItemDaoFactory{

	@Override
	public OrderItemDao getOrderGoodsDao() {
		// TODO Auto-generated method stub
		return new JdbcOrderItemDaoImpl();
	}
	
}
