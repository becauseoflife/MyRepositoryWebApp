<%@page import="com.entity.Order"%>
<%@page import="com.mapper.UserInfo"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.mapper.OrderItemInfo"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
	String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>创建订单</title>
    
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    
    <link href="css/styles.home.css" rel="stylesheet" />
    <link href="css/myStyles.home.css" rel="stylesheet" />
   	<!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.0-dist/css/bootstrap.css">
    
    <script src="js/all.min.js" crossorigin="anonymous"></script>

  </head>
  
  <body class="sb-nav-fixed" onload="getOrderId();">
    <nav class="sb-topnav navbar navbar-expand navbar-dark bg-darks">
        <a class="navbar-brand" href="SearchUIServlet">仓库管理</a>
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
                        	<a id="my-nav-link" class="nav-link" href="SearchUIServlet">
                        		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            	<text>库存查询</text>
                        	</a>
                            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                            	<div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                	订单拣货
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                	<a class="nav-link" href="OrderUIServlet">创建订单</a>
                                	<a class="nav-link" href="PickUIServlet">拣货操作</a>
                                </nav>
                            </div>
	                        <a id="my-nav-link"  class="nav-link" href="CheckUIServlet">
	                        	<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
	                           	<text>库存盘点</text>
	                        </a>
                        <div class="sb-sidenav-menu-heading">其他</div>
                        	<a id="my-nav-link" class="nav-link" href="PutOnUIServlet">
                        		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            	<text>货品上架</text>
                        	</a>

                    </div>
                </div>
                <div class="sb-sidenav-footer">
                    <div class="small">用户:</div>
                    <%
                    	UserInfo user = (UserInfo)session.getAttribute("user");
                    %>
                    <text>&emsp;<%=user.getUserName()%></text>
                </div>
            </nav>
        </div>

        <div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
					<h1 class="mt-4">订单拣货</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="index.html">库存查询</a></li>
						<li class="breadcrumb-item active">订单拣货</li>
					</ol>


					<div class="card mb-4">
						<div class="card text-center">
							<div class="card-header">
								待处理订单
							</div>
						  	<div class="card-body">
						  		<form method="post" action="PickGoodsServlet?action=getOrder" onsubmit="return checkSelectSubmit();"> 
						  		
									<select name="select_orderId" id="orderSelect" class="custom-select">
									</select>
									<div style="height: 3vh;"></div>
									<button id="lookBtn" type="submit" class="btn btn-primary myAdd-btn">查看</button>
									
								</form>
							</div>
						</div>
					</div>

			<%
				if(session.getAttribute("order") != null)
				{
					Order order = (Order)session.getAttribute("order");
					String orderID = order.getOrderInfo().getOrder_id();
			%>

					<div class="card mb-4">
						<div class="card text-center">
							<div class="card-header">
								订单详情
							</div>
						  	<div class="card-body">
							
							<table class="table table-bordered text-center">
								  	<thead>
									    <tr>
									    	<th class="table-primary"></th>
										    <th class="table-primary" scope="col" colspan="1">订单ID</th>
										    <th scope="col" colspan="4"><%=orderID%></th>
									    </tr>
									    <tr  class="table-primary">
										    <th scope="col">#</th>
										    <th scope="col">订购产品</th>
										    <th scope="col">位置</th>
										    <th scope="col">数量</th>
										    <th scope="col">标记</th>
										    <th scope="col">拣货时间</th>
									    </tr>
								  	</thead>
								  	<tbody>
								  	<!-- 循环开始 -->
								  	<%
								  		int row=0;
	  							  		String pickSign=""; 
	  							  		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  							  		df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	  							  		for(OrderItemInfo g : order.getGoodsList())
	  							  		{
		  							  		row++;
		  							  		if(g.getPick_sign() == OrderItemInfo.NOT_PICK)
		  							  		{
		  							  			pickSign = "未拣货";
		  							  		}
		  							  		else if(g.getPick_sign() == OrderItemInfo.PICK)
		  							  		{
		  							  			pickSign = "已拣货";
		  							  		}
		  							  		else if(g.getPick_sign() == OrderItemInfo.PICK_FAILED)
		  							  		{
		  							  			pickSign = "拣货失败";
		  							  		}
								  	%>
									    	<tr>
									      		<th scope="row"><%=row %></th>
									      		<td><%=g.getClothingID() %></td>
									      		<td><%=g.getShelves() %>-<%=g.getLocation() %></td>
									      		<td><%=g.getNumber() %></td>
									      		<td><%=pickSign %></td>
									      	<%
									      		if(g.getPick_time() != null)
									      		{
									      	 %>
									      			<td><%=df.format(g.getPick_time()) %></td>
									      	<%
									      		}
									      		else
									      		{
									      	 %>
									      	 		<td> - - </td>
									      	 <%
									      	 	}
									      	  %>	
								    		</tr>
									<!-- 循环结束 -->
									<%
										}
									 %>
								  	</tbody>	
								</table>
								<form method="post" action="PickGoodsServlet?action=orderResolved">
									<button type="submit" class="btn btn-primary">处理完成</button>
								</form>
						  	</div>
						</div>
					</div>

					<div class="card mb-4">
						<div class="card text-center">
						  	<div class="card-header">
						    	输入产品ID或者扫描产品标签
						  	</div>
						  	<div class="card-body">
								<form method="post" action="PickGoodsServlet?action=pickGood" onsubmit="return pickSubmit();">
									<div class="input-group mb-3">
									  	<label for="staticEmail" class="col-sm-1 col-form-label">服装ID</label>
										<input id="pickIdInput" type="text" name="clothingId" class="form-control" placeholder="请输入服装ID" aria-label="" aria-describedby="button-addon2">
									</div>
	
									<button id="pickBtn" type="submit" class="btn btn-primary myAdd-btn">拣货</button>
								</form>
						  	</div>
						</div>
					</div>
			<%
			}
			 %>
				</div>
			</main>
            <footer class="py-4 bg-light mt-auto">
                <div class="container-fluid">
                    <div class="d-flex align-items-center justify-content-between small">
                        <div class="text-muted">Copyright &copy; PanPan</div>
                        <div>
                            <a href="#">Privacy Policy</a>
                            &middot;
                            <a href="#">Terms &amp; Conditions</a>
                        </div>
                    </div>
                </div>
            </footer>
        </div>
    </div>
    
    <!-- JQuery -->
    <script type="text/javascript" src="resources/jquery-3.4.1/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="resources/bootstrap-4.5.0-dist/js/bootstrap.min.js"></script>
    
    <script src="resources/bootstrap-4.5.0-dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>
    
    <script type="text/javascript" src="js/view/user/pick_good.js"></script>
    
    <!-- Modal -->
	<!--消息提示框 -->
    <jsp:include page="/modal/modal_message.jsp"></jsp:include>
  </body>
</html>
