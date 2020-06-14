package com.daofactory.impl;

import com.dao.WarehouseDao;
import com.dao.impl.JdbcWarehouseDaoImpl;
import com.daofactory.WarehouseDaoFactory;

/* 工厂方法模式： 产生SQL仓库数据操作类*/
public class JdbcWarehouseDaoFactory implements WarehouseDaoFactory {

	@Override
	public WarehouseDao getWarehouseDao() {
		// TODO Auto-generated method stub
		return new JdbcWarehouseDaoImpl();
	}

}
