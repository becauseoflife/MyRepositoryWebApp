package com.dao;

import java.util.List;

import com.mapper.OrderItemInfo;

/* �����Ĳ�Ʒ�б����ݱ���� */
public interface OrderItemDao {

	public boolean add(OrderItemInfo orderItemInfo);				// ����
	public List<OrderItemInfo> findAllById(String orderID);			// ���Ҷ�������Ʒ�б�
	
	public boolean updateSetSign(OrderItemInfo orderItem);					// ���¼�����
	
}
