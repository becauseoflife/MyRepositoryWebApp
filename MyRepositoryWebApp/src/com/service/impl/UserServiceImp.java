package com.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.event.IIOReadProgressListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.dao.OrderDao;
import com.dao.OrderGoodsDao;
import com.dao.UserDao;
import com.dao.WarehouseDao;
import com.dao.impl.OrderDaoImpl;
import com.dao.impl.OrderGoodsDaoImpl;
import com.dao.impl.WarehouseDaoImpl;
import com.entity.Cart;
import com.entity.Check;
import com.entity.CheckTable;
import com.entity.ClothingInfo;
import com.entity.Order;
import com.entity.OrderGoods;
import com.entity.User;
import com.service.UserService;
import com.simplefactory.UserDBHelperFactory;
import com.util.CreateOrderID;
import com.util.ExportExcelUtil;

public class UserServiceImp implements UserService {

	// ��ȡ�û����ݿ������
	private UserDao userDBHelper = UserDBHelperFactory.getUserDBHelperDao();
	
	// �ֿ�������
	private WarehouseDao warehouseDBHelper = new WarehouseDaoImpl();
	
	// ������¼�������
	private OrderDao orderBDHelper = new OrderDaoImpl();
	
	// ������Ʒ��¼�������
	private OrderGoodsDao orderGoodsDBHelper = new OrderGoodsDaoImpl();

	@Override
	public boolean login(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("userName"); 	// �û���
		String password = request.getParameter("passWord");		// ����
		
		User user = null;
		try {
			user = userDBHelper.queryByUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		// ��֤�û���¼��Ϣ
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(3600 * 24 * 10);		// �������ʱ��
		if(user != null && user.getPassword().equals(password))
		{
			session.setAttribute("user", user);		// �����û���Ϣ��session��ȥ
			return true;		// ��¼�ɹ�
		}
		else if(user == null)
		{
			session.setAttribute("message", "�û������ڣ�����ע��!");
			return false;
		}
		else if(user != null && !user.getPassword().equals(password))
		{
			session.setAttribute("message", "�����������������!");
			return false;
		}
		
		return false;
	}

	
	@Override
	public boolean register(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("registerUsername"); 	// �û���
		String password = request.getParameter("registerPassword");		// ����
		String telephone = request.getParameter("registerTelephone");	// ��ϵ�绰
		String email = request.getParameter("registerEmail");			// �����ʼ�
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		//System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	
		// �½��û�
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setTelephone(telephone);
		user.setEmail(email);
		user.setRegDate(new Date());
		
		try {
			HttpSession session = request.getSession();
			if (userDBHelper.insert(user)) {
				
				session.setAttribute("message", "ע��ɹ���");
				return true;
			}else{
				session.setAttribute("message", "ע��ʧ�ܣ�<br/>"+userName+"�û����Ѵ��ڣ�");
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}


	@Override
	public boolean queryClothingById(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String clothingID = request.getParameter("clothingId");
		List<ClothingInfo> list = warehouseDBHelper.queryListByClothingID(clothingID);
		
/*		if(list.size()==0)	// û�в鵽��¼	
			return false;*/

		int sum = 0;	// �·�����
		List<String> indexList = new ArrayList<String>();	// �·�λ��
		
		for (ClothingInfo c : list) {
			sum += c.getNumber();
			indexList.add(c.getShelves() + "-" + c.getLocation());
		}
		
		// ���浽session��
		HttpSession session = request.getSession();
		session.setAttribute("clothingID", clothingID);	// ��װID
		session.setAttribute("isSearch", true);			// �Ƿ����
		session.setAttribute("clothingSum", sum);		// ��װ����
		session.setAttribute("indexList", indexList);	// λ���б�
		
		return true;
		
	}


	@Override
	public void addToCart(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String clothingID = request.getParameter("clothingId");
		int number = Integer.parseInt(request.getParameter("number"));
		HttpSession session = request.getSession();
		if(number<0)
			return;
		
		// �Ƿ��ǵ�һ�������Ʒ����Ҫ��session�����һ���µĹ��ﳵ����
		if(session.getAttribute("order") == null)
		{
			Cart cart = new Cart();
			session.setAttribute("order", cart);
		}
		Cart cart = (Cart)session.getAttribute("order");
		
		// ��ȡ�ֿ��еķ���
		List<ClothingInfo> cloList = warehouseDBHelper.queryListByClothingID(clothingID);
		// ���β�����
		if(cloList.size() == 0){
			session.setAttribute("message", "��װID"+ clothingID + "�����ڣ�<br/> ���������룡");
			return;
		}
		
		// ���δ���
		int total = 0;
		int orderNum = number; 
		// ������ֿ��з��ε�������
		for (ClothingInfo c : cloList) {
			total += c.getNumber();
		}
		// ����������е�������
		if(cart.getGoods().get(cloList.get(0))!=null){
			orderNum += cart.getGoods().get(cloList.get(0));
		}
		// ��ӵ��������ڿ�棬���ʧ��
		if( orderNum > total)
		{
			session.setAttribute("message",
					"���������"+ total + 
					"<br/> ����������" + orderNum + "<br/>��������������������������!"
					);
			return;
		}
		
		
		// ������������������
		if(cart.addGoodsInCart(cloList.get(0), number))
		{
			//session.setAttribute("message", "��ӳɹ���");
			return;
		}
		
		
	}


	@Override
	public void deleteFormCart(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String clothingID = request.getParameter("clothingId");
		
		//System.out.println("clothingID: " + clothingID );
		// ����
		Cart cart = (Cart)request.getSession().getAttribute("order");
		ClothingInfo deleteItem = warehouseDBHelper.queryByClothingID(clothingID);
		
		// �ӹ��ﳵ��ɾ��
		if(cart.removeGoodFormCart(deleteItem))
		{
			request.getSession().setAttribute("message", "ɾ���ɹ���");
		}
		else
		{
			request.getSession().setAttribute("message", "ɾ��ʧ�ܣ�");
		}
		
	}


	@Override
	public void createOrder(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		// ��ȡ�û�
		User user = (User)request.getSession().getAttribute("user");
		// ��ȡ����
		Cart cart = (Cart)request.getSession().getAttribute("order");
		// ��������ID
		String orderID = CreateOrderID.getOrderIdGenerator().nextOrderID();
		
		// ����Ϊ��
		if( cart==null || cart.getGoods().isEmpty())
		{
			request.getSession().setAttribute("message", "���������б�Ϊ�գ�<br/>���������Ʒ��");
			return;
		}
		
		// ����������¼
		Order order = new Order();
		order.setOrder_id(orderID);
		order.setUsername(user.getUserName());
		order.setState(Order.UNRESOLVED);
		order.setCreate_time(new Date());
		
		// ���붩����¼���ݿ�
		if(orderBDHelper.insert(order))
		{
			// �������е���Ʒ���浽���ݿ���
			HashMap<ClothingInfo, Integer> goods = cart.getGoods();
			Set<ClothingInfo> items = goods.keySet();
  			Iterator<ClothingInfo> it = items.iterator();
			while(it.hasNext())
			{
				OrderGoods g = new OrderGoods();
				ClothingInfo c = it.next();
				g.setOrder_id(orderID);
				g.setShelves(c.getShelves());
				g.setLocation(c.getLocation());
				g.setClothingID(c.getClothingID());
				g.setNumber(goods.get(c));
				g.setPick_sign(OrderGoods.NOT_PICK);
				g.setPick_time(null);

				orderGoodsDBHelper.insert(g);
			}
			
			// ��ת���������
			request.getSession().setAttribute("message", "���ɶ����ɹ���");
			// �������б����
			cart = null;
			request.getSession().setAttribute("order", cart);
			return;
		}
		else
		{
			request.getSession().setAttribute("message", "���ɶ���ʧ�ܣ�");
		}
		
	}


	@Override
	public void getOrderSelectOptions(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		List<Order> list = orderBDHelper.queryOrderListByState(Order.UNRESOLVED);	// ��ȡδ����Ķ���
		  
		PrintWriter writer;
		String jsonStr = JSON.toJSONString(list);
		//System.out.println(jsonStr);
		try {
			writer = response.getWriter();
			writer.write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void getOrderInfo(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String order_id = request.getParameter("select_orderId");

		// ��ȡ�����б�
		List<OrderGoods> goodsList = orderGoodsDBHelper.queryByOrderID(order_id);
		
		// ������session��ȥ
		request.getSession().setAttribute("order_goods_list", goodsList);
		
		return;
	}


	@Override
	public void pickGood(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ����ķ�װID�Ͷ����б�
		String clothingID = request.getParameter("clothingId");
		List<OrderGoods> goodsList = (List<OrderGoods>) session.getAttribute("order_goods_list");
		
		// �Ӳֿ��л�ȡ����
		ClothingInfo clothingInfo = warehouseDBHelper.queryByClothingID(clothingID);
		
		// ��session�еĻ�ȡ������Ϣ
		for (OrderGoods o : goodsList) {
			// �������и÷��μ�¼
			if(clothingID.equals(o.getClothingID()))
			{
				// �Ѽ��
				if(o.getPick_sign() == OrderGoods.PICK)
				{
					session.setAttribute("message", "��Ʒ:"+clothingID+" �Ѽ��<br/>������ѡ������Ʒ");
					return;
				}
				
				// ����㹻 ���м������
				if(clothingInfo.getNumber() >= o.getNumber()){
					// ���ý����е�����
					o.setPick_sign(OrderGoods.PICK);
					o.setPick_time(new Date());
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = df.format(o.getPick_time());
					// �������ݿ��и���Ʒ�ļ������
					orderGoodsDBHelper.updateForPickSign(o.getOrder_id(), o.getClothingID(), OrderGoods.PICK, time);
					// �ֿ��е���������
					warehouseDBHelper.updateForNumber(clothingID, o.getShelves(), o.getLocation(), clothingInfo.getNumber()-o.getNumber());
					
					session.setAttribute("message", "����ɹ���");

					return;
					
				}
				// ��治��
				else	
				{
					session.setAttribute("message", "��治�㣡");
					return;
				}
			}
		}
		// �����в����ڸ���Ʒ
		session.setAttribute("message", "�����в����ڸ÷�װ��");
		return;
	}


	@Override
	public void orderResolved(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		List<OrderGoods> goodsList = (List<OrderGoods>) session.getAttribute("order_goods_list");
		String orderID  = goodsList.get(0).getOrder_id();
		
		// �ж϶����Ƿ������
		boolean flag = true;
		
		for (OrderGoods o : goodsList) {
			if(o.getPick_sign() != OrderGoods.PICK)
				flag = false;
		}
		
		if(flag)	// ����ȫ�������
		{
			// ���ݿ�״̬�ֶ��޸ĳɹ�
			if(orderBDHelper.updateForState(orderID, Order.RESOLVED))	
			{
				session.removeAttribute("order_goods_list"); 		// ɾ��session�еĶ�����Ϣ
				session.setAttribute("message", "����ɹ���");
				return;
			}
			// ���ݿ�״̬�ֶ��޸�ʧ��
			else	
			{
				session.setAttribute("message", "����ʧ�ܣ�");
				return;
			}
		}
		else	// 	������δ����Ĳ�Ʒ
		{
			session.setAttribute("message", "���в�Ʒδ�����");
			return;
		}
		
	}


	@Override
	public void queryClothingByLocation(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		// ��ȡλ����Ϣ
		String shelves = request.getParameter("shelves");
		String location = request.getParameter("location");
 		//System.out.println("λ����Ϣ��"+ shelves + "-" + location);
		
		// �̵������Ϊ�գ��򴴽�
		if(session.getAttribute("checkTable")== null)
		{
			CheckTable checkTable = new CheckTable();
			session.setAttribute("checkTable", checkTable);		// ���̵��ŵ�session��
		}
		
		// �Ӳֿ��л�ȡ������Ϣ
		ClothingInfo c = warehouseDBHelper.queryByLocation(shelves, location);
		
		// �����ζ������session��
		if(c!=null)
		{
			session.setAttribute("clothingInfo", c);
		}
		else
		{
			session.setAttribute("message", "λ����������<br/>���������룡<br/>��: <br/>����:  A<br/>��λ:  1 ");
		}

	}


	@Override
	public void updateClothingNumber(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		// ��ȡ���µ�����
		int updateNum = Integer.parseInt(request.getParameter("updateNum"));
		
		// ��ȡ�����µķ�װ
		ClothingInfo c = (ClothingInfo)session.getAttribute("clothingInfo");
		
		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		if(c!=null && checkTable!=null){
		
			// �������ݿ��е�����
			warehouseDBHelper.updateForNumber(c.getClothingID(), c.getShelves(), c.getLocation(), updateNum);
			
			// ��ӯ���̿���ֵ
			int surplus = 0;
			int loss = 0;
	
			if(c.getNumber() > updateNum)	// �̿�
			{
				loss = Math.abs(c.getNumber()-updateNum);
			}
			else if(c.getNumber() < updateNum)	// ��ӯ
			{
				surplus = Math.abs(c.getNumber()-updateNum);
			}
			
			// �̵����
			Check check = new Check(
					c.getClothingID(),
					c.getShelves()+"-"+c.getLocation(),
					c.getNumber(),
					updateNum,
					surplus,
					loss);
			
			// �̵����¼
			checkTable.addCheck(check);
			
			// ������ɾ���������ظ�����
			session.removeAttribute("clothingInfo");
	
		}	
		return;
	}


	@Override
	public void deleteFormCheckTable(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡɾ��������
		String row = request.getParameter("row");
		
		// ȡ��ɾ���Ķ���
		Check check = (Check)session.getAttribute(row);
			
		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		// ɾ��
		checkTable.deleteCheck(check);
		
		return;
	}


	@Override
	public boolean exportExcel(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		if(checkTable == null)
		{
			session.setAttribute("message", "�ѵ�����");
			return false;
		}
		
		// ��������������
		ExportExcelUtil<Check> excelUtil = new ExportExcelUtil<Check>();
		
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=check.xlsx");//Ҫ������ļ���
		response.setContentType("application/octet-stream; charset=utf-8");
		
		try {
			excelUtil.ExportExcel("��ӯ���̿�����", checkTable.getTitle(), checkTable.getDataSet(), response.getOutputStream(), ExportExcelUtil.EXCEl_FILE_2007);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ���̵�������session��ɾ��
		session.removeAttribute("checkTable");
		
		return true;
	}


	@Override
	public void outLogin(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		request.getSession().invalidate();		// ����session
	
	}
	
}
