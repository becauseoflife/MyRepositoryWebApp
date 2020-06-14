package com.entity;

import java.util.List;

import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;

/* 订单实体 */
public class Order {

	private OrderInfo orderInfo;				// 订单信息
	
	private List<OrderItemInfo> goodsList;		// 订购商品

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
