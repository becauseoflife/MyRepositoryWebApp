package com.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.mapper.ClothingInfo;

// 购物车类
public class Cart {

	private HashMap<ClothingInfo, Integer> goods;

	// 构造函数
	public Cart(){
		this.goods = new HashMap<ClothingInfo, Integer>();
	}
	
	public HashMap<ClothingInfo, Integer> getGoods() {
		return goods;
	}

	public void setGoods(HashMap<ClothingInfo, Integer> goods) {
		this.goods = goods;
	}

	// 添加商品进购物车
	public boolean addGoodsInCart(ClothingInfo clothing, int number){
		// 判断是否添加过该服饰
		if(goods.containsKey(clothing)){
			goods.put(clothing, goods.get(clothing) + number);
		}
		else
		{
			goods.put(clothing, number);
		}
		return true;
	}
	
	// 删除商品
	public boolean removeGoodFormCart(ClothingInfo clothing){
		 goods.remove(clothing);
		 return true;
	}
	
	// 测试添加商品
/*	public static void main(String[] args) {
		
		ClothingInfo c1 = new ClothingInfo();
		c1.setClothingID("4a507db7-7c52-2c22-d6a6-77ade48625bd");
		c1.setShelves("A");
		c1.setLocation("5");
		c1.setNumber(10);
		ClothingInfo c3 = new ClothingInfo();
		c3.setClothingID("4a507db7-7c52-2c22-d6a6-77ade48625bd");
		c3.setShelves("A");
		c3.setLocation("5");
		c3.setNumber(10);
		
		Cart cart = new Cart();
		cart.addGoodsInCart(c1, 11);
		cart.addGoodsInCart(c3, 10);
		
		Set<Map.Entry<ClothingInfo, Integer>> items = cart.getGoods().entrySet();
		for(Map.Entry<ClothingInfo, Integer> obj : items){
			System.out.println(obj);
		}
		
	}*/
	
	
	
}
