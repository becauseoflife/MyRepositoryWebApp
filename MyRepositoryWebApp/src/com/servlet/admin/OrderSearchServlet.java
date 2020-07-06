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
       
	private String action;		// 界面的动作
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
			if(action.equals("getSelectOption"))	// 获得未处理订单列表
			{
				getOrderSelectOptions(request, response);
			}
			if(action.equals("getOrder"))			// 获得订单信息
			{
				getOrder(request, response);
			}
			if (action.equals("orderResolved"))		// 订单处理完成
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
		// 获取订单
		Order order = (Order) request.getSession().getAttribute("order");
		String OrderID = order.getOrderInfo().getOrder_id();
		
		// 判断订单是否拣货完毕
		boolean flag = true;
		
		for (OrderItemInfo o : order.getGoodsList()) {
			if(o.getPick_sign() != OrderItemInfo.PICK)
				flag = false;
		}
		
		if(flag)	// 订单全部被拣货
		{
			orderService.updateSetState(OrderID, OrderInfo.RESOLVED);
			
			request.getSession().removeAttribute("order"); 		// 删除session中的订单信息
			request.getSession().setAttribute("message", "处理成功！");
			request.getRequestDispatcher("/WEB-INF/view/admin/order.jsp").forward(request, response);
			return;
		}
		else	// 	订单有未拣货的产品
		{
			request.getSession().setAttribute("message", "还有产品未拣货！");
			request.getRequestDispatcher("/WEB-INF/view/admin/order.jsp").forward(request, response);
			return;
		}

	}

	private void getOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String order_id = request.getParameter("select_orderId");
		
		// 获取订单
		Order order = orderService.getOrder(order_id);
		
		// 保存在session中去
		request.getSession().setAttribute("order", order);
		request.getRequestDispatcher("/WEB-INF/view/admin/order.jsp").forward(request, response);
	}

	private void getOrderSelectOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// 获取未处理订单
		List<OrderInfo> list = orderService.findAllByState(OrderInfo.UNRESOLVED);

		String jsonStr = JSON.toJSONString(list);
		//System.out.println(jsonStr);

		PrintWriter writer = response.getWriter();
		writer.write(jsonStr);
	}
}
