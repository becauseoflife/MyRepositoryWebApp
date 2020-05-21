package com.dao.impl;

import java.sql.SQLException;

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
			return true;
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
	


}
