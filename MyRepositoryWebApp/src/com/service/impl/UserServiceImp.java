package com.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dao.OrderDao;
import com.dao.OrderGoodsDao;
import com.dao.UserDao;
import com.dao.WarehouseDao;
import com.dao.impl.OrderDaoImpl;
import com.dao.impl.OrderGoodsDaoImpl;
import com.dao.impl.WarehouseDaoImpl;
import com.entity.Cart;
import com.entity.ClothingInfo;
import com.entity.Order;
import com.entity.OrderGoods;
import com.entity.User;
import com.service.UserService;
import com.simplefactory.UserDBHelperFactory;
import com.util.CreateOrderID;

public class UserServiceImp implements UserService {

	// ��ȡ�û����ݿ������
	private UserDao userDBHelper = UserDBHelperFactory.getUserDBHelperDao();
	
	// �ֿ��������
	private WarehouseDao warehouseDBHelper = new WarehouseDaoImpl();
	
	// ������¼��������
	private OrderDao orderBDHelper = new OrderDaoImpl();
	
	// ������Ʒ��¼��������
	private OrderGoodsDao orderGoodsDBHelper = new OrderGoodsDaoImpl();

	@Override
	public boolean login(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("userName"); 	// �û���
		String password = request.getParameter("passWord");		// ����
		
		User user = null;
		try {
			user = userDBHelper.querryByUserName(userName);
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
			session.setAttribute("massage", "�����������������!");
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
		System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	
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
				session.setAttribute("message", "ע��ʧ�ܣ�");
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}


	@Override
	public void queryClothing(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String clothingID = request.getParameter("clothingId");
		List<ClothingInfo> list = warehouseDBHelper.queryListByClothingID(clothingID);

		int sum = 0;	// �·�����
		List<String> indexList = new ArrayList<String>();	// �·�λ��
		
		for (ClothingInfo c : list) {
			sum += c.getNumber();
			indexList.add(c.getShelves() + "-" + c.getLocation());
		}
		
		// ���浽session��
		HttpSession session = request.getSession();
		session.setAttribute("isSearch", true);
		session.setAttribute("clothingSum", sum);
		session.setAttribute("indexList", indexList);
		
	}


	@Override
	public void addToCart(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String clothingID = request.getParameter("clothingId");
		int number = Integer.parseInt(request.getParameter("number"));
		HttpSession session = request.getSession();
		
		// �Ƿ��ǵ�һ��������Ʒ����Ҫ��session������һ���µĹ��ﳵ����
		if(session.getAttribute("order") == null)
		{
			Cart cart = new Cart();
			session.setAttribute("order", cart);
		}
		Cart cart = (Cart)session.getAttribute("order");
		
		// ��ȡ�ֿ��еķ���
		List<ClothingInfo> cloList = warehouseDBHelper.queryListByClothingID(clothingID);
		// �жϷ����Ƿ����
		if(cloList.size() == 0){
			session.setAttribute("message", "��װID"+ clothingID + "�����ڣ�<br/> ���������룡");
			try {
				request.getRequestDispatcher("/pages/create_order.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		
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
		// ���ӵ��������ڿ�棬����ʧ��
		if( orderNum > total)
		{
			session.setAttribute("message",
					"���������"+ total + 
					"<br/> ��������" + orderNum + "���������������������������!"
					);
			try {
				request.getRequestDispatcher("/pages/create_order.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		
		// ������������������
		if(cart.addGoodsInCart(cloList.get(0), number))
		{
			session.setAttribute("message", "���ӳɹ���");
			try {
				request.getRequestDispatcher("/pages/create_order.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			try {
				request.getRequestDispatcher("/pages/create_order.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				request.getRequestDispatcher("/pages/create_order.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				g.setClothingID(c.getClothingID());
				g.setNumber(goods.get(c));
				g.setPick_sign(OrderGoods.NOT_PICK);
				g.setPick_time(null);
				System.out.println("���룺" + g.getClothingID());
				orderGoodsDBHelper.insert(g);
			}
			
			// ��ת���������
			try {
				request.getRequestDispatcher("/pages/pick_good.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			request.getSession().setAttribute("message", "���ɶ���ʧ�ܣ�");
			try {
				request.getRequestDispatcher("/pages/create_order.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}
	
}