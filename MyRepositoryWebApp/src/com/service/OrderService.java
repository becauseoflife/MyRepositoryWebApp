package com.service;

import java.util.List;

import com.entity.Cart;
import com.entity.Order;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;

public interface OrderService {

	public boolean createOrder(OrderInfo order, Cart cart);		// 生成订单
	
	public List<OrderInfo> findAllByState(int sign);			// 查找订单信息
	
	public Order getOrder(String orderID);						// 获得订单
	
	public boolean updateSetPickSign(OrderItemInfo orderItem);	// 更新订单商品
	
	public boolean updateSetState(String order_id, int state);	// 更新订单状态
	
	
	
	public boolean inportOrder(Order order);					// 订单导入
	
	public boolean add(OrderInfo orderInfo);					// 订单信息导入
	
	public boolean add(OrderItemInfo itemInfo);					// 订单商品信息导入
	
	public List<OrderItemInfo> findAllItemByPickSign(int pickSign);	// 获取全部的订购商品
	
	public boolean updateSetPickUser(OrderItemInfo itemInfo);		// 分配人员
	
	public int sumForNumber(String pick_user, int pick_sign);			// 获取拣货人员分配的数量
	
	public List<OrderItemInfo> findAllItemByPickUser(String pickUser, int pick_sign);	// 获取拣货任务
	
	public List<OrderInfo> findAllOrderInfo();		// 查找所有订单
	
	
	
}
