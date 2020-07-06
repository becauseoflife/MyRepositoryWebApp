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
		
		if(request.getParameter("action") != null)
		{
			this.action = request.getParameter("action");
			if(action.equals("pickGood"))			// 进行拣货操作
			{
				pickGood(request, response);
			}
			if(action.equals("search"))				// 查看商品位置
			{
				searchLocation(request, response);
			}
			if(action.equals("update"))			// 获取拣货任务列表
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
		// 获取登录的用户
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		// 查找出自己的拣货任务
		List<OrderItemInfo> myPickGoodList = orderService.findAllItemByPickUser(user.getUserName(), OrderItemInfo.NOT_PICK);
		
		//保存到session中
		session.setAttribute("myPickGoodList", myPickGoodList);
		request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
	}

	private void searchLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String clothingID = request.getParameter("ClothingID");
		
		List<ClothingInfo> list = warehouseService.findAllById(clothingID);
		
		String message = "";		//返回给前端的信息 
		
		// 未查到记录
		if (list.size()==0) 
		{
			message = "未查到商品信息！";
			session.setAttribute("message", message);
			
			request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
			return;			
		}
		
		
		int sum = 0;	// 衣服总数
		String position = "";	// 衣服位置
		
		for (ClothingInfo c : list) {
			sum += c.getNumber();
			position += c.getShelves() + "-" + c.getLocation() + "  "; 
		}
		
		// 构造查询结果
		message = "商品位置: <strong>" + position + "</strong><br/>" +
				  "商品总数: <strong>" + sum + "</strong>";
		// 保存到session中
		session.setAttribute("message", message);
		
		request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
		return;	
	}


	private void pickGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取拣货的服装ID和订单列表
		String clothingID = request.getParameter("clothingId");
		List<OrderItemInfo> myPickGoodList = (List<OrderItemInfo>) session.getAttribute("myPickGoodList");
		
		String message = null;
		// 从仓库中获取服饰
		ClothingInfo clothingInfo = warehouseService.findById(clothingID);
		
		// 订单中不存在该商品
		if(clothingInfo == null)
		{
			message = "仓库中不存在该服装！";
			request.getSession().setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
			return;
		}
		
		// 从session中的获取订购信息
		for (OrderItemInfo orderItem : myPickGoodList)
		{
			// 订单中有该服饰记录
			if(clothingID.equals(clothingInfo.getClothingID()))
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
					orderService.updateSetPickSign(orderItem);
					
					// 仓库中的数量更新
					clothingInfo.setNumber(clothingInfo.getNumber()-orderItem.getNumber());
					warehouseService.updateSetNumber(clothingInfo);
					
					// 移除
					myPickGoodList.remove(orderItem);
					
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
			else	// 任务不存在该服饰
			{
				message = "不存在该货品的拣货任务！";
				request.getSession().setAttribute("message", message);
				request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
				return;
			}
		}
		
		message = "不存在该货品的拣货任务！";
		request.getSession().setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/view/user/pick_good.jsp").forward(request, response);
		return;
		
	}

}
