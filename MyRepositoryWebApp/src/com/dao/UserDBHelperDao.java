package com.dao;

import java.sql.SQLException;

import com.entity.User;

/* 用户数据库的操作 */
public interface UserDBHelperDao {

	public boolean insert(User user) throws SQLException;					// 插入
	//public void delete(String userName) throws SQLException; 			// 删除
	public User querryByUserName(String userName) throws SQLException;	// 查询
	
}
