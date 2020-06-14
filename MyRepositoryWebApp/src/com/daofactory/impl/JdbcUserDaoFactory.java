package com.daofactory.impl;

import com.dao.UserDao;
import com.dao.impl.JdbcUserDaoImpl;
import com.daofactory.UserDaoFactory;

/* 工厂方法模式: 创建SQL实现的用户数据操作类 */
public class JdbcUserDaoFactory implements UserDaoFactory{
	
	@Override
	public UserDao getUserDao() {
		// TODO Auto-generated method stub
		return new JdbcUserDaoImpl();
	}
	
}
