package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.entity.Order;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;
import com.service.OrderService;
import com.service.ExportService;
import com.service.WarehouseService;
import com.service.impl.OrderServiceImpl;
import com.service.impl.ExportServiceImp;
import com.service.impl.WarehouseServiceImpl;

/**
 * Servlet implementation class PickGoodsServlet
 */
@WebServlet("/PickGoodsServlet")
public class PickGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String action;		// ����Ķ���

	private WarehouseService warehouseService = new WarehouseServiceImpl();
	private OrderService orderService = new OrderServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PickGoodsServlet() {
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
		
		//System.out.println("action:" + request.getParameter("action"));
		
		ExportService service = new ExportServiceImp();
		
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
			if(action.equals("pickGood"))			// ���м������
			{
				pickGood(request, response);
			}
			if (action.equals("orderResolved"))		// �����������
			{
				orderResolved(request, response);
			}
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
			request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
			return;
		}
		else	// 	������δ����Ĳ�Ʒ
		{
			request.getSession().setAttribute("message", "���в�Ʒδ�����");
			request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
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
		request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
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

	private void pickGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// ��ȡ����ķ�װID�Ͷ����б�
		String clothingID = request.getParameter("clothingId");
		Order order = (Order) request.getSession().getAttribute("order");
		
		String message = null;
		// �Ӳֿ��л�ȡ����
		ClothingInfo clothingInfo = warehouseService.findById(clothingID);
		
		// ��session�еĻ�ȡ������Ϣ
		for (OrderItemInfo orderItem : order.getGoodsList()) {
			// �������и÷��μ�¼
			if(clothingID.equals(orderItem.getClothingID()))
			{
				// �Ѽ��
				if(orderItem.getPick_sign() == OrderItemInfo.PICK)
				{
					message = "��Ʒ:"+clothingID+" �Ѽ��<br/>������ѡ������Ʒ";
					request.getSession().setAttribute("message", message);
					request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
					return;
				}
				
				// ����㹻 ���м������
				if(clothingInfo.getNumber() >= orderItem.getNumber()){
					// ���ý����е�����
//					OrderItemInfo updateItem = new OrderItemInfo();
//					updateItem.setOrder_id(orderItem.getOrder_id());
//					updateItem.setClothingID(orderItem.getClothingID());
//					updateItem.setShelves(orderItem.getShelves());
//					updateItem.setLocation(orderItem.getLocation());
//					updateItem.setNumber(orderItem.getNumber());
//					updateItem.setPick_sign(OrderItemInfo.PICK);
//					updateItem.setPick_time(new Date());
					
					orderItem.setPick_sign(OrderItemInfo.PICK);
					orderItem.setPick_time(new Date());
					
					// �������ݿ��и���Ʒ�ļ����־�ͼ��ʱ��
					orderService.update(orderItem);
					
					// �ֿ��е���������
					clothingInfo.setNumber(clothingInfo.getNumber()-orderItem.getNumber());
					warehouseService.updateSetNumber(clothingInfo);
					
					message = "����ɹ���";
					request.getSession().setAttribute("message", message);
					request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
					return;
					
				}
				// ��治��
				else	
				{
					message = "��治�㣡";
					request.getSession().setAttribute("message", message);
					request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
					return;
				}
			}
		}
		// �����в����ڸ���Ʒ
		message = "�����в����ڸ÷�װ��";
		request.getSession().setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
		return;
	}

}
