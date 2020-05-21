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

/* 实现用户业务逻辑类 */
public class UserDaoImpl implements UserDao {
	
	// 获取用户数据库操作类
	private UserDBHelperDao userDBHelper = UserDBHelperFactory.getUserDBHelperDao();

	@Override
	public boolean login(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("userName"); 	// 用户名
		String password = request.getParameter("passWord");		// 密码
		
		User user = null;
		try {
			user = userDBHelper.querryByUserName(userName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		// 验证用户登录信息
		HttpSession session = request.getSession();
		if(user != null && user.getPassword().equals(password))
		{
			return true;		// 登录成功
		}
		else if(user == null)
		{
			session.setAttribute("message", "用户不存在，请先注册!");
			return false;
		}
		else if(user != null && !user.getPassword().equals(password))
		{
			session.setAttribute("massage", "密码错误，请重新输入!");
			return false;
		}
		
		return false;
	}

	
	@Override
	public boolean register(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		String userName = request.getParameter("registerUsername"); 	// 用户名
		String password = request.getParameter("registerPassword");		// 密码
		String telephone = request.getParameter("registerTelephone");	// 联系电话
		String email = request.getParameter("registerEmail");			// 电子邮件
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
	
		// 新建用户
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setTelephone(telephone);
		user.setEmail(email);
		user.setRegDate(new Date());
		
		try {
			HttpSession session = request.getSession();
			if (userDBHelper.insert(user)) {
				
				session.setAttribute("message", "注册成功！");
				return true;
			}else{
				session.setAttribute("message", "注册失败！");
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	


}
