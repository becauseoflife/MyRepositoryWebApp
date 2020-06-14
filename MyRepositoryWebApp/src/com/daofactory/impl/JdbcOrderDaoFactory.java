package com.daofactory.impl;

import com.dao.OrderInfoDao;
import com.dao.impl.JdbcOrderInfoDaoImpl;
import com.daofactory.OrderInfoDaoFactory;

/* ��������ģʽ�� ����SQL�������ݵĲ�����*/
public class JdbcOrderDaoFactory implements OrderInfoDaoFactory {

	@Override
	public OrderInfoDao getOrderDao() {
		// TODO Auto-generated method stub
		return new JdbcOrderInfoDaoImpl();
	}


}
