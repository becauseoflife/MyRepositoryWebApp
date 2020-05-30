package com.dao;

import java.util.List;

import com.entity.Order;

/* �������ݿ���� */
public interface OrderDao {

	public boolean insert(Order order);							// �½�����
	public List<Order> queryOrderListByState(int state);		// ��ȡδ����Ķ���
	public boolean updateForState(String order_id, int state);	// ���´�����״̬
	
}
