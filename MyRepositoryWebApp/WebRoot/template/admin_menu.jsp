<%@page import="com.mapper.Admin"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>

    <nav class="sb-topnav navbar navbar-expand navbar-dark bg-darks">
        <a class="navbar-brand" href="admin/UserManagementServlet">仓库管理</a>
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
                    <a class="dropdown-item" href="admin/AdminOutLoginServlet">退出登录</a>
                </div>
            </li>
        </ul>
    </nav>
    <div id="layoutSidenav">
        <div id="layoutSidenav_nav">
            <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                <div class="sb-sidenav-menu">
                    <div class="nav">
                        <div class="sb-sidenav-menu-heading">账户</div>
                        	<a id="my-nav-link" class="nav-link" href="admin/UserManagementServlet?action=getUser">
                        		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            	<text>用户管理</text>
                        	</a>
                        <div class="sb-sidenav-menu-heading">订单</div>
                        	<a id="my-nav-link" class="nav-link" href="admin/OrderUIServlet">
                        		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            	<text>创建订单</text>
                        	</a>
                        	<a id="my-nav-link" class="nav-link" href="admin/OrderHandleServlet?action=InitInfo">
                        		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            	<text>拣货分配</text>
                        	</a>
                        	<a id="my-nav-link" class="nav-link" href="admin/OrderSearchServlet">
                        		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            	<text>订单处理</text>
                        	</a>     
                        	<a id="my-nav-link" class="nav-link" href="admin/ReturnOrderServlet">
                        		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            	<text>退货订单</text>
                        	</a>
                        	<a id="my-nav-link" class="nav-link" href="admin/CheckTaskServlet">
                        		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            	<text>盘点任务</text>
                        	</a>                          	
                    </div>
                </div>
                <div class="sb-sidenav-footer">
                    <div class="small">用户:</div>
                    <%
                    	Admin admin = (Admin)session.getAttribute("admin");
                    %>
                    <text><%=admin.getUsername() %></text>
                </div>
            </nav>
        </div>
