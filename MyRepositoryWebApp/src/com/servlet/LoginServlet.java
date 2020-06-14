package com.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mapper.Admin;
import com.mapper.UserInfo;
import com.service.UserAccountService;
import com.service.AdminAccountService;
import com.service.ExportService;
import com.service.impl.UserAccountServiceImpl;
import com.service.impl.AdminAccountServiceImpl;
import com.service.impl.ExportServiceImp;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	// 用户业务逻辑实现类
	private UserAccountService userAccountService = new UserAccountServiceImpl();
	private AdminAccountService adminAccountService = new AdminAccountServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		
		String role;	// 角色
		if(request.getParameter("role") != null)
		{
			role = "admin";
		}
		else
		{
			role = "user";
		}

		//System.out.println("角色：" + role);
		
		if(role.equals("admin"))
		{
			adminLogin(request, response);
		}
		if(role.equals("user"))
		{
			userLogin(request, response);
		}

	}

	private void adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName"); 	// 用户名
		String password = request.getParameter("passWord");		// 密码
		
		Admin admin = adminAccountService.findByName(userName);
		
		// 检查信息
		if(admin==null)
		{
			session.setAttribute("message", "用户不存在!");
			response.sendRedirect("login.jsp");
		}
		else if(admin!=null && !admin.getPassword().equals(password))
		{
			session.setAttribute("message", "密码错误，请重新输入!");
			response.sendRedirect("login.jsp");
		}
		else if(admin!=null && admin.getPassword().equals(password))
		{
			session.setAttribute("admin", admin);
			request.getRequestDispatcher("UserManagementServlet?action=getUser").forward(request, response);	// 登录成功
		}
	}

	private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		String userName = request.getParameter("userName"); 	// 用户名
		String password = request.getParameter("passWord");		// 密码
		
		UserInfo userInfo = null;
		try {
			userInfo = userAccountService.findByName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 验证用户登录信息
		if(userInfo != null && userInfo.getPassword().equals(password))
		{
			session.setAttribute("user", userInfo);		// 保存用户信息到session中去
			request.getRequestDispatcher("/WEB-INF/view/user/search_good.jsp").forward(request, response);	// 登录成功
		}
		else if(userInfo == null)
		{
			session.setAttribute("message", "用户不存在，请先注册!");
			response.sendRedirect("login.jsp");
		}
		else if(userInfo != null && !userInfo.getPassword().equals(password))
		{
			session.setAttribute("message", "密码错误，请重新输入!");
			response.sendRedirect("login.jsp");
		}
	}

}
