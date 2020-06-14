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
	
	private String action;		// 界面的动作

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
			if(action.equals("getSelectOption"))	// 获得未处理订单列表
			{
				getOrderSelectOptions(request, response);
			}
			if(action.equals("getOrder"))			// 获得订单信息
			{
				getOrder(request, response);
			}
			if(action.equals("pickGood"))			// 进行拣货操作
			{
				pickGood(request, response);
			}
			if (action.equals("orderResolved"))		// 订单处理完成
			{
				orderResolved(request, response);
			}
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
			request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
			return;
		}
		else	// 	订单有未拣货的产品
		{
			request.getSession().setAttribute("message", "还有产品未拣货！");
			request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
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
		request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
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

	private void pickGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 获取拣货的服装ID和订单列表
		String clothingID = request.getParameter("clothingId");
		Order order = (Order) request.getSession().getAttribute("order");
		
		String message = null;
		// 从仓库中获取服饰
		ClothingInfo clothingInfo = warehouseService.findById(clothingID);
		
		// 从session中的获取订购信息
		for (OrderItemInfo orderItem : order.getGoodsList()) {
			// 订单中有该服饰记录
			if(clothingID.equals(orderItem.getClothingID()))
			{
				// 已拣货
				if(orderItem.getPick_sign() == OrderItemInfo.PICK)
				{
					message = "商品:"+clothingID+" 已拣货<br/>请重新选择拣货商品";
					request.getSession().setAttribute("message", message);
					request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
					return;
				}
				
				// 库存足够 进行拣货过程
				if(clothingInfo.getNumber() >= orderItem.getNumber()){
					// 设置界面中的数据
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
					
					// 更新数据库中该商品的拣货标志和拣货时间
					orderService.update(orderItem);
					
					// 仓库中的数量更新
					clothingInfo.setNumber(clothingInfo.getNumber()-orderItem.getNumber());
					warehouseService.updateSetNumber(clothingInfo);
					
					message = "拣货成功！";
					request.getSession().setAttribute("message", message);
					request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
					return;
					
				}
				// 库存不足
				else	
				{
					message = "库存不足！";
					request.getSession().setAttribute("message", message);
					request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
					return;
				}
			}
		}
		// 订单中不存在该商品
		message = "订单中不存在该服装！";
		request.getSession().setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
		return;
	}

}
