package com.simplefactory;

import com.dao.UserDBHelperDao;
import com.dao.impl.UserDBGHelperDaoImpl;

/* 简单工厂方法，创建用户数据库操作类 */
public class UserDBHelperFactory {
	
	public static UserDBHelperDao getUserDBHelperDao(){
		
		return new UserDBGHelperDaoImpl();
	}
	
}
