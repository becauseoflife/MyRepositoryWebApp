package com.service;

import java.util.List;

import com.entity.Cart;
import com.entity.Order;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;

public interface OrderService {

	public boolean createOrder(OrderInfo order, Cart cart);		// 生成订单
	
	public List<OrderInfo> findAllByState(int sign);			// 查找订单
	
	public Order getOrder(String orderID);						// 获得订单
	
	public boolean update(OrderItemInfo orderItem);			    // 更新订单商品
	
	public boolean updateSetState(String order_id, int state);	// 更新订单状态
	
}
