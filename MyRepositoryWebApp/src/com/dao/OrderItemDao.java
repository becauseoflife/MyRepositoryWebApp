package com.dao;

import java.util.List;

import com.mapper.OrderItemInfo;

/* �����Ĳ�Ʒ�б����ݱ���� */
public interface OrderItemDao {

	public boolean add(OrderItemInfo orderItemInfo);				// ����
	public List<OrderItemInfo> findAllById(String orderID);			// ���Ҷ�������Ʒ�б�
	
	public boolean updateSetSign(OrderItemInfo orderItem);			// ���¼�����
	
	
	public List<OrderItemInfo> findAllByPickSign(int pickSign);		// �Ӽ����־��ȡ��Ʒ
	
	public boolean updateSetPickUser(OrderItemInfo itemInfo);		// ��������Ա
	
	
	public int sumForNumber(String pick_user, int pick_sign);		// ������Ա������
	
	public List<OrderItemInfo> findAllByPickUser(String pickUser, int pick_sign);	// ��ȡ�������
	
}
