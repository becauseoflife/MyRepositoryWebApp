<%@page import="com.mapper.UserInfo"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>

<!--  用户界面的工具栏 -->

<nav class="sb-topnav navbar navbar-expand navbar-dark bg-darks">
    <a class="navbar-brand" href="user/SearchUIServlet">仓库管理</a>
    <button class="btn btn-link btn-sm order-1 order-lg-0" id="sidebarToggle" href="#"><i class="fas fa-bars"></i></button>
    <!-- Navbar Search-->
    <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
        <div class="input-group">
            <input class="form-control" type="text" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2" />
            <div class="input-group-append">
                <button class="btn btn-primary" type="button"><i class="fas fa-search"></i></button>
            </div>
        </div>
    </form>
    <!-- Navbar-->
    <ul class="navbar-nav ml-auto ml-md-0">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="userDropdown" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                <a class="dropdown-item" href="">个人信息</a>
                <a class="dropdown-item" href="">修改信息</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="OutLoginServlet">退出登录</a>
            </div>
        </li>
    </ul>
</nav>
<div id="layoutSidenav">
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <div class="sb-sidenav-menu-heading">功能</div>
                    	<a id="my-nav-link" class="nav-link" href="user/SearchUIServlet">
                    		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                        	<text>库存查询</text>
                    	</a>
						<a id="my-nav-link" class="nav-link" href="user/PickGoodsServlet?action=update">
                    		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                        	<text>拣货任务</text>
                    	</a>
	                     <a id="my-nav-link"  class="nav-link" href="user/CheckUIServlet">
	                     	<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
	                        	<text>库存盘点</text>
	                     </a>
                    <div class="sb-sidenav-menu-heading">其他</div>
                    	<a id="my-nav-link" class="nav-link" href="user/PutOnGoodServlet?action=getEmptyPositon">
                    		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                        	<text>货品入库</text>
                    	</a>
                    	<a id="my-nav-link" class="nav-link" href="user/InportUIServlet">
                    		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                        	<text>订单导入</text>
                    	</a>
                    	<a id="my-nav-link" class="nav-link" href="user/ReturnHandleServlet">
                    		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                        	<text>退货订单</text>
                    	</a>
                </div>
            </div>
            <div class="sb-sidenav-footer">
                <div class="small">用户:</div>
                <%
                	UserInfo user = (UserInfo)session.getAttribute("user");
                %>
               <text><%=user.getUserName() %></text>
           </div>
       </nav>
   </div>
