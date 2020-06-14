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
       
	private String action;		// ����Ķ���
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
			if(action.equals("getUser"))	// ����û��б�
			{
				// ���ȫ���û�
				List<UserInfo> userList = userAccountService.findAll();
				
				// ���浽session�� 
				request.getSession().setAttribute("userList", userList);
				
/*				String jsonUserList = JSON.toJSONString(userList);
				response.getWriter().write(jsonUserList);*/
				
				// ת��
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
			// ���ȫ���û�
			List<UserInfo> userList = userAccountService.findAll();
			// ���浽session�� 
			request.getSession().setAttribute("userList", userList);
			request.getSession().setAttribute("message", "ɾ���ɹ�!");
		}
		else
		{
			request.getSession().setAttribute("message", "ɾ��ʧ��!");
		}
		request.getRequestDispatcher("/WEB-INF/view/admin/user_management.jsp").forward(request, response);
	}

	private void UpdateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String oldUserName = request.getParameter("username");	// �޸ĵ��û���
		
		String userName = request.getParameter("updateUsername"); 	// �û���
		String password = request.getParameter("updatePassword");		// ����
		String telephone = request.getParameter("updateTelephone");	// ��ϵ�绰
		String email = request.getParameter("updateEmail");			// �����ʼ�
		
		// �����û�
		UserInfo userInfo = UserInfo.builder()
				.userName(userName)
				.password(password)
				.telephone(telephone)
				.email(email)
				.regDate(new Date())
				.build();
		
		if(userAccountService.updateByName(oldUserName, userInfo))
		{
			// ���ȫ���û�
			List<UserInfo> userList = userAccountService.findAll();
			// ���浽session�� 
			request.getSession().setAttribute("userList", userList);
			request.getSession().setAttribute("message", "���³ɹ�!");
		}
		else
		{
			request.getSession().setAttribute("message", "����ʧ��!");
		}
		request.getRequestDispatcher("/WEB-INF/view/admin/user_management.jsp").forward(request, response);
		
	}

	private void AddUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("registerUsername"); 	// �û���
		String password = request.getParameter("registerPassword");		// ����
		String telephone = request.getParameter("registerTelephone");	// ��ϵ�绰
		String email = request.getParameter("registerEmail");			// �����ʼ�
		
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		//System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	
		// �½��û�
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
				// ���ȫ���û�
				List<UserInfo> userList = userAccountService.findAll();
				// ���浽session�� 
				request.getSession().setAttribute("userList", userList);
				
				request.getSession().setAttribute("message", "�����ɹ�!");	
			}
			else
			{
				request.getSession().setAttribute("message", "����ʧ�ܣ�<br/>"+userInfo.getUserName() +"�û����Ѵ��ڣ�");
			}
			request.getRequestDispatcher("/WEB-INF/view/admin/user_management.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
