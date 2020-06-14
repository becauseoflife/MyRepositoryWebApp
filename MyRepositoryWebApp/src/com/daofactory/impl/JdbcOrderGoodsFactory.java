package com.daofactory.impl;

import com.dao.OrderItemDao;
import com.dao.impl.JdbcOrderItemDaoImpl;
import com.daofactory.OrderItemDaoFactory;

/* ��������ģʽ������SQL ������Ʒ������ */
public class JdbcOrderGoodsFactory implements OrderItemDaoFactory{

	@Override
	public OrderItemDao getOrderGoodsDao() {
		// TODO Auto-generated method stub
		return new JdbcOrderItemDaoImpl();
	}
	
}
