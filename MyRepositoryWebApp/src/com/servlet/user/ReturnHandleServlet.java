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
       
	private String action;		// 界面的动作
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
			if(action.equals("getSelectOption"))	// 获得未处理订单列表
			{
				getOrderSelectOptions(request, response);
			}
			if(action.equals("getOrder"))			// 获得订单信息
			{
				getOrder(request, response);
			}
			if(action.equals("reback"))		// 货品退回仓库
			{	
				reback(request, response);
			}
			if(action.equals("puton"))		// 货品退会仓库不存在，则上架
			{
				putOn(request, response);
			}
			if(action.equals("return_order_handle"))	// 退货订单处理完成
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
		// 获取订单
		Order order = (Order)session.getAttribute("return_order_handle");
		
		// 判断商品是否全部退库
		for(OrderItemInfo itemInfo : order.getGoodsList())
		{
			// 有未退库的
			if(itemInfo.getPick_sign() == OrderItemInfo.RETURN_PICK)
			{
				session.setAttribute("message", "有商品未退库!");
				request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
				return;
			}
		}
		
		// 完成则更新订单状态
		orderService.updateSetState(order.getOrderInfo().getOrder_id(), OrderInfo.RETUEN_OK);
		
		// 删除信息
		session.removeAttribute("return_order_handle");
		
		session.setAttribute("message", "处理成功!");
		
		request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
	}

	private void putOn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		// 获取上架商品
		OrderItemInfo itemInfo = (OrderItemInfo) session.getAttribute("putonItem");
		
		// 获取上架位置
		String position = request.getParameter("position");
		
		// 商品入库
		String[] p = position.split("-");
		
		ClothingInfo updateClo = ClothingInfo.builder()
				.clothingID(itemInfo.getClothingID())
				.shelves(p[0])
				.location(p[1])
				.number(itemInfo.getNumber())
				.build();
		
		// 更新新数据库
		if(warehouseService.updateSetIdAndNumber(updateClo))
		{
			// 更新标记
			itemInfo.setPick_sign(OrderItemInfo.RETURN_NOT_PICK);
			orderService.updateSetPickSign(itemInfo);
			
			// 返回信息
			String message = "处理成功!</br> 请将商品放置仓库的<strong>" + position + "</strong>位置！";
			session.setAttribute("message", message);
		}
		else
		{
			String message = "上架失败！";
			session.setAttribute("message", message);
		}
		
		// 删除信息
		session.removeAttribute("allEmptyPosition");
		session.removeAttribute("putonItem");
		
		request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
	}

	private void reback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		// 获取订单
		Order order = (Order)session.getAttribute("return_order_handle");
		
		// 获取下标
		int index = Integer.parseInt(request.getParameter("index"));
		
		// 获得服装信息
		OrderItemInfo itemInfo = order.getGoodsList().get(index);
		
		// 查询服饰
		ClothingInfo cInfo = warehouseService.findById(itemInfo.getClothingID());
		
		// 服装存在
		if(cInfo != null)
		{
			// 更新数量
			cInfo.setNumber(cInfo.getNumber() + itemInfo.getNumber());
			warehouseService.updateSetNumber(cInfo);
			
			// 更新标记
			itemInfo.setPick_sign(OrderItemInfo.RETURN_NOT_PICK);
			orderService.updateSetPickSign(itemInfo);
			
			// 返回信息
			String message = "处理成功!</br> 请将商品放置仓库的<strong>" + cInfo.getShelves() + "-" + cInfo.getLocation() + "</strong>位置！";
			session.setAttribute("message", message);
			
			request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
			return;
		}
		else	// 服装不存在
		{
			// 获取空位置
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
		
		// 获取订单
		Order order = orderService.getOrder(order_id);
		
		// 保存在session中去
		request.getSession().setAttribute("return_order_handle", order);
		request.getRequestDispatcher("/WEB-INF/view/user/return_order_handle.jsp").forward(request, response);
	}

	private void getOrderSelectOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		// 获取退货订单
		List<OrderInfo> returnList = orderService.findAllByState(OrderInfo.RETURN);


		String jsonStr = JSON.toJSONString(returnList);
		//System.out.println(jsonStr);

		PrintWriter writer = response.getWriter();
		writer.write(jsonStr);
	}



}
