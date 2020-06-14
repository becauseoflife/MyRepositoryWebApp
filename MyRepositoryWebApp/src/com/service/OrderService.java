package com.service;

import java.util.List;

import com.entity.Cart;
import com.entity.Order;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;

public interface OrderService {

	public boolean createOrder(OrderInfo order, Cart cart);		// ���ɶ���
	
	public List<OrderInfo> findAllByState(int sign);			// ���Ҷ���
	
	public Order getOrder(String orderID);						// ��ö���
	
	public boolean update(OrderItemInfo orderItem);			    // ���¶�����Ʒ
	
	public boolean updateSetState(String order_id, int state);	// ���¶���״̬
	
}
