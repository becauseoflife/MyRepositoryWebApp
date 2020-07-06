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
 * 1.两者都是为了初始化用，但是不推荐在构造函数中对servlet做初始化工作。
 * 2.servlet实例的生命周期是由容器控制的。构造函数中的初始化工作只会在容器构造这个servlet时做一次。
 * 3.servlet的实例是会被多个请求复用，但是构造函数却只能提供一次初始化，所以必须将初始化工作放入init中，由容器来控制。
 * 在filter下新建一个EncodingFilter用来解决中文字符集乱码，它需要实现Filter接口，并重写doFilter函数	
 */

public class EncodingFilter implements Filter{

	private String passUrls;		// 不需要拦截的url
	
	
	public EncodingFilter() {
		//System.out.println("过滤器构造！");
	}
	
	@Override
	public void destroy() {
		//System.out.println("过滤器销毁！");
	}

	
	/**
	 *	过滤器拦截到响应url的请求后会先执行doFilter()方法中chain.doFilter()之前的代码，
	 *	然后执行下一个过滤器或者servelt。紧接着执行chain.doFilter()之后的代码
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//获取请求URL
		String servletPath = req.getServletPath();
		
		// 不需要检测的url集合
		List<String> urls = Arrays.asList(passUrls.split(";"));
		for(String url : urls)
		{
			if(servletPath.contains(url)/* || servletPath.contains(".js") || servletPath.contains(".css")*/)
			{
				chain.doFilter(request, response);
				return;
			}
		}
		//System.out.println("编码过滤器拦截了：" + req.getRequestURL());
		
		//将编码改为utf-8
		request.setCharacterEncoding("utf-8"); 
		response.setContentType("text/html;charset=utf-8");
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//System.out.println("过滤器初始化！");
		
		// 从web.xml文件中获取不需要拦截的Urls
		this.passUrls = arg0.getInitParameter("PassUrls");
	}
	
	
}
