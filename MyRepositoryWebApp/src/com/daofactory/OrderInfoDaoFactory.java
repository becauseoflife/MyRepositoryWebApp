package com.daofactory;

import com.dao.OrderInfoDao;

/* ��������ģʽ�� �����������ݵĲ����� */
public interface OrderInfoDaoFactory {

	public OrderInfoDao getOrderDao();
	
}
