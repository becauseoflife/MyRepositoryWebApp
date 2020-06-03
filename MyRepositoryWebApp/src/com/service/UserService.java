package com.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.VoiceStatus;

public interface UserService {

	public boolean login(HttpServletRequest request);  		// 用户登录
	public boolean register(HttpServletRequest request);	// 用户注册
	
	public boolean queryClothingById(HttpServletRequest request);	// 查询服装位置和数量
	
	public void addToCart(HttpServletRequest request, HttpServletResponse response);		// 添加商品到订单中
	public void deleteFormCart(HttpServletRequest request, HttpServletResponse response);	// 从订单中删除商品
	public void createOrder(HttpServletRequest request, HttpServletResponse response);		// 创建订单
	
	public void getOrderSelectOptions(HttpServletRequest request, HttpServletResponse response);	// 获取未处理订单列表
	public void getOrderInfo(HttpServletRequest request, HttpServletResponse response);				// 获取订单详情
	public void pickGood(HttpServletRequest request, HttpServletResponse response);					// 拣货操作
	public void orderResolved(HttpServletRequest request, HttpServletResponse response);			// 处理订单完成
	
	public void queryClothingByLocation(HttpServletRequest request, HttpServletResponse response);	// 盘点查询
	public void updateClothingNumber(HttpServletRequest request, HttpServletResponse response);		// 盘点更新
	public void deleteFormCheckTable(HttpServletRequest request, HttpServletResponse response);		// 盘点表的行删除
	public boolean exportExcel(HttpServletRequest request, HttpServletResponse response);				// 导出报表
	
	public void outLogin(HttpServletRequest request, HttpServletResponse response);		// 退出登录
	
	public void getEmptyPositon(HttpServletRequest request, HttpServletResponse response);	// 获得上架的空位置
	public void putOnGood(HttpServletRequest request, HttpServletResponse response);		// 物品上架
}
