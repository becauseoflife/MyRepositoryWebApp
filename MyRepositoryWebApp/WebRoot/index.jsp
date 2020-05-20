<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<!--
		这是设置基础路径的,basepath为变量
		简单的静态网页的话你设置比如:
		<base href="http://www.baidu.com" mce_href="http://www.baidu.com">,
		那你下面的href属性就会以你上面设的为基准,
		如:<a href="http://www.baidu.com/xxx.htm"></a>
		你现在就只需要写<a href="xxx.htm"></a>
	-->
    
    <title>My JSP 'index.jsp' starting page</title>
    
    <!-- 禁止浏览器从本地计算机的缓存中访问页面内容,这样设定，访问者将无法脱机浏览 -->
	<meta http-equiv="pragma" content="no-cache">
	<!-- 用于设定网页的到期时间，过期则必须到服务器上重新调用 -->
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<!-- keywords用来告诉搜索引擎你网页的关键字是什么   -->
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<!-- description用来告诉搜索引擎你的网站主要内容 -->
	<meta http-equiv="description" content="This is my page">
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
  </head>
  
  <body>
    	这是一个Jsp界面. <br>
  </body>
</html>
