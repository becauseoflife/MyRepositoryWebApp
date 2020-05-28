package com.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateOrderID {

	private static CreateOrderID createOrderID;		// ˽�б��� 
	
	private static int increment;
	
	
	// ˽�й��췽��
	private CreateOrderID(){
		increment = 1;
	}
	
	// ��̬���й�������������Ψһʵ��
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
