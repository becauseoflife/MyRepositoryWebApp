package com.daofactory.impl;

import com.dao.CheckRecordsDao;
import com.dao.impl.JdbcCheckRecordsDaoImpl;
import com.daofactory.CheckRecordsDaoFactory;

public class JdbcCheckRecordsDaoFactory implements CheckRecordsDaoFactory{

	@Override
	public CheckRecordsDao getCheckRecordsDao() {
		// TODO Auto-generated method stub
		return new JdbcCheckRecordsDaoImpl();
	}

}
