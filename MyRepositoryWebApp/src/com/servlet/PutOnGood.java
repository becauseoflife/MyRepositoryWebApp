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
 * Servlet implementation class PutOnGood
 */
@WebServlet("/PutOnGood")
public class PutOnGood extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String action;	// 请求的动作
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PutOnGood() {
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
			if(action.equals("getEmptyPositon"))
			{
				service.getEmptyPositon(request, response);
			}
			if(action.equals("putOn"))
			{
				service.putOnGood(request, response);
				response.sendRedirect("pages/putOnGood.jsp");
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
