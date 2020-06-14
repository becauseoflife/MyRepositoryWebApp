package com.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.enterprise.inject.New;

import com.dao.AdminDao;
import com.dao.UserDao;
import com.daofactory.impl.JdbcAdminDaoFactory;
import com.daofactory.impl.JdbcUserDaoFactory;
import com.mapper.Admin;
import com.mapper.UserInfo;
import com.service.UserAccountService;

public class UserAccountServiceImpl implements UserAccountService{

	// 获取用户数据库操作类
	private UserDao userDBHelper = new JdbcUserDaoFactory().getUserDao();

	@Override
	public UserInfo findByName(String userName) throws SQLException {
		// TODO Auto-generated method stub
		return userDBHelper.findByName(userName);
	}

	@Override
	public boolean add(UserInfo userInfo) throws SQLException {
		// TODO Auto-generated method stub
		return userDBHelper.add(userInfo);
	}

	@Override
	public void outLogin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<UserInfo> findAll() {
		// TODO Auto-generated method stub
		return userDBHelper.findAll();
	}

	@Override
	public boolean updateByName(String username, UserInfo user) {
		// TODO Auto-generated method stub
		return userDBHelper.updateByName(username, user);
	}

	@Override
	public boolean deleteByName(String username) {
		// TODO Auto-generated method stub
		return userDBHelper.deleteByName(username);
	}

}
