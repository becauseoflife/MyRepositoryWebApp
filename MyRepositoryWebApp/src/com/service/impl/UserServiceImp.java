package com.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.event.IIOReadProgressListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.dao.OrderDao;
import com.dao.OrderGoodsDao;
import com.dao.UserDao;
import com.dao.WarehouseDao;
import com.dao.impl.OrderDaoImpl;
import com.dao.impl.OrderGoodsDaoImpl;
import com.dao.impl.WarehouseDaoImpl;
import com.entity.Cart;
import com.entity.Check;
import com.entity.CheckTable;
import com.entity.ClothingInfo;
import com.entity.Order;
import com.entity.OrderGoods;
import com.entity.User;
import com.service.UserService;
import com.simplefactory.UserDBHelperFactory;
import com.util.CreateOrderID;
import com.util.ExportExcelUtil;

public class UserServiceImp implements UserService {

	// 获取用户数据库操作类
	private UserDao userDBHelper = UserDBHelperFactory.getUserDBHelperDao();
	
	// 仓库表操作类
	private WarehouseDao warehouseDBHelper = new WarehouseDaoImpl();
	
	// 订单记录表操作类
	private OrderDao orderBDHelper = new OrderDaoImpl();
	
	// 订单物品记录表操作类
	private OrderGoodsDao orderGoodsDBHelper = new OrderGoodsDaoImpl();

	@Override
	public boolean login(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("userName"); 	// 用户名
		String password = request.getParameter("passWord");		// 密码
		
		User user = null;
		try {
			user = userDBHelper.queryByUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		// 验证用户登录信息
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(3600 * 24 * 10);		// 最大生存时间
		if(user != null && user.getPassword().equals(password))
		{
			session.setAttribute("user", user);		// 保存用户信息到session中去
			return true;		// 登录成功
		}
		else if(user == null)
		{
			session.setAttribute("message", "用户不存在，请先注册!");
			return false;
		}
		else if(user != null && !user.getPassword().equals(password))
		{
			session.setAttribute("message", "密码错误，请重新输入!");
			return false;
		}
		
		return false;
	}

	
	@Override
	public boolean register(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("registerUsername"); 	// 用户名
		String password = request.getParameter("registerPassword");		// 密码
		String telephone = request.getParameter("registerTelephone");	// 联系电话
		String email = request.getParameter("registerEmail");			// 电子邮件
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
	
		// 新建用户
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setTelephone(telephone);
		user.setEmail(email);
		user.setRegDate(new Date());
		
		try {
			HttpSession session = request.getSession();
			if (userDBHelper.insert(user)) {
				
				session.setAttribute("message", "注册成功！");
				return true;
			}else{
				session.setAttribute("message", "注册失败！<br/>"+userName+"用户名已存在！");
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}


	@Override
	public boolean queryClothingById(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String clothingID = request.getParameter("clothingId");
		List<ClothingInfo> list = warehouseDBHelper.queryListByClothingID(clothingID);
		
/*		if(list.size()==0)	// 没有查到记录	
			return false;*/

		int sum = 0;	// 衣服总数
		List<String> indexList = new ArrayList<String>();	// 衣服位置
		
		for (ClothingInfo c : list) {
			sum += c.getNumber();
			indexList.add(c.getShelves() + "-" + c.getLocation());
		}
		
		// 保存到session中
		HttpSession session = request.getSession();
		session.setAttribute("clothingID", clothingID);	// 服装ID
		session.setAttribute("isSearch", true);			// 是否查找
		session.setAttribute("clothingSum", sum);		// 服装数量
		session.setAttribute("indexList", indexList);	// 位置列表
		
		return true;
		
	}


	@Override
	public void addToCart(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String clothingID = request.getParameter("clothingId");
		int number = Integer.parseInt(request.getParameter("number"));
		HttpSession session = request.getSession();
		if(number<0)
			return;
		
		// 是否是第一次添加物品，需要给session中添加一个新的购物车对象
		if(session.getAttribute("order") == null)
		{
			Cart cart = new Cart();
			session.setAttribute("order", cart);
		}
		Cart cart = (Cart)session.getAttribute("order");
		
		// 获取仓库中的服饰
		List<ClothingInfo> cloList = warehouseDBHelper.queryListByClothingID(clothingID);
		// 服饰不存在
		if(cloList.size() == 0){
			session.setAttribute("message", "服装ID"+ clothingID + "不存在！<br/> 请重新输入！");
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
			session.setAttribute("message",
					"库存数量："+ total + 
					"<br/> 订购数量：" + orderNum + "<br/>数量超出库存数量，请重新添加!"
					);
			return;
		}
		
		
		// 上面两个条件都满足
		if(cart.addGoodsInCart(cloList.get(0), number))
		{
			//session.setAttribute("message", "添加成功！");
			return;
		}
		
		
	}


	@Override
	public void deleteFormCart(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String clothingID = request.getParameter("clothingId");
		
		//System.out.println("clothingID: " + clothingID );
		// 订单
		Cart cart = (Cart)request.getSession().getAttribute("order");
		ClothingInfo deleteItem = warehouseDBHelper.queryByClothingID(clothingID);
		
		// 从购物车中删除
		if(cart.removeGoodFormCart(deleteItem))
		{
			request.getSession().setAttribute("message", "删除成功！");
		}
		else
		{
			request.getSession().setAttribute("message", "删除失败！");
		}
		
	}


	@Override
	public void createOrder(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		// 获取用户
		User user = (User)request.getSession().getAttribute("user");
		// 获取订单
		Cart cart = (Cart)request.getSession().getAttribute("order");
		// 创建订单ID
		String orderID = CreateOrderID.getOrderIdGenerator().nextOrderID();
		
		// 订单为空
		if( cart==null || cart.getGoods().isEmpty())
		{
			request.getSession().setAttribute("message", "订单订购列表为空！<br/>请先添加商品！");
			return;
		}
		
		// 创建订单记录
		Order order = new Order();
		order.setOrder_id(orderID);
		order.setUsername(user.getUserName());
		order.setState(Order.UNRESOLVED);
		order.setCreate_time(new Date());
		
		// 插入订单记录数据库
		if(orderBDHelper.insert(order))
		{
			// 将订单中的商品保存到数据库中
			HashMap<ClothingInfo, Integer> goods = cart.getGoods();
			Set<ClothingInfo> items = goods.keySet();
  			Iterator<ClothingInfo> it = items.iterator();
			while(it.hasNext())
			{
				OrderGoods g = new OrderGoods();
				ClothingInfo c = it.next();
				g.setOrder_id(orderID);
				g.setShelves(c.getShelves());
				g.setLocation(c.getLocation());
				g.setClothingID(c.getClothingID());
				g.setNumber(goods.get(c));
				g.setPick_sign(OrderGoods.NOT_PICK);
				g.setPick_time(null);

				orderGoodsDBHelper.insert(g);
			}
			
			// 跳转到订单拣货
			request.getSession().setAttribute("message", "生成订单成功！");
			// 将订单列表清空
			cart = null;
			request.getSession().setAttribute("order", cart);
			return;
		}
		else
		{
			request.getSession().setAttribute("message", "生成订单失败！");
		}
		
	}


	@Override
	public void getOrderSelectOptions(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		List<Order> list = orderBDHelper.queryOrderListByState(Order.UNRESOLVED);	// 获取未处理的订单
		  
		PrintWriter writer;
		String jsonStr = JSON.toJSONString(list);
		//System.out.println(jsonStr);
		try {
			writer = response.getWriter();
			writer.write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void getOrderInfo(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String order_id = request.getParameter("select_orderId");

		// 获取订单列表
		List<OrderGoods> goodsList = orderGoodsDBHelper.queryByOrderID(order_id);
		
		// 保存在session中去
		request.getSession().setAttribute("order_goods_list", goodsList);
		
		return;
	}


	@Override
	public void pickGood(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取拣货的服装ID和订单列表
		String clothingID = request.getParameter("clothingId");
		List<OrderGoods> goodsList = (List<OrderGoods>) session.getAttribute("order_goods_list");
		
		// 从仓库中获取服饰
		ClothingInfo clothingInfo = warehouseDBHelper.queryByClothingID(clothingID);
		
		// 从session中的获取订购信息
		for (OrderGoods o : goodsList) {
			// 订单中有该服饰记录
			if(clothingID.equals(o.getClothingID()))
			{
				// 已拣货
				if(o.getPick_sign() == OrderGoods.PICK)
				{
					session.setAttribute("message", "商品:"+clothingID+" 已拣货<br/>请重新选择拣货商品");
					return;
				}
				
				// 库存足够 进行拣货过程
				if(clothingInfo.getNumber() >= o.getNumber()){
					// 设置界面中的数据
					o.setPick_sign(OrderGoods.PICK);
					o.setPick_time(new Date());
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = df.format(o.getPick_time());
					// 设置数据库中该商品的拣货操作
					orderGoodsDBHelper.updateForPickSign(o.getOrder_id(), o.getClothingID(), OrderGoods.PICK, time);
					// 仓库中的数量更新
					warehouseDBHelper.updateForNumber(clothingID, o.getShelves(), o.getLocation(), clothingInfo.getNumber()-o.getNumber());
					
					session.setAttribute("message", "拣货成功！");

					return;
					
				}
				// 库存不足
				else	
				{
					session.setAttribute("message", "库存不足！");
					return;
				}
			}
		}
		// 订单中不存在该商品
		session.setAttribute("message", "订单中不存在该服装！");
		return;
	}


	@Override
	public void orderResolved(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		List<OrderGoods> goodsList = (List<OrderGoods>) session.getAttribute("order_goods_list");
		String orderID  = goodsList.get(0).getOrder_id();
		
		// 判断订单是否拣货完毕
		boolean flag = true;
		
		for (OrderGoods o : goodsList) {
			if(o.getPick_sign() != OrderGoods.PICK)
				flag = false;
		}
		
		if(flag)	// 订单全部被拣货
		{
			// 数据库状态字段修改成功
			if(orderBDHelper.updateForState(orderID, Order.RESOLVED))	
			{
				session.removeAttribute("order_goods_list"); 		// 删除session中的订单信息
				session.setAttribute("message", "处理成功！");
				return;
			}
			// 数据库状态字段修改失败
			else	
			{
				session.setAttribute("message", "处理失败！");
				return;
			}
		}
		else	// 	订单有未拣货的产品
		{
			session.setAttribute("message", "还有产品未拣货！");
			return;
		}
		
	}


	@Override
	public void queryClothingByLocation(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		// 获取位置信息
		String shelves = request.getParameter("shelves");
		String location = request.getParameter("location");
 		//System.out.println("位置信息："+ shelves + "-" + location);
		
		// 盘点表格对象为空，则创建
		if(session.getAttribute("checkTable")== null)
		{
			CheckTable checkTable = new CheckTable();
			session.setAttribute("checkTable", checkTable);		// 将盘点表放到session中
		}
		
		// 从仓库中获取服饰信息
		ClothingInfo c = warehouseDBHelper.queryByLocation(shelves, location);
		
		// 将服饰对象放入session中
		if(c!=null)
		{
			session.setAttribute("clothingInfo", c);
		}
		else
		{
			session.setAttribute("message", "位置输入有误！<br/>请重新输入！<br/>例: <br/>货架:  A<br/>货位:  1 ");
		}

	}


	@Override
	public void updateClothingNumber(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		// 获取跟新的数量
		int updateNum = Integer.parseInt(request.getParameter("updateNum"));
		
		// 获取被跟新的服装
		ClothingInfo c = (ClothingInfo)session.getAttribute("clothingInfo");
		
		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		if(c!=null && checkTable!=null){
		
			// 更新数据库中的数量
			warehouseDBHelper.updateForNumber(c.getClothingID(), c.getShelves(), c.getLocation(), updateNum);
			
			// 盘盈和盘亏的值
			int surplus = 0;
			int loss = 0;
	
			if(c.getNumber() > updateNum)	// 盘亏
			{
				loss = Math.abs(c.getNumber()-updateNum);
			}
			else if(c.getNumber() < updateNum)	// 盘盈
			{
				surplus = Math.abs(c.getNumber()-updateNum);
			}
			
			// 盘点对象
			Check check = new Check(
					c.getClothingID(),
					c.getShelves()+"-"+c.getLocation(),
					c.getNumber(),
					updateNum,
					surplus,
					loss);
			
			// 盘点表格记录
			checkTable.addCheck(check);
			
			// 将服饰删除，放置重复操作
			session.removeAttribute("clothingInfo");
	
		}	
		return;
	}


	@Override
	public void deleteFormCheckTable(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取删除的行数
		String row = request.getParameter("row");
		
		// 取得删除的对象
		Check check = (Check)session.getAttribute(row);
			
		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		// 删除
		checkTable.deleteCheck(check);
		
		return;
	}


	@Override
	public boolean exportExcel(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		if(checkTable == null)
		{
			session.setAttribute("message", "已导出！");
			return false;
		}
		
		// 创建导出工具类
		ExportExcelUtil<Check> excelUtil = new ExportExcelUtil<Check>();
		
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=check.xlsx");//要保存的文件名
		response.setContentType("application/octet-stream; charset=utf-8");
		
		try {
			excelUtil.ExportExcel("盘盈和盘亏报表", checkTable.getTitle(), checkTable.getDataSet(), response.getOutputStream(), ExportExcelUtil.EXCEl_FILE_2007);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 将盘点表格对象从session中删除
		session.removeAttribute("checkTable");
		
		return true;
	}


	@Override
	public void outLogin(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		request.getSession().invalidate();		// 销毁session
	
	}
	
}
