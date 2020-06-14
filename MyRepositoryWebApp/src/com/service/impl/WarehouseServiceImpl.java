package com.service.impl;

import java.util.List;

import com.dao.WarehouseDao;
import com.daofactory.impl.JdbcWarehouseDaoFactory;
import com.mapper.ClothingInfo;
import com.service.WarehouseService;

public class WarehouseServiceImpl implements WarehouseService{

	// ²Ö¿â±í²Ù×÷Àà
	private WarehouseDao warehouseDBHelper = new JdbcWarehouseDaoFactory().getWarehouseDao();
	
	@Override
	public List<ClothingInfo> findAllById(String clothingID) {
		// TODO Auto-generated method stub
		return warehouseDBHelper.findAllById(clothingID);
	}

	@Override
	public ClothingInfo findById(String clothingID) {
		// TODO Auto-generated method stub
		return warehouseDBHelper.findById(clothingID);
	}

	@Override
	public boolean updateSetNumber(ClothingInfo c) {
		// TODO Auto-generated method stub
		return warehouseDBHelper.updateSetNumber(c);
	}

	@Override
	public ClothingInfo findByPosition(String shelves, String location) {
		// TODO Auto-generated method stub
		return warehouseDBHelper.findByPosition(shelves, location);
	}

	@Override
	public List<ClothingInfo> findAllWithEmpty() {
		// TODO Auto-generated method stub
		return warehouseDBHelper.findAllWithEmpty();
	}

	@Override
	public boolean updateSetIdAndNumber(ClothingInfo c) {
		// TODO Auto-generated method stub
		return warehouseDBHelper.updateSetIdAndNumber(c);
	}

}
