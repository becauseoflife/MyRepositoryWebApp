package com.daofactory;

import com.dao.WarehouseDao;

/* 工厂方法模式：产生仓库数据操作类 */
public interface WarehouseDaoFactory {

	public WarehouseDao getWarehouseDao();
	
}
