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
	
	public void getOrderSelectOptions(HttpServletRequest request, HttpServletResponse response);	// 获取未处理订单列表
	public void getOrderInfo(HttpServletRequest request, HttpServletResponse response);				// 获取订单详情
	public void pickGood(HttpServletRequest request, HttpServletResponse response);					// 拣货操作
	public void orderResolved(HttpServletRequest request, HttpServletResponse response);			// 处理订单完成
}
