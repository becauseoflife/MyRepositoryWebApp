package com.dao;

import java.util.List;

import com.mapper.OrderInfo;

/* �������ݿ���� */
public interface OrderInfoDao {

	public boolean add(OrderInfo orderInfo);					// �½�����
	public OrderInfo findById(String orderID);					// ��ö�������
	public List<OrderInfo> findAllByState(int state);			// ��ȡδ����Ķ���
	public boolean updateSetState(String order_id, int state);	// ���´�����״̬
	
	public  List<OrderInfo> findAll();		// �������ж���
}
