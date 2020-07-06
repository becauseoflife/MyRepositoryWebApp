package com.servlet.user;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mapper.UserInfo;
import com.service.UserAccountService;
import com.service.ExportService;
import com.service.impl.UserAccountServiceImpl;
import com.service.impl.ExportServiceImp;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/user/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// �û�ҵ���߼�ʵ����
		UserAccountService userAccountService = new UserAccountServiceImpl();

		String userName = request.getParameter("registerUsername"); // �û���
		String password = request.getParameter("registerPassword"); // ����
		String telephone = request.getParameter("registerTelephone"); // ��ϵ�绰
		String email = request.getParameter("registerEmail"); // �����ʼ�

		// SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");//�������ڸ�ʽ
		// System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��

		// �½��û�
		UserInfo userInfo = UserInfo.builder().userName(userName).password(password).telephone(telephone).email(email)
				.regDate(new Date()).build();
		/*
		 * userInfo.setUserName(userName); userInfo.setPassword(password);
		 * userInfo.setTelephone(telephone); userInfo.setEmail(email);
		 * userInfo.setRegDate(new Date());
		 */

		try {
			if (userAccountService.add(userInfo)) {
				request.getSession().setAttribute("message", "ע��ɹ���");
				response.sendRedirect("login.jsp");
			} else {
				request.getSession().setAttribute("message", "ע��ʧ�ܣ�<br/>" + userInfo.getUserName() + "�û����Ѵ��ڣ�");
				response.sendRedirect("register.jsp");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
