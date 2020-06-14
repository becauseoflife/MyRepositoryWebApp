package com.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.New;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Cart;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.UserInfo;
import com.service.OrderService;
import com.service.ExportService;
import com.service.WarehouseService;
import com.service.impl.OrderServiceImpl;
import com.service.impl.ExportServiceImp;
import com.service.impl.WarehouseServiceImpl;
import com.util.CreateOrderID;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String action;		// ��������Ķ���
	
	private WarehouseService warehouseService = new WarehouseServiceImpl();
	
	private OrderService orderService = new OrderServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//System.out.println("action:" + request.getParameter("action"));
		
		ExportService service = new ExportServiceImp();
		
		if(request.getParameter("action") != null)
		{
			this.action = request.getParameter("action");
			if(action.equals("add"))		// ��ӽ����ﳵ
			{
				addToOrder(request, response);
			}
			if(action.equals("delete"))		// �Ӷ�����ɾ����Ʒ
			{
				deleteFormOrder(request, response);
			}
			if(action.equals("create"))		// ��������
			{
				createOrder(request, response);
			}
		}
	}

	private void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String message = null;
		// ��ȡ�û�ID
		String userName = ((UserInfo)request.getSession().getAttribute("user")).getUserName();
		// ��ȡ����
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		
		// ��������ID
		String orderID = CreateOrderID.getOrderIdGenerator().nextOrderID();
		
		// ����Ϊ��
		if( cart==null || cart.getGoods().isEmpty())
		{
			message = "���������б�Ϊ�գ�<br/>���������Ʒ��";
			request.getSession().setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
		}
		
		// ����������¼
		OrderInfo orderInfo = OrderInfo.builder()
				.order_id(orderID)
				.username(userName)
				.state(OrderInfo.UNRESOLVED)
				.create_time(new Date())
				.build();
		
/*		orderInfo.setOrder_id(orderID);
		orderInfo.setUsername(userName);
		orderInfo.setState(OrderInfo.UNRESOLVED);
		orderInfo.setCreate_time(new Date());*/
		
		orderService.createOrder(orderInfo, cart);
		
		// ��ת���������
		message = "���ɶ����ɹ���";
		request.getSession().setAttribute("message", message);
		// �������б����
		cart = null;
		request.getSession().setAttribute("cart", cart);
		
		request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
	}

	// �Ӷ�����ɾ����Ʒ
	private void deleteFormOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String clothingID = request.getParameter("clothingId");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		
		ClothingInfo item = warehouseService.findById(clothingID);
		String message = null;
		
		// �ӹ��ﳵ��ɾ��
		if(cart.removeGoodFormCart(item))
		{
			message= "ɾ���ɹ���";
		}
		else
		{
			message= "ɾ��ʧ�ܣ�";
		}
		
		request.getSession().setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
	}

	// �����Ʒ��������
	private void addToOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String clothingID = request.getParameter("clothingId");
		int number = Integer.parseInt(request.getParameter("number"));
		HttpSession session = request.getSession();
		// �Ƿ��ǵ�һ�������Ʒ����Ҫ��session�����һ���µĹ��ﳵ����
		if(session.getAttribute("cart") == null)
		{
			Cart cart = new Cart();
			session.setAttribute("cart", cart);
		}
		Cart cart = (Cart)session.getAttribute("cart");
		List<ClothingInfo> cloList = warehouseService.findAllById(clothingID);
		String message = null;
		// ���β�����
		if(cloList.size() == 0){
			message = "��װID"+ clothingID + "�����ڣ�<br/> ���������룡";
			session.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
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
			message =
					"���������"+ total + 
					"<br/> ����������" + orderNum + "<br/>��������������������������!";
			session.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
			return;
		}
		
		// ������������������
		if(cart.addGoodsInCart(cloList.get(0), number))
		{
			message = "��ӳɹ���";
		}
		session.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
	}
}
