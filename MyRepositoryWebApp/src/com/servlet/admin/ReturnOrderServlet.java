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
 * Servlet implementation class ReturnOrderServlet
 */
@WebServlet("/admin/ReturnOrderServlet")
public class ReturnOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	private String action;		// ����Ķ���
	private OrderService orderService = new OrderServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnOrderServlet() {
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
			if (action.equals("return_order"))		// �����������
			{
				returnOrder(request, response);
			}
		}
		else
		{
			request.getRequestDispatcher("/WEB-INF/view/admin/return_order.jsp").forward(request, response);
		}
		
	}

	private void returnOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// ��ȡ����
		Order order = (Order) request.getSession().getAttribute("return_order");
		String OrderID = order.getOrderInfo().getOrder_id();
		
		// �����˵���־
		orderService.updateSetState(OrderID, OrderInfo.RETURN);
		
		// ������Ʒ��־
		for (OrderItemInfo o : order.getGoodsList())
		{
			if(o.getPick_sign() == OrderItemInfo.PICK)
				o.setPick_sign(OrderItemInfo.RETURN_PICK);
			if(o.getPick_sign() == OrderItemInfo.NOT_PICK)
				o.setPick_sign(OrderItemInfo.RETURN_NOT_PICK);
			orderService.updateSetPickSign(o);
		}
		
		// 
		request.getSession().setAttribute("message" ,"�����˵��ɹ�!");
		request.getSession().removeAttribute("return_order");
		request.getRequestDispatcher("/WEB-INF/view/admin/return_order.jsp").forward(request, response);
	}

	private void getOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String order_id = request.getParameter("orderId");
		
		// ��ȡ����
		Order order = orderService.getOrder(order_id);
		
		// ������session��ȥ
		request.getSession().setAttribute("return_order", order);
		request.getRequestDispatcher("/WEB-INF/view/admin/return_order.jsp").forward(request, response);
	}

	private void getOrderSelectOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// ��ȡδ�����δ����
		List<OrderInfo> okList = orderService.findAllByState(OrderInfo.RESOLVED);
		List<OrderInfo> noList = orderService.findAllByState(OrderInfo.UNRESOLVED);
		
		okList.addAll(noList);

		String jsonStr = JSON.toJSONString(okList);
		//System.out.println(jsonStr);

		PrintWriter writer = response.getWriter();
		writer.write(jsonStr);
	}

}
