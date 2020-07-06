package com.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;

import com.dao.OrderItemDao;
import com.dao.OrderInfoDao;
import com.daofactory.impl.JdbcOrderDaoFactory;
import com.daofactory.impl.JdbcOrderGoodsFactory;
import com.entity.Cart;
import com.entity.Order;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;
import com.service.OrderService;

public class OrderServiceImpl implements OrderService{

	// 订单记录表操作类
	private OrderInfoDao orderBDHelper = new JdbcOrderDaoFactory().getOrderDao();
	
	// 订单物品记录表操作类
	private OrderItemDao orderGoodsDBHelper = new JdbcOrderGoodsFactory().getOrderGoodsDao();
	
	
	@Override
	public boolean createOrder(OrderInfo order, Cart cart) {
		// TODO Auto-generated method stub
		// 插入订单记录数据库
		if(orderBDHelper.add(order))
		{
			// 将订单中的商品保存到数据库中
			HashMap<ClothingInfo, Integer> goods = cart.getGoods();
			Set<ClothingInfo> items = goods.keySet();
  			Iterator<ClothingInfo> it = items.iterator();
			while(it.hasNext())
			{
				ClothingInfo c = it.next();
				
				OrderItemInfo g = OrderItemInfo.builder()
						.order_id(order.getOrder_id())
						.clothingID(c.getClothingID())
						.number(goods.get(c))
						.pick_sign(OrderItemInfo.NOT_PICK)
						.pick_time(null)
						.build();
				
/*				g.setOrder_id(order.getOrder_id());
				g.setShelves(c.getShelves());
				g.setLocation(c.getLocation());
				g.setClothingID(c.getClothingID());
				g.setNumber(goods.get(c));
				g.setPick_sign(OrderItemInfo.NOT_PICK);
				g.setPick_time(null);*/

				orderGoodsDBHelper.add(g);
			}		
		}
		return true;
	}


	@Override
	public List<OrderInfo> findAllByState(int sign) {
		// TODO Auto-generated method stub
		return orderBDHelper.findAllByState(sign);	
	}


	@Override
	public Order getOrder(String orderID) {
		// TODO Auto-generated method stub
		// 获取订单详情
		OrderInfo orderInfo = orderBDHelper.findById(orderID);
		List<OrderItemInfo> goodsList = orderGoodsDBHelper.findAllById(orderID);
		Order order = new Order(orderInfo, goodsList);
		
		return order;
	}


	@Override
	public boolean updateSetPickSign(OrderItemInfo orderItem) {
		// TODO Auto-generated method stub
		
		return orderGoodsDBHelper.updateSetSign(orderItem);
	}


	@Override
	public boolean updateSetState(String order_id, int state) {
		// TODO Auto-generated method stub
		return orderBDHelper.updateSetState(order_id, state);
	}


	@Override
	public boolean inportOrder(Order order) {
		// TODO Auto-generated method stub
		
		// 添加订单信息到数据库
		OrderInfo orderInfo = order.getOrderInfo();
		if(orderBDHelper.add(orderInfo))
		{
			// 添加商品信息到数据库
			for (OrderItemInfo itemInfo : order.getGoodsList())
			{
				orderGoodsDBHelper.add(itemInfo);
			}
			return true;
		}
		else
		{
			return false;
		}

	}


	@Override
	public boolean add(OrderInfo orderInfo) {
		// TODO Auto-generated method stub
		
		return orderBDHelper.add(orderInfo);
	}


	@Override
	public boolean add(OrderItemInfo itemInfo) {
		// TODO Auto-generated method stub
		return orderGoodsDBHelper.add(itemInfo);
	}


	@Override
	public List<OrderItemInfo> findAllItemByPickSign(int pickSign) {
		// TODO Auto-generated method stub
		
		return orderGoodsDBHelper.findAllByPickSign(pickSign);
	}


	@Override
	public boolean updateSetPickUser(OrderItemInfo itemInfo) {
		// TODO Auto-generated method stub
		
		return orderGoodsDBHelper.updateSetPickUser(itemInfo);
	}


	@Override
	public int sumForNumber(String pick_user, int pick_sign) {
		// TODO Auto-generated method stub
		return orderGoodsDBHelper.sumForNumber(pick_user, pick_sign);
	}


	@Override
	public List<OrderItemInfo> findAllItemByPickUser(String pickUser, int pick_sign) {
		// TODO Auto-generated method stub
		return orderGoodsDBHelper.findAllByPickUser(pickUser, pick_sign);
	}


	@Override
	public List<OrderInfo> findAllOrderInfo() {
		// TODO Auto-generated method stub
		return orderBDHelper.findAll();
	}
	
	

}
