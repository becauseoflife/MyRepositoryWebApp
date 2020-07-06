package com.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.entity.Order;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;
import com.service.OrderService;
import com.service.impl.OrderServiceImpl;
 
/**
 * Servlet implementation class OrderSearchServlet
 */
@WebServlet("/admin/OrderSearchServlet")
public class OrderSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String action;		// ����Ķ���
	private OrderService orderService = new OrderServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(request.getParameter("action") != null)
		{
			this.action = request.getParameter("action");
			if(action.equals("getSelectOption"))	// ���δ�������б�
			{
				getOrderSelectOptions(request, response);
			}
			if(action.equals("getOrder"))			// ��ö�����Ϣ
			{
				getOrder(request, response);
			}
			if (action.equals("orderResolved"))		// �����������
			{
				orderResolved(request, response);
			}
		}
		else
		{
			request.getRequestDispatcher("/WEB-INF/view/admin/order.jsp").forward(request, response);
		}
		
	}

	private void orderResolved(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// ��ȡ����
		Order order = (Order) request.getSession().getAttribute("order");
		String OrderID = order.getOrderInfo().getOrder_id();
		
		// �ж϶����Ƿ������
		boolean flag = true;
		
		for (OrderItemInfo o : order.getGoodsList()) {
			if(o.getPick_sign() != OrderItemInfo.PICK)
				flag = false;
		}
		
		if(flag)	// ����ȫ�������
		{
			orderService.updateSetState(OrderID, OrderInfo.RESOLVED);
			
			request.getSession().removeAttribute("order"); 		// ɾ��session�еĶ�����Ϣ
			request.getSession().setAttribute("message", "����ɹ���");
			request.getRequestDispatcher("/WEB-INF/view/admin/order.jsp").forward(request, response);
			return;
		}
		else	// 	������δ����Ĳ�Ʒ
		{
			request.getSession().setAttribute("message", "���в�Ʒδ�����");
			request.getRequestDispatcher("/WEB-INF/view/admin/order.jsp").forward(request, response);
			return;
		}

	}

	private void getOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String order_id = request.getParameter("select_orderId");
		
		// ��ȡ����
		Order order = orderService.getOrder(order_id);
		
		// ������session��ȥ
		request.getSession().setAttribute("order", order);
		request.getRequestDispatcher("/WEB-INF/view/admin/order.jsp").forward(request, response);
	}

	private void getOrderSelectOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// ��ȡδ������
		List<OrderInfo> list = orderService.findAllByState(OrderInfo.UNRESOLVED);

		String jsonStr = JSON.toJSONString(list);
		//System.out.println(jsonStr);

		PrintWriter writer = response.getWriter();
		writer.write(jsonStr);
	}
}
