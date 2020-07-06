package com.filter;

import java.io.IOException;
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

/**
 * 1.���߶���Ϊ�˳�ʼ���ã����ǲ��Ƽ��ڹ��캯���ж�servlet����ʼ��������
 * 2.servletʵ�����������������������Ƶġ����캯���еĳ�ʼ������ֻ���������������servletʱ��һ�Ρ�
 * 3.servlet��ʵ���ǻᱻ��������ã����ǹ��캯��ȴֻ���ṩһ�γ�ʼ�������Ա��뽫��ʼ����������init�У������������ơ�
 * ��filter���½�һ��EncodingFilter������������ַ������룬����Ҫʵ��Filter�ӿڣ�����дdoFilter����	
 */

public class EncodingFilter implements Filter{

	private String passUrls;		// ����Ҫ���ص�url
	
	
	public EncodingFilter() {
		//System.out.println("���������죡");
	}
	
	@Override
	public void destroy() {
		//System.out.println("���������٣�");
	}

	
	/**
	 *	���������ص���Ӧurl����������ִ��doFilter()������chain.doFilter()֮ǰ�Ĵ��룬
	 *	Ȼ��ִ����һ������������servelt��������ִ��chain.doFilter()֮��Ĵ���
	 */
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
		//System.out.println("��������������ˣ�" + req.getRequestURL());
		
		//�������Ϊutf-8
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8");
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//System.out.println("��������ʼ����");
		
		// ��web.xml�ļ��л�ȡ����Ҫ���ص�Urls
		this.passUrls = arg0.getInitParameter("PassUrls");
	}
	
	
}
