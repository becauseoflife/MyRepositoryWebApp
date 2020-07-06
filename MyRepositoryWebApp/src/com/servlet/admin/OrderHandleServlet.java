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
       
	private String action;		// ����Ķ���
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
			if(action.equals("InitInfo"))	// ����û��б�
			{
				InitInfo(request, response);
			}
			if(action.equals("allocation"))		// �����û�
			{
				allocation(request, response);
			}
		}
		
	}

	private void allocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		// ��÷����û���
		String username = request.getParameter("select_username");
		int index = Integer.parseInt(request.getParameter("index"));
		
		List<OrderItemInfo> itemInfoList = (List<OrderItemInfo>)session.getAttribute("itemInfoList");
		
		// ��ȡ����������Ʒ
		OrderItemInfo itemInfo = itemInfoList.get(index);
		
		// �ж��Ƿ񳬹�5��
		int sum = orderService.sumForNumber(username, OrderItemInfo.NOT_PICK);
		System.out.println("������" + sum);
		if(sum >= 5)
		{
			String message = "����ʧ��!"
					+ "</br>�û�<strong>"+ username + "</strong>�Ѿ�����������:<strong>" + sum +"</strong>"
					+ "</br>������������ܳ���<strong> 5 </strong>��!";
			session.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/admin/item_handle.jsp").forward(request, response);
			return;
		}
	
		itemInfo.setPick_user(username);
		
		if(orderService.updateSetPickUser(itemInfo))
		{
			session.setAttribute("message", "����ɹ���");
		}
		else
		{
			itemInfo.setPick_user("δ����");
			session.setAttribute("message", "����ʧ�ܣ�");
		}
		
		request.getRequestDispatcher("/WEB-INF/view/admin/item_handle.jsp").forward(request, response);
	}

	private void InitInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		// ���ȫ���û�
		List<UserInfo> userList = userAccountService.findAll();
		
		request.getSession().setAttribute("userList", userList);
		
		// ��ȡδ�������Ʒ
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
