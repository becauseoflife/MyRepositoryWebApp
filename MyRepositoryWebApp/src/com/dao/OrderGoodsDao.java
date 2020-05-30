package com.dao;

import java.util.List;

import com.entity.OrderGoods;

/* �����Ĳ�Ʒ�б����ݱ���� */
public interface OrderGoodsDao {

	public boolean insert(OrderGoods orderGoods);					// ����
	public List<OrderGoods> queryByOrderID(String orderID);			// ���Ҷ�������Ʒ�б�
	
	public boolean updateForPickSign(String orderID, String clothingID, int pick_sign, String pick_time);		// ���¼�����
	
}
