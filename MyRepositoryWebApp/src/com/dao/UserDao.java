package com.dao;

import javax.servlet.http.HttpServletRequest;

/* �û�ҵ���߼��� */
public interface UserDao {

	public boolean login(HttpServletRequest request);  		// �û���¼
	public boolean register(HttpServletRequest request);	// �û�ע��
	
}
