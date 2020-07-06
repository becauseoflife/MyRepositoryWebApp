package com.daofactory.impl;

import com.dao.CheckTaskDao;
import com.dao.impl.JdbcCheckTaskDaoImpl;
import com.daofactory.CheckTaskDaoFactory;

public class JdbcCheckTaskDaoFactory implements CheckTaskDaoFactory {

	@Override
	public CheckTaskDao getCheckTaskDao() {
		// TODO Auto-generated method stub
		return new JdbcCheckTaskDaoImpl();
	}

}
