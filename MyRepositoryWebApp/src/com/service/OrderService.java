package com.service;

import java.util.List;

import com.entity.Cart;
import com.entity.Order;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;

public interface OrderService {

	public boolean createOrder(OrderInfo order, Cart cart);		// ���ɶ���
	
	public List<OrderInfo> findAllByState(int sign);			// ���Ҷ�����Ϣ
	
	public Order getOrder(String orderID);						// ��ö���
	
	public boolean updateSetPickSign(OrderItemInfo orderItem);	// ���¶�����Ʒ
	
	public boolean updateSetState(String order_id, int state);	// ���¶���״̬
	
	
	
	public boolean inportOrder(Order order);					// ��������
	
	public boolean add(OrderInfo orderInfo);					// ������Ϣ����
	
	public boolean add(OrderItemInfo itemInfo);					// ������Ʒ��Ϣ����
	
	public List<OrderItemInfo> findAllItemByPickSign(int pickSign);	// ��ȡȫ���Ķ�����Ʒ
	
	public boolean updateSetPickUser(OrderItemInfo itemInfo);		// ������Ա
	
	public int sumForNumber(String pick_user, int pick_sign);			// ��ȡ�����Ա���������
	
	public List<OrderItemInfo> findAllItemByPickUser(String pickUser, int pick_sign);	// ��ȡ�������
	
	public List<OrderInfo> findAllOrderInfo();		// �������ж���
	
	
	
}
