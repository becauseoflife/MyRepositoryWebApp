<%@page import="com.entity.Order"%>
<%@page import="com.mapper.OrderItemInfo"%>
<%@page import="com.mapper.OrderInfo"%>
<%@page import="com.mapper.Admin"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.entity.ResponseResult"%>
<%@page import="com.mapper.UserInfo"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>订单退货</title>
    
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    
    <link href="css/styles.home.css" rel="stylesheet" />
    <link rel="stylesheet" href="css/styles.login.register.css" id="theme-stylesheet">
   	<!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.0-dist/css/bootstrap.min.css">
    
    <script src="js/all.min.js" crossorigin="anonymous"></script>

    
  </head>
  
  <body class="sb-nav-fixed" onload="getAllOrderId();">
		<!-- 菜单列表 -->
		<jsp:include page="/template/admin_menu.jsp"></jsp:include>

        <div id="layoutSidenav_content">
			<main>

				<div class="container-fluid">
                       <h1 class="mt-4">退货订单</h1>
                       <ol class="breadcrumb mb-4">
                           <li class="breadcrumb-item"><a href="admin/UserManagementServlet?action=getUser">仓库管理</a></li>
                           <li class="breadcrumb-item active">退货订单</li>
                       </ol>

					<div class="">
						<div class="card text-center">
							<div class="card-header">
								全部未退订单
							</div>
						  	<div class="card-body">
						  		<form method="post" action="admin/ReturnOrderServlet?action=getOrder" onsubmit="return checkSelectSubmit();"> 
						  		
									<select name="orderId" id="orderSelect" class="custom-select">
									</select>
									<div style="height: 3vh;"></div>
									<button id="lookBtn" type="submit" class="btn btn-primary myAdd-btn">查看</button>
									
								</form>
							</div>
						</div>
					</div>

			<%
				if(session.getAttribute("return_order") != null)
				{
					Order order = (Order)session.getAttribute("return_order");
					String orderID = order.getOrderInfo().getOrder_id();
			%>

					<div class="">
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
										    <th scope="col">数量</th>
										    <th scope="col">拣货员</th>
										    <th scope="col">标记</th>
										    <th scope="col">拣货时间</th>
									    </tr>
								  	</thead>
								  	<tbody>
								  	<!-- 循环开始 -->
								  	<%
								  		int row=0;
	  							  		String pickSign=""; 
	  							  		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
								  	%>
									    	<tr>
									      		<th scope="row"><%=row %></th>
									      		<td><%=g.getClothingID() %></td>
									      		<td><%=g.getNumber() %></td>
									      		<td><%=g.getPick_user() %></td>
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
								<form method="post" action="admin/ReturnOrderServlet?action=return_order">
									<button type="submit" class="btn btn-primary">订单退货</button>
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
    
    <script type="text/javascript" src="js/view/admin/return_order.js"></script>
    
    <!-- Modal -->
	<!--消息提示框 -->
    <jsp:include page="/modal/modal_message.jsp"></jsp:include>
	
  </body>
</html>
