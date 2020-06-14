package com.daofactory;

import com.dao.OrderInfoDao;

/* 工厂方法模式： 创建订单数据的操作类 */
public interface OrderInfoDaoFactory {

	public OrderInfoDao getOrderDao();
	
}
