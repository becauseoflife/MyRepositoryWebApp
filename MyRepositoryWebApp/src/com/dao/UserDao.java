package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.mapper.UserInfo;

/* 用户数据库的操作 */
public interface UserDao {

	public boolean add(UserInfo userInfo) throws SQLException;			// 插入

	public UserInfo findByName(String userName) throws SQLException;	// 查询
	
	public List<UserInfo>  findAll();	// 查询全部用户
	
	public boolean updateByName(String username, UserInfo user);	// 更新
	
	public boolean deleteByName(String username);		// 删除
	
}
