package com.daofactory.impl;

import com.dao.WarehouseDao;
import com.dao.impl.JdbcWarehouseDaoImpl;
import com.daofactory.WarehouseDaoFactory;

/* ��������ģʽ�� ����SQL�ֿ����ݲ�����*/
public class JdbcWarehouseDaoFactory implements WarehouseDaoFactory {

	@Override
	public WarehouseDao getWarehouseDao() {
		// TODO Auto-generated method stub
		return new JdbcWarehouseDaoImpl();
	}

}
