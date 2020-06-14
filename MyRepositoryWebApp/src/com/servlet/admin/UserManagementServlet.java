package com.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mapper.UserInfo;
import com.service.UserAccountService;
import com.service.impl.UserAccountServiceImpl;

/**
 * Servlet implementation class UserManagementServlet
 */
@WebServlet("/UserManagementServlet")
public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String action;		// 界面的动作
	private UserAccountService userAccountService = new UserAccountServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserManagementServlet() {
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
			System.out.println(action);
			if(action.equals("getUser"))	// 获得用户列表
			{
				// 获得全部用户
				List<UserInfo> userList = userAccountService.findAll();
				
				// 保存到session中 
				request.getSession().setAttribute("userList", userList);
				
/*				String jsonUserList = JSON.toJSONString(userList);
				response.getWriter().write(jsonUserList);*/
				
				// 转发
				request.getRequestDispatcher("/WEB-INF/view/admin/user_management.jsp").forward(request, response);
			}
			if(action.equals("addUser"))
			{
				AddUser(request, response);
			}
			if(action.equals("updateUser"))
			{
				UpdateUser(request, response);
			}
			if (action.equals("deleteUser")) {
				DeleteUser(request, response);
			}
		}
	}

	private void DeleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		
		if(userAccountService.deleteByName(username))
		{
			// 获得全部用户
			List<UserInfo> userList = userAccountService.findAll();
			// 保存到session中 
			request.getSession().setAttribute("userList", userList);
			request.getSession().setAttribute("message", "删除成功!");
		}
		else
		{
			request.getSession().setAttribute("message", "删除失败!");
		}
		request.getRequestDispatcher("/WEB-INF/view/admin/user_management.jsp").forward(request, response);
	}

	private void UpdateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String oldUserName = request.getParameter("username");	// 修改的用户名
		
		String userName = request.getParameter("updateUsername"); 	// 用户名
		String password = request.getParameter("updatePassword");		// 密码
		String telephone = request.getParameter("updateTelephone");	// 联系电话
		String email = request.getParameter("updateEmail");			// 电子邮件
		
		// 更新用户
		UserInfo userInfo = UserInfo.builder()
				.userName(userName)
				.password(password)
				.telephone(telephone)
				.email(email)
				.regDate(new Date())
				.build();
		
		if(userAccountService.updateByName(oldUserName, userInfo))
		{
			// 获得全部用户
			List<UserInfo> userList = userAccountService.findAll();
			// 保存到session中 
			request.getSession().setAttribute("userList", userList);
			request.getSession().setAttribute("message", "更新成功!");
		}
		else
		{
			request.getSession().setAttribute("message", "更新失败!");
		}
		request.getRequestDispatcher("/WEB-INF/view/admin/user_management.jsp").forward(request, response);
		
	}

	private void AddUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("registerUsername"); 	// 用户名
		String password = request.getParameter("registerPassword");		// 密码
		String telephone = request.getParameter("registerTelephone");	// 联系电话
		String email = request.getParameter("registerEmail");			// 电子邮件
		
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
	
		// 新建用户
		UserInfo userInfo = UserInfo.builder()
				.userName(userName)
				.password(password)
				.telephone(telephone)
				.email(email)
				.regDate(new Date())
				.build();
/*		userInfo.setUserName(userName);
		userInfo.setPassword(password);
		userInfo.setTelephone(telephone);
		userInfo.setEmail(email);
		userInfo.setRegDate(new Date());*/
		
		try {
			if(userAccountService.add(userInfo))
			{
				// 获得全部用户
				List<UserInfo> userList = userAccountService.findAll();
				// 保存到session中 
				request.getSession().setAttribute("userList", userList);
				
				request.getSession().setAttribute("message", "新增成功!");	
			}
			else
			{
				request.getSession().setAttribute("message", "新增失败！<br/>"+userInfo.getUserName() +"用户名已存在！");
			}
			request.getRequestDispatcher("/WEB-INF/view/admin/user_management.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
