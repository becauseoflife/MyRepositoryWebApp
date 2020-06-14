package com.service.impl;

import com.dao.AdminDao;
import com.daofactory.impl.JdbcAdminDaoFactory;
import com.mapper.Admin;
import com.service.AdminAccountService;

public class AdminAccountServiceImpl implements AdminAccountService {

	// ��ȡ����Ա���ݿ������
	private AdminDao adminDBHelper = new JdbcAdminDaoFactory().getAdminDao();
	
	@Override
	public Admin findByName(String username) {
		// TODO Auto-generated method stub
		return adminDBHelper.findByName(username);
	}

}
