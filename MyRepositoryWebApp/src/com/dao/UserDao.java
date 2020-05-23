package com.dao;

import javax.servlet.http.HttpServletRequest;

/* 用户业务逻辑类 */
public interface UserDao {

	public boolean login(HttpServletRequest request);  		// 用户登录
	public boolean register(HttpServletRequest request);	// 用户注册
	
	public void queryClothing(HttpServletRequest request);	// 查询服装位置和数量 
}
