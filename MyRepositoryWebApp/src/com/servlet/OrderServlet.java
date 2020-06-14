package com.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.enterprise.inject.New;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Cart;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.UserInfo;
import com.service.OrderService;
import com.service.ExportService;
import com.service.WarehouseService;
import com.service.impl.OrderServiceImpl;
import com.service.impl.ExportServiceImp;
import com.service.impl.WarehouseServiceImpl;
import com.util.CreateOrderID;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String action;		// 订单界面的动作
	
	private WarehouseService warehouseService = new WarehouseServiceImpl();
	
	private OrderService orderService = new OrderServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//System.out.println("action:" + request.getParameter("action"));
		
		ExportService service = new ExportServiceImp();
		
		if(request.getParameter("action") != null)
		{
			this.action = request.getParameter("action");
			if(action.equals("add"))		// 添加进购物车
			{
				addToOrder(request, response);
			}
			if(action.equals("delete"))		// 从订单中删除商品
			{
				deleteFormOrder(request, response);
			}
			if(action.equals("create"))		// 创建订单
			{
				createOrder(request, response);
			}
		}
	}

	private void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String message = null;
		// 获取用户ID
		String userName = ((UserInfo)request.getSession().getAttribute("user")).getUserName();
		// 获取订单
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		
		// 创建订单ID
		String orderID = CreateOrderID.getOrderIdGenerator().nextOrderID();
		
		// 订单为空
		if( cart==null || cart.getGoods().isEmpty())
		{
			message = "订单订购列表为空！<br/>请先添加商品！";
			request.getSession().setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
		}
		
		// 创建订单记录
		OrderInfo orderInfo = OrderInfo.builder()
				.order_id(orderID)
				.username(userName)
				.state(OrderInfo.UNRESOLVED)
				.create_time(new Date())
				.build();
		
/*		orderInfo.setOrder_id(orderID);
		orderInfo.setUsername(userName);
		orderInfo.setState(OrderInfo.UNRESOLVED);
		orderInfo.setCreate_time(new Date());*/
		
		orderService.createOrder(orderInfo, cart);
		
		// 跳转到订单拣货
		message = "生成订单成功！";
		request.getSession().setAttribute("message", message);
		// 将订单列表清空
		cart = null;
		request.getSession().setAttribute("cart", cart);
		
		request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
	}

	// 从订单中删除商品
	private void deleteFormOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String clothingID = request.getParameter("clothingId");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		
		ClothingInfo item = warehouseService.findById(clothingID);
		String message = null;
		
		// 从购物车中删除
		if(cart.removeGoodFormCart(item))
		{
			message= "删除成功！";
		}
		else
		{
			message= "删除失败！";
		}
		
		request.getSession().setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
	}

	// 添加商品到订单中
	private void addToOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String clothingID = request.getParameter("clothingId");
		int number = Integer.parseInt(request.getParameter("number"));
		HttpSession session = request.getSession();
		// 是否是第一次添加物品，需要给session中添加一个新的购物车对象
		if(session.getAttribute("cart") == null)
		{
			Cart cart = new Cart();
			session.setAttribute("cart", cart);
		}
		Cart cart = (Cart)session.getAttribute("cart");
		List<ClothingInfo> cloList = warehouseService.findAllById(clothingID);
		String message = null;
		// 服饰不存在
		if(cloList.size() == 0){
			message = "服装ID"+ clothingID + "不存在！<br/> 请重新输入！";
			session.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
			return;
		}
		
		// 服饰存在
		int total = 0;
		int orderNum = number; 
		// 计算出仓库中服饰的总数量
		for (ClothingInfo c : cloList) {
			total += c.getNumber();
		}
		// 计算出订单中的总数量
		if(cart.getGoods().get(cloList.get(0))!=null){
			orderNum += cart.getGoods().get(cloList.get(0));
		}
		// 添加的数量大于库存，添加失败
		if( orderNum > total)
		{
			message =
					"库存数量："+ total + 
					"<br/> 订购数量：" + orderNum + "<br/>数量超出库存数量，请重新添加!";
			session.setAttribute("message", message);
			request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
			return;
		}
		
		// 上面两个条件都满足
		if(cart.addGoodsInCart(cloList.get(0), number))
		{
			message = "添加成功！";
		}
		session.setAttribute("message", message);
		request.getRequestDispatcher("/WEB-INF/view/user/order_good.jsp").forward(request, response);
	}
}
