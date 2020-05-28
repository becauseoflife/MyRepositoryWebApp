package com.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateOrderID {

	private static CreateOrderID createOrderID;		// 私有变量 
	
	private static int increment;
	
	
	// 私有构造方法
	private CreateOrderID(){
		increment = 1;
	}
	
	// 静态共有工厂方法，返回唯一实例
	public static CreateOrderID getOrderIdGenerator(){
		if(createOrderID == null)
			createOrderID = new CreateOrderID();
		return createOrderID;
	}
	
	public String nextOrderID(){
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String sequ = new DecimalFormat("000000").format(this.increment++);
        
        return date + sequ;
	}
	
}
