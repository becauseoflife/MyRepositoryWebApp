package com.entity;

import java.util.List;

import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;

/* ����ʵ�� */
public class Order {

	private OrderInfo orderInfo;				// ������Ϣ
	
	private List<OrderItemInfo> goodsList;		// ������Ʒ

	public Order(OrderInfo orderInfo, List<OrderItemInfo> goodsList) {
		super();
		this.orderInfo = orderInfo;
		this.goodsList = goodsList;
	}

	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public List<OrderItemInfo> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<OrderItemInfo> goodsList) {
		this.goodsList = goodsList;
	}

}
