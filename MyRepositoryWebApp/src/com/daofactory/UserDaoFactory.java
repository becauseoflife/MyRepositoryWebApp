package com.daofactory;

import com.dao.UserDao;

/* 工厂方法模式: 产生用户数据的操作类 */
public interface UserDaoFactory {

	public UserDao getUserDao();
	
}
