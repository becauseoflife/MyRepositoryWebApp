package com.dao;

import java.sql.SQLException;

import com.entity.User;

/* �û����ݿ�Ĳ��� */
public interface UserDBHelperDao {

	public void insert(User user) throws SQLException;					// ����
	//public void delete(String userName) throws SQLException; 			// ɾ��
	public User querryByUserName(String userName) throws SQLException;	// ��ѯ
	
}
