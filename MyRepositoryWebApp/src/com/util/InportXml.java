package com.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.entity.Order;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;

public class InportXml implements Inport{

	private static final String ORDERS = "orders";			// 订单总结点
	private static final String ORDER = "order";			// 订单节点
	private static final String ORDERID = "orderID";		// 订单ID节点
	private static final String ORDERITEM = "orderItem";	// 订单商品节点
	private static final String ITEMID = "itemID";			// 订单商品ID
	private static final String NUMBER = "number";			// 订单商品数量
	
	@Override
	public List<Order> inportOrder(InputStream path) {
		// TODO Auto-generated method stub
		
		// 订单列表
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			//创建文档对象
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dFactory.newDocumentBuilder();
			Document doc = builder.newDocument();
		
			// 测试
			//doc = builder.parse(new File("D:/Desktop/大三下/软件系统分析实验/test.xml"));
			doc = builder.parse(path);
			
			// 获取dom树里面的根节点
			Element orders = doc.getDocumentElement();
			// 得到所有的order节点的子节点
			NodeList allOrder = orders.getElementsByTagName(this.ORDER);
			//System.out.println(allOrder.getLength());
			// 循环读出订单
			for (int i = 0; i < allOrder.getLength(); i++)
			{
				// 订单节点
				Element orderElement = (Element)allOrder.item(i);
				
				//	orderID节点
				NodeList orderID = orderElement.getElementsByTagName(this.ORDERID);
				//System.out.println("订单ID：" + orderID.item(0).getTextContent());
				
				// 新建订单信息
				OrderInfo orderInfo = OrderInfo.builder()
										.order_id(orderID.item(0).getTextContent())
										.state(OrderInfo.UNRESOLVED)
										.create_time(new Date())
										.build();
				
				// orderItem节点
				NodeList orderItem = orderElement.getElementsByTagName(this.ORDERITEM);
					
				// 所有的orderItem节点
				Element allOrderItem = (Element) orderItem.item(0);
				
				// ItemID节点和number节点
				NodeList itemIDList = allOrderItem.getElementsByTagName(this.ITEMID);
				NodeList numList = allOrderItem.getElementsByTagName(this.NUMBER);
				//System.out.println( "itemIDList长度：" + itemIDList.getLength());
				//System.out.println( "numList长度：" + numList.getLength());	
				
				// 新建订单商品列表
				List<OrderItemInfo> goodsList = new ArrayList<OrderItemInfo>();
				for (int j = 0; j < itemIDList.getLength(); j++) 
				{
					// 获取商品信息
					OrderItemInfo itemInfo = OrderItemInfo.builder()
											.order_id(orderID.item(0).getTextContent())
											.clothingID(itemIDList.item(j).getTextContent())
											.number(Integer.parseInt(numList.item(j).getTextContent()))
											.pick_sign(OrderItemInfo.NOT_PICK)
											.pick_time(null)
											.build();
					goodsList.add(itemInfo);
					// 获取商品ID
					//System.out.println("	itemID:" + itemIDList.item(j).getTextContent());
					//System.out.println("	nummber:" + numList.item(j).getTextContent());
				}
				

				// 生成订单
				Order order = new Order(orderInfo, goodsList);
				
				orderList.add(order);
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("订单总数:" + orderList.size());
	
		return orderList;
	}

	
	
	/* 由Excel导出xml */
	public void ExportXml(List<Order> orderList) throws ParserConfigurationException
	{
		//创建文档对象
		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dFactory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		// 总节点
		Element orders = doc.createElement(this.ORDERS);
		
		// xml结构
		doc.appendChild(orders);
		
		// 订单id属性的值
		int id = 0;
		// 循环生成节点
		for (Order o : orderList) {
			id++;
			
			Element order = doc.createElement(this.ORDER);		// 生成订单节点
			//Attr attrId = doc.createAttribute("id");			// 创建id属性
			//attrId.setNodeValue(String.valueOf(id));
			
			Element orderID = doc.createElement(this.ORDERID);
			Text orderIDText = doc.createTextNode(o.getOrderInfo().getOrder_id());
			
			// xml结构
			orders.appendChild(order);
					//order.setAttributeNode(attrId);
					order.appendChild(orderID);
						orderID.appendChild(orderIDText);
			
			// 订购商品属性值
			int itemId = 0;		
			Element orderItem = doc.createElement(this.ORDERITEM);
			
			// xml结构
					order.appendChild(orderItem);
			
			// 循环生成订购列表节点
			for (OrderItemInfo item : o.getGoodsList())
			{
				itemId++;
				// 商品
				Element itemID = doc.createElement(this.ITEMID);
				Text itemIDText = doc.createTextNode(item.getClothingID());
				//Attr attritemId = doc.createAttribute("id");					// 创建id属性
				//attritemId.setNodeValue(String.valueOf(itemId));
				
				// 数量
				Element num = doc.createElement(this.NUMBER);
				Text numText = doc.createTextNode(String.valueOf(item.getNumber()));
				
				// xml结构
				orderItem.appendChild(itemID);
						//itemID.setAttributeNode(attritemId);
						itemID.appendChild(itemIDText);
				orderItem.appendChild(num);
						num.appendChild(numText);
			}
			
			
		}
		
		// 导出文件
		try {	
			// 创建TransformerFactory对象
			TransformerFactory tff = TransformerFactory.newInstance();
			// 创建 Transformer对象
			Transformer tf = tff.newTransformer();
			// 输出内容是否使用换行
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			
			// 创建xml文件并写入内容
			tf.transform(new DOMSource(doc), new StreamResult(new File("D:/Desktop/大三下/软件系统分析实验/test.xml")));
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	// 测试
/*	public static void main(String[] args) {
		Inport inport = new InportXml();
		inport.inportOrder(null);
	}*/
	
}
