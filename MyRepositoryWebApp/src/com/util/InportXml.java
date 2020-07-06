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

	private static final String ORDERS = "orders";			// �����ܽ��
	private static final String ORDER = "order";			// �����ڵ�
	private static final String ORDERID = "orderID";		// ����ID�ڵ�
	private static final String ORDERITEM = "orderItem";	// ������Ʒ�ڵ�
	private static final String ITEMID = "itemID";			// ������ƷID
	private static final String NUMBER = "number";			// ������Ʒ����
	
	@Override
	public List<Order> inportOrder(InputStream path) {
		// TODO Auto-generated method stub
		
		// �����б�
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			//�����ĵ�����
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dFactory.newDocumentBuilder();
			Document doc = builder.newDocument();
		
			// ����
			//doc = builder.parse(new File("D:/Desktop/������/���ϵͳ����ʵ��/test.xml"));
			doc = builder.parse(path);
			
			// ��ȡdom������ĸ��ڵ�
			Element orders = doc.getDocumentElement();
			// �õ����е�order�ڵ���ӽڵ�
			NodeList allOrder = orders.getElementsByTagName(this.ORDER);
			//System.out.println(allOrder.getLength());
			// ѭ����������
			for (int i = 0; i < allOrder.getLength(); i++)
			{
				// �����ڵ�
				Element orderElement = (Element)allOrder.item(i);
				
				//	orderID�ڵ�
				NodeList orderID = orderElement.getElementsByTagName(this.ORDERID);
				//System.out.println("����ID��" + orderID.item(0).getTextContent());
				
				// �½�������Ϣ
				OrderInfo orderInfo = OrderInfo.builder()
										.order_id(orderID.item(0).getTextContent())
										.state(OrderInfo.UNRESOLVED)
										.create_time(new Date())
										.build();
				
				// orderItem�ڵ�
				NodeList orderItem = orderElement.getElementsByTagName(this.ORDERITEM);
					
				// ���е�orderItem�ڵ�
				Element allOrderItem = (Element) orderItem.item(0);
				
				// ItemID�ڵ��number�ڵ�
				NodeList itemIDList = allOrderItem.getElementsByTagName(this.ITEMID);
				NodeList numList = allOrderItem.getElementsByTagName(this.NUMBER);
				//System.out.println( "itemIDList���ȣ�" + itemIDList.getLength());
				//System.out.println( "numList���ȣ�" + numList.getLength());	
				
				// �½�������Ʒ�б�
				List<OrderItemInfo> goodsList = new ArrayList<OrderItemInfo>();
				for (int j = 0; j < itemIDList.getLength(); j++) 
				{
					// ��ȡ��Ʒ��Ϣ
					OrderItemInfo itemInfo = OrderItemInfo.builder()
											.order_id(orderID.item(0).getTextContent())
											.clothingID(itemIDList.item(j).getTextContent())
											.number(Integer.parseInt(numList.item(j).getTextContent()))
											.pick_sign(OrderItemInfo.NOT_PICK)
											.pick_time(null)
											.build();
					goodsList.add(itemInfo);
					// ��ȡ��ƷID
					//System.out.println("	itemID:" + itemIDList.item(j).getTextContent());
					//System.out.println("	nummber:" + numList.item(j).getTextContent());
				}
				

				// ���ɶ���
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
		//System.out.println("��������:" + orderList.size());
	
		return orderList;
	}

	
	
	/* ��Excel����xml */
	public void ExportXml(List<Order> orderList) throws ParserConfigurationException
	{
		//�����ĵ�����
		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dFactory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		// �ܽڵ�
		Element orders = doc.createElement(this.ORDERS);
		
		// xml�ṹ
		doc.appendChild(orders);
		
		// ����id���Ե�ֵ
		int id = 0;
		// ѭ�����ɽڵ�
		for (Order o : orderList) {
			id++;
			
			Element order = doc.createElement(this.ORDER);		// ���ɶ����ڵ�
			//Attr attrId = doc.createAttribute("id");			// ����id����
			//attrId.setNodeValue(String.valueOf(id));
			
			Element orderID = doc.createElement(this.ORDERID);
			Text orderIDText = doc.createTextNode(o.getOrderInfo().getOrder_id());
			
			// xml�ṹ
			orders.appendChild(order);
					//order.setAttributeNode(attrId);
					order.appendChild(orderID);
						orderID.appendChild(orderIDText);
			
			// ������Ʒ����ֵ
			int itemId = 0;		
			Element orderItem = doc.createElement(this.ORDERITEM);
			
			// xml�ṹ
					order.appendChild(orderItem);
			
			// ѭ�����ɶ����б�ڵ�
			for (OrderItemInfo item : o.getGoodsList())
			{
				itemId++;
				// ��Ʒ
				Element itemID = doc.createElement(this.ITEMID);
				Text itemIDText = doc.createTextNode(item.getClothingID());
				//Attr attritemId = doc.createAttribute("id");					// ����id����
				//attritemId.setNodeValue(String.valueOf(itemId));
				
				// ����
				Element num = doc.createElement(this.NUMBER);
				Text numText = doc.createTextNode(String.valueOf(item.getNumber()));
				
				// xml�ṹ
				orderItem.appendChild(itemID);
						//itemID.setAttributeNode(attritemId);
						itemID.appendChild(itemIDText);
				orderItem.appendChild(num);
						num.appendChild(numText);
			}
			
			
		}
		
		// �����ļ�
		try {	
			// ����TransformerFactory����
			TransformerFactory tff = TransformerFactory.newInstance();
			// ���� Transformer����
			Transformer tf = tff.newTransformer();
			// ��������Ƿ�ʹ�û���
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			
			// ����xml�ļ���д������
			tf.transform(new DOMSource(doc), new StreamResult(new File("D:/Desktop/������/���ϵͳ����ʵ��/test.xml")));
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	// ����
/*	public static void main(String[] args) {
		Inport inport = new InportXml();
		inport.inportOrder(null);
	}*/
	
}
