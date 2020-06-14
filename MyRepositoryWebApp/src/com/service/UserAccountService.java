package com.service;

import java.sql.SQLException;
import java.util.List;

import com.mapper.Admin;
import com.mapper.UserInfo;

public interface UserAccountService {
	
	public UserInfo findByName(String userName) throws SQLException;  		// 用户登录
	public boolean add(UserInfo userInfo) throws SQLException;				// 用户注册
	
	public void outLogin();		// 退出登录
	
	public List<UserInfo> findAll();
	
	public boolean updateByName(String username, UserInfo user);	// 更新用户
	
	public boolean deleteByName(String username);					// 删除用户
	
}
