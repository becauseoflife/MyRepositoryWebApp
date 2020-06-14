package com.daofactory.impl;

import com.dao.AdminDao;
import com.dao.impl.JdbcAdminDaoImpl;
import com.daofactory.AdminDaoFactory;

public class JdbcAdminDaoFactory implements AdminDaoFactory {

	@Override
	public AdminDao getAdminDao() {
		// TODO Auto-generated method stub
		return new JdbcAdminDaoImpl();
	}

}
