package com.simplefactory;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;

/* �򵥹��������������û����ݿ������ */
public class UserDBHelperFactory {
	
	public static UserDao getUserDBHelperDao(){
		
		return new UserDaoImpl();
	}
	
}
