package com.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.entity.Order;
import com.entity.SearchResult;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;
import com.mapper.UserInfo;
import com.service.OrderService;
import com.service.ExportService;
import com.service.WarehouseService;
import com.service.impl.OrderServiceImpl;
import com.service.impl.ExportServiceImp;
import com.service.impl.WarehouseServiceImpl;

/**
 * Servlet implementation class PickGoodsServlet
 */
@WebServlet("/user/PickGoodsServlet")
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
		
		if(request.getParameter("action") != null)
		{
			this.action = request.getParameter("action");
			if(action.equals("pickGood"))			// ���м������
			{
				pickGood(request, response);
			}
			if(action.equals("search"))				// �鿴��Ʒλ��
			{
				searchLocation(request, response);
			}
			if(action.equals("update"))			// ��ȡ��������б�
			{
				getPickGoodList(request, response);
			}
		}
		
	}

	private void getCheckTask(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void getPickGoodList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ��¼���û�
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		// ���ҳ��Լ��ļ������
		List<OrderItemInfo> myPickGoodList = orderService.findAllItemByPickUser(user.getUserName(), OrderItemInfo.NOT_PICK);
		
		//���浽session��
		session.setAttribute("myPickGoodList", myPickGoodList);
		request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
	}

	private void searchLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String clothingID = request.getParameter("ClothingID");
		
		List<ClothingInfo> list = warehouseService.findAllById(clothingID);
		
		String message = "";		//���ظ�ǰ�˵���Ϣ 
		
		// δ�鵽��¼
		if (list.size()==0) 
		{
			message = "δ�鵽��Ʒ��Ϣ��";
			session.setAttribute("message", message);
			
			request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
			return;			
		}
		
		
		int sum = 0;	// �·�����
		String position = "";	// �·�λ��
		
		for (ClothingInfo c : list) {
			sum += c.getNumber();
			position += c.getShelves() + "-" + c.getLocation() + "  "; 
		}
		
		// �����ѯ���
		message = "��Ʒλ��: <strong>" + position + "</strong><br/>" +
				  "��Ʒ����: <strong>" + sum + "</strong>";
		// ���浽session��
		session.setAttribute("message", message);
		
		request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
		return;	
	}


	private void pickGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ����ķ�װID�Ͷ����б�
		String clothingID = request.getParameter("clothingId");
		List<OrderItemInfo> myPickGoodList = (List<OrderItemInfo>) session.getAttribute("myPickGoodList");
		
		String message = null;
		// �Ӳֿ��л�ȡ����
		ClothingInfo clothingInfo = warehouseService.findById(clothingID);
		
		// �����в����ڸ���Ʒ
		if(clothingInfo == null)
		{
			message = "�ֿ��в����ڸ÷�װ��";
			request.getSession().setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
			return;
		}
		
		// ��session�еĻ�ȡ������Ϣ
		for (OrderItemInfo orderItem : myPickGoodList)
		{
			// �������и÷��μ�¼
			if(clothingID.equals(clothingInfo.getClothingID()))
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
					orderService.updateSetPickSign(orderItem);
					
					// �ֿ��е���������
					clothingInfo.setNumber(clothingInfo.getNumber()-orderItem.getNumber());
					warehouseService.updateSetNumber(clothingInfo);
					
					// �Ƴ�
					myPickGoodList.remove(orderItem);
					
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
			else	// ���񲻴��ڸ÷���
			{
				message = "�����ڸû�Ʒ�ļ������";
				request.getSession().setAttribute("message", message);
				request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
				return;
			}
		}
		
		message = "�����ڸû�Ʒ�ļ������";
		request.getSession().setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
		return;
		
	}

}
