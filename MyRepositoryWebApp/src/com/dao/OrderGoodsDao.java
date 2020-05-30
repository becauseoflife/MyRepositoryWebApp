package com.dao;

import java.util.List;

import com.entity.OrderGoods;

/* 订单的产品列表数据表操作 */
public interface OrderGoodsDao {

	public boolean insert(OrderGoods orderGoods);					// 插入
	public List<OrderGoods> queryByOrderID(String orderID);			// 查找订单的商品列表
	
	public boolean updateForPickSign(String orderID, String clothingID, int pick_sign, String pick_time);		// 更新拣货标记
	
}
