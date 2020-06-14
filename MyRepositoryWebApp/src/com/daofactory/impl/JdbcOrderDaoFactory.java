package com.daofactory.impl;

import com.dao.OrderInfoDao;
import com.dao.impl.JdbcOrderInfoDaoImpl;
import com.daofactory.OrderInfoDaoFactory;

/* 工厂方法模式： 产生SQL订单数据的操作类*/
public class JdbcOrderDaoFactory implements OrderInfoDaoFactory {

	@Override
	public OrderInfoDao getOrderDao() {
		// TODO Auto-generated method stub
		return new JdbcOrderInfoDaoImpl();
	}


}
