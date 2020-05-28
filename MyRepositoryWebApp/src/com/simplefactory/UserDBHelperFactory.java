package com.simplefactory;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;

/* 简单工厂方法，创建用户数据库操作类 */
public class UserDBHelperFactory {
	
	public static UserDao getUserDBHelperDao(){
		
		return new UserDaoImpl();
	}
	
}
