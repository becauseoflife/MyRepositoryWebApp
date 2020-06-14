package com.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mapper.Admin;
import com.mapper.UserInfo;

public class LoginFilter implements Filter{

	private String passUrls;		// ����Ҫ���ص�url
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//��ȡ����URL
		String servletPath = req.getServletPath();
		
		// ����Ҫ����url����
		List<String> urls = Arrays.asList(passUrls.split(";"));
		for(String url : urls)
		{
			if(servletPath.contains(url)/* || servletPath.contains(".js") || servletPath.contains(".css")*/)
			{
				chain.doFilter(request, response);
				return;
			}
		}
		
		System.out.println("�����ˣ�" + req.getRequestURL());
		
		HttpSession session = req.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute("user");
		Admin admin = (Admin)session.getAttribute("admin");
		
		// ���жϻ������Ƿ����û�
		if(userInfo != null || admin != null){
			chain.doFilter(request, response);
		}else{
			session.setAttribute("message", "��ݹ���!<br/>�����µ�¼!");
			res.sendRedirect(req.getContextPath() + "/login.jsp");
		}
		
		//chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
		// ��web.xml�ļ��л�ȡ����Ҫ���ص�Urls
		this.passUrls = filterConfig.getInitParameter("PassUrls");
	}

}
