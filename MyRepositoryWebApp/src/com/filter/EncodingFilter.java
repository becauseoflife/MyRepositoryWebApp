package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 1.���߶���Ϊ�˳�ʼ���ã����ǲ��Ƽ��ڹ��캯���ж�servlet����ʼ��������
 * 2.servletʵ�����������������������Ƶġ����캯���еĳ�ʼ������ֻ���������������servletʱ��һ�Ρ�
 * 3.servlet��ʵ���ǻᱻ��������ã����ǹ��캯��ȴֻ���ṩһ�γ�ʼ�������Ա��뽫��ʼ����������init�У������������ơ�
 * ��filter���½�һ��EncodingFilter������������ַ������룬����Ҫʵ��Filter�ӿڣ�����дdoFilter����	
 */

public class EncodingFilter implements Filter{

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
		request.setCharacterEncoding("utf-8"); //�������Ϊutf-8
		response.setContentType("text/html;charset=utf-8");
		// System.out.println("���ز���");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//System.out.println("��������ʼ����");
	}
	
	
}
