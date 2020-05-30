package com.entity;

import java.util.HashMap;

/* 订单列表  */
public class OrderTable {

	private HashMap<String, OrderGoods> goodsHashMap;		// 订单列表
	
	// 构造函数
	public OrderTable(){
		this.goodsHashMap = new HashMap<String, OrderGoods>();
	}

	public HashMap<String, OrderGoods> getGoodsHashMap() {
		return goodsHashMap;
	}

	public void setGoodsHashMap(HashMap<String, OrderGoods> goodsHashMap) {
		this.goodsHashMap = goodsHashMap;
	}

	// 拣货
	
	
}
