package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.UserService;
import com.service.impl.UserServiceImp;

/**
 * Servlet implementation class PickGoodsServlet
 */
@WebServlet("/PickGoodsServlet")
public class PickGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String action;		// 拣货界面的动作
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PickGoodsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("action:" + request.getParameter("action"));
		
		UserService service = new UserServiceImp();
		
		if(request.getParameter("action") != null)
		{
			this.action = request.getParameter("action");
			if(action.equals("getSelectOption"))
			{
				service.getOrderSelectOptions(request, response);
			}
			if(action.equals("getOrderInfo"))
			{
				service.getOrderInfo(request, response);
			}
			if(action.equals("pickGood"))
			{
				service.pickGood(request, response);
			}
			if (action.equals("orderResolved"))
			{
				service.orderResolved(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
