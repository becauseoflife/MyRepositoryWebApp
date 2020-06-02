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
 * Servlet implementation class CheckServet
 */
@WebServlet("/CheckServlet")
public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String action;		// 盘点界面的动作
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		UserService service = new UserServiceImp();
		
		if(request.getParameter("action") != null)
		{
			this.action = request.getParameter("action");
			if(action.equals("query"))
			{
				service.queryClothingByLocation(request, response);
				response.sendRedirect("pages/check.jsp");
			}
			if (action.equals("update")) 
			{
				service.updateClothingNumber(request, response);
				response.sendRedirect("pages/check.jsp");
			}
			if(action.equals("delete"))
			{
				service.deleteFormCheckTable(request, response);
				response.sendRedirect("pages/check.jsp");
			}
			if(action.equals("exportExcel"))
			{
				if(!service.exportExcel(request, response))
					response.sendRedirect("pages/check.jsp");
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
