package com.entity;

import java.util.HashMap;

/* �����б�  */
public class OrderTable {

	private HashMap<String, OrderGoods> goodsHashMap;		// �����б�
	
	// ���캯��
	public OrderTable(){
		this.goodsHashMap = new HashMap<String, OrderGoods>();
	}

	public HashMap<String, OrderGoods> getGoodsHashMap() {
		return goodsHashMap;
	}

	public void setGoodsHashMap(HashMap<String, OrderGoods> goodsHashMap) {
		this.goodsHashMap = goodsHashMap;
	}

	// ���
	
	
}
