package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.mapper.UserInfo;

/* �û����ݿ�Ĳ��� */
public interface UserDao {

	public boolean add(UserInfo userInfo) throws SQLException;			// ����

	public UserInfo findByName(String userName) throws SQLException;	// ��ѯ
	
	public List<UserInfo>  findAll();	// ��ѯȫ���û�
	
	public boolean updateByName(String username, UserInfo user);	// ����
	
	public boolean deleteByName(String username);		// ɾ��
	
}
