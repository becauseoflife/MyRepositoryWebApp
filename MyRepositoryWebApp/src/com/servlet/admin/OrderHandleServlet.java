package com.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mapper.OrderItemInfo;
import com.mapper.UserInfo;
import com.service.OrderService;
import com.service.UserAccountService;
import com.service.impl.OrderServiceImpl;
import com.service.impl.UserAccountServiceImpl;
 
/**
 * Servlet implementation class OrderHandleServlet
 */
@WebServlet("/admin/OrderHandleServlet")
public class OrderHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String action;		// 界面的动作
	private UserAccountService userAccountService = new UserAccountServiceImpl();
	private OrderService orderService = new OrderServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderHandleServlet() {
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
			System.out.println(action);
			if(action.equals("InitInfo"))	// 获得用户列表
			{
				InitInfo(request, response);
			}
			if(action.equals("allocation"))		// 分配用户
			{
				allocation(request, response);
			}
		}
		
	}

	private void allocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		// 获得分配用户名
		String username = request.getParameter("select_username");
		int index = Integer.parseInt(request.getParameter("index"));
		
		List<OrderItemInfo> itemInfoList = (List<OrderItemInfo>)session.getAttribute("itemInfoList");
		
		// 获取分配拣货的商品
		OrderItemInfo itemInfo = itemInfoList.get(index);
		
		// 判断是否超过5件
		int sum = orderService.sumForNumber(username, OrderItemInfo.NOT_PICK);
		System.out.println("总数：" + sum);
		if(sum >= 5)
		{
			String message = "分配失败!"
					+ "</br>用户<strong>"+ username + "</strong>已经分配任务数:<strong>" + sum +"</strong>"
					+ "</br>最大任务数不能超过<strong> 5 </strong>件!";
			session.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/admin/item_handle.jsp").forward(request, response);
			return;
		}
	
		itemInfo.setPick_user(username);
		
		if(orderService.updateSetPickUser(itemInfo))
		{
			session.setAttribute("message", "分配成功！");
		}
		else
		{
			itemInfo.setPick_user("未分配");
			session.setAttribute("message", "分配失败！");
		}
		
		request.getRequestDispatcher("/WEB-INF/view/admin/item_handle.jsp").forward(request, response);
	}

	private void InitInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		// 获得全部用户
		List<UserInfo> userList = userAccountService.findAll();
		
		request.getSession().setAttribute("userList", userList);
		
		// 获取未拣货的商品
		List<OrderItemInfo> itemInfoList = orderService.findAllItemByPickSign(OrderItemInfo.NOT_PICK);
		
		request.getSession().setAttribute("itemInfoList", itemInfoList);
		
		request.getRequestDispatcher("/WEB-INF/view/admin/item_handle.jsp").forward(request, response);
//		String jsonStr = JSON.toJSONString(userList);
//		//System.out.println(jsonStr);
//
//		PrintWriter writer = response.getWriter();
//		writer.write(jsonStr);
		
	}



}
