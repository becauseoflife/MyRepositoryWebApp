package com.daofactory.impl;

import com.dao.UserDao;
import com.dao.impl.JdbcUserDaoImpl;
import com.daofactory.UserDaoFactory;

/* ��������ģʽ: ����SQLʵ�ֵ��û����ݲ����� */
public class JdbcUserDaoFactory implements UserDaoFactory{
	
	@Override
	public UserDao getUserDao() {
		// TODO Auto-generated method stub
		return new JdbcUserDaoImpl();
	}
	
}
