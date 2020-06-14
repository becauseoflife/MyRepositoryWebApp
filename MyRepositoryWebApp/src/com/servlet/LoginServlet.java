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
       
	// �û�ҵ���߼�ʵ����
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
		
		String role;	// ��ɫ
		if(request.getParameter("role") != null)
		{
			role = "admin";
		}
		else
		{
			role = "user";
		}

		//System.out.println("��ɫ��" + role);
		
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
		String userName = request.getParameter("userName"); 	// �û���
		String password = request.getParameter("passWord");		// ����
		
		Admin admin = adminAccountService.findByName(userName);
		
		// �����Ϣ
		if(admin==null)
		{
			session.setAttribute("message", "�û�������!");
			response.sendRedirect("login.jsp");
		}
		else if(admin!=null && !admin.getPassword().equals(password))
		{
			session.setAttribute("message", "�����������������!");
			response.sendRedirect("login.jsp");
		}
		else if(admin!=null && admin.getPassword().equals(password))
		{
			session.setAttribute("admin", admin);
			request.getRequestDispatcher("UserManagementServlet?action=getUser").forward(request, response);	// ��¼�ɹ�
		}
	}

	private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		String userName = request.getParameter("userName"); 	// �û���
		String password = request.getParameter("passWord");		// ����
		
		UserInfo userInfo = null;
		try {
			userInfo = userAccountService.findByName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��֤�û���¼��Ϣ
		if(userInfo != null && userInfo.getPassword().equals(password))
		{
			session.setAttribute("user", userInfo);		// �����û���Ϣ��session��ȥ
			request.getRequestDispatcher("/WEB-INF/view/user/search_good.jsp").forward(request, response);	// ��¼�ɹ�
		}
		else if(userInfo == null)
		{
			session.setAttribute("message", "�û������ڣ�����ע��!");
			response.sendRedirect("login.jsp");
		}
		else if(userInfo != null && !userInfo.getPassword().equals(password))
		{
			session.setAttribute("message", "�����������������!");
			response.sendRedirect("login.jsp");
		}
	}

}
