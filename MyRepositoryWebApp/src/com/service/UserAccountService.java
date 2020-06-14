package com.service;

import java.sql.SQLException;
import java.util.List;

import com.mapper.Admin;
import com.mapper.UserInfo;

public interface UserAccountService {
	
	public UserInfo findByName(String userName) throws SQLException;  		// �û���¼
	public boolean add(UserInfo userInfo) throws SQLException;				// �û�ע��
	
	public void outLogin();		// �˳���¼
	
	public List<UserInfo> findAll();
	
	public boolean updateByName(String username, UserInfo user);	// �����û�
	
	public boolean deleteByName(String username);					// ɾ���û�
	
}
