package com.dao;

import java.sql.SQLException;

import com.entity.User;

/* �û����ݿ�Ĳ��� */
public interface UserDao {

	public boolean insert(User user) throws SQLException;				// ����
	//public void delete(String userName) throws SQLException; 			// ɾ��
	public User queryByUserName(String userName) throws SQLException;	// ��ѯ
	
}
