package com.simplefactory;

import com.dao.UserDBHelperDao;
import com.dao.impl.UserDBGHelperDaoImpl;

/* �򵥹��������������û����ݿ������ */
public class UserDBHelperFactory {
	
	public static UserDBHelperDao getUserDBHelperDao(){
		
		return new UserDBGHelperDaoImpl();
	}
	
}
