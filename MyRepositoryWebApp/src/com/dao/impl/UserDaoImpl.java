package com.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dao.UserDBHelperDao;
import com.dao.UserDao;
import com.entity.User;
import com.simplefactory.UserDBHelperFactory;

/* ʵ���û�ҵ���߼��� */
public class UserDaoImpl implements UserDao {
	
	// ��ȡ�û����ݿ������
	private UserDBHelperDao userDBHelper = UserDBHelperFactory.getUserDBHelperDao();

	@Override
	public boolean login(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("userName"); 	// �û���
		String password = request.getParameter("passWord");		// ����
		
		User user = null;
		try {
			user = userDBHelper.querryByUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		// ��֤�û���¼��Ϣ
		HttpSession session = request.getSession();
		if(user != null && user.getPassword().equals(password))
		{
			return true;		// ��¼�ɹ�
		}
		else if(user == null)
		{
			session.setAttribute("message", "�û������ڣ�����ע��!");
			return false;
		}
		else if(user != null && !user.getPassword().equals(password))
		{
			session.setAttribute("massage", "�����������������!");
			return false;
		}
		
		return false;
	}

	
	@Override
	public boolean register(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("registerUsername"); 	// �û���
		String password = request.getParameter("registerPassword");		// ����
		String telephone = request.getParameter("registerTelephone");	// ��ϵ�绰
		String email = request.getParameter("registerEmail");			// �����ʼ�
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	
		// �½��û�
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setTelephone(telephone);
		user.setEmail(email);
		user.setRegDate(new Date());
		
		try {
			HttpSession session = request.getSession();
			if (userDBHelper.insert(user)) {
				
				session.setAttribute("message", "ע��ɹ���");
				return true;
			}else{
				session.setAttribute("message", "ע��ʧ�ܣ�");
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	


}
