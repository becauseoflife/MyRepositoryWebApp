package com.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

	public boolean login(HttpServletRequest request);  		// 用户登录
	public boolean register(HttpServletRequest request);	// 用户注册
	
	public void queryClothing(HttpServletRequest request);	// 查询服装位置和数量
	
	public void addToCart(HttpServletRequest request, HttpServletResponse response);		// 添加商品到订单中
	public void deleteFormCart(HttpServletRequest request, HttpServletResponse response);	// 从订单中删除商品
	public void createOrder(HttpServletRequest request, HttpServletResponse response);		// 创建订单
}
