package com.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.entity.Order;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;
import com.service.OrderService;
import com.service.WarehouseService;
import com.service.impl.OrderServiceImpl;
import com.service.impl.WarehouseServiceImpl;

/**
 * Servlet implementation class ReturnHandleServlet
 */
@WebServlet("/user/ReturnHandleServlet")
public class ReturnHandleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String action;		// ����Ķ���
	private OrderService orderService = new OrderServiceImpl();
	private WarehouseService warehouseService = new WarehouseServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnHandleServlet() {
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
			if(action.equals("reback"))		// ��Ʒ�˻زֿ�
			{	
				reback(request, response);
			}
			if(action.equals("puton"))		// ��Ʒ�˻�ֿⲻ���ڣ����ϼ�
			{
				putOn(request, response);
			}
			if(action.equals("return_order_handle"))	// �˻������������
			{
				returnOrderHandle(request, response);
			}
		}
		else
		{
			request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
		}
	}

	private void returnOrderHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ����
		Order order = (Order)session.getAttribute("return_order_handle");
		
		// �ж���Ʒ�Ƿ�ȫ���˿�
		for(OrderItemInfo itemInfo : order.getGoodsList())
		{
			// ��δ�˿��
			if(itemInfo.getPick_sign() == OrderItemInfo.RETURN_PICK)
			{
				session.setAttribute("message", "����Ʒδ�˿�!");
				request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
				return;
			}
		}
		
		// �������¶���״̬
		orderService.updateSetState(order.getOrderInfo().getOrder_id(), OrderInfo.RETUEN_OK);
		
		// ɾ����Ϣ
		session.removeAttribute("return_order_handle");
		
		session.setAttribute("message", "����ɹ�!");
		
		request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
	}

	private void putOn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		// ��ȡ�ϼ���Ʒ
		OrderItemInfo itemInfo = (OrderItemInfo) session.getAttribute("putonItem");
		
		// ��ȡ�ϼ�λ��
		String position = request.getParameter("position");
		
		// ��Ʒ���
		String[] p = position.split("-");
		
		ClothingInfo updateClo = ClothingInfo.builder()
				.clothingID(itemInfo.getClothingID())
				.shelves(p[0])
				.location(p[1])
				.number(itemInfo.getNumber())
				.build();
		
		// ���������ݿ�
		if(warehouseService.updateSetIdAndNumber(updateClo))
		{
			// ���±��
			itemInfo.setPick_sign(OrderItemInfo.RETURN_NOT_PICK);
			orderService.updateSetPickSign(itemInfo);
			
			// ������Ϣ
			String message = "����ɹ�!</br> �뽫��Ʒ���òֿ��<strong>" + position + "</strong>λ�ã�";
			session.setAttribute("message", message);
		}
		else
		{
			String message = "�ϼ�ʧ�ܣ�";
			session.setAttribute("message", message);
		}
		
		// ɾ����Ϣ
		session.removeAttribute("allEmptyPosition");
		session.removeAttribute("putonItem");
		
		request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
	}

	private void reback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		// ��ȡ����
		Order order = (Order)session.getAttribute("return_order_handle");
		
		// ��ȡ�±�
		int index = Integer.parseInt(request.getParameter("index"));
		
		// ��÷�װ��Ϣ
		OrderItemInfo itemInfo = order.getGoodsList().get(index);
		
		// ��ѯ����
		ClothingInfo cInfo = warehouseService.findById(itemInfo.getClothingID());
		
		// ��װ����
		if(cInfo != null)
		{
			// ��������
			cInfo.setNumber(cInfo.getNumber() + itemInfo.getNumber());
			warehouseService.updateSetNumber(cInfo);
			
			// ���±��
			itemInfo.setPick_sign(OrderItemInfo.RETURN_NOT_PICK);
			orderService.updateSetPickSign(itemInfo);
			
			// ������Ϣ
			String message = "����ɹ�!</br> �뽫��Ʒ���òֿ��<strong>" + cInfo.getShelves() + "-" + cInfo.getLocation() + "</strong>λ�ã�";
			session.setAttribute("message", message);
			
			request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
			return;
		}
		else	// ��װ������
		{
			// ��ȡ��λ��
			List<ClothingInfo> allEmptyPosition = warehouseService.findAllWithEmpty();
			
			session.setAttribute("allEmptyPosition", allEmptyPosition);
			session.setAttribute("putonItem", itemInfo);
			
			request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
			return;
		}

	}

	private void getOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String order_id = request.getParameter("returnOrderId");
		
		// ��ȡ����
		Order order = orderService.getOrder(order_id);
		
		// ������session��ȥ
		request.getSession().setAttribute("return_order_handle", order);
		request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
	}

	private void getOrderSelectOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		// ��ȡ�˻�����
		List<OrderInfo> returnList = orderService.findAllByState(OrderInfo.RETURN);


		String jsonStr = JSON.toJSONString(returnList);
		//System.out.println(jsonStr);

		PrintWriter writer = response.getWriter();
		writer.write(jsonStr);
	}



}
