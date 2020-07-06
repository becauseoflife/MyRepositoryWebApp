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
    
    <title>拣货任务</title>
    
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    
    <link href="css/styles.home.css" rel="stylesheet" />
    <link href="css/myStyles.home.css" rel="stylesheet" />
   	<!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.0-dist/css/bootstrap.css">
    
    <script src="js/all.min.js" crossorigin="anonymous"></script>

  </head>
  
  <body class="sb-nav-fixed" onload="">
		<!-- 引入工具栏 -->
		<jsp:include page="/template/user_menu.jsp"></jsp:include>

        <div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
					<h1 class="mt-4">拣货任务</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="user/SearchUIServlet">仓库管理</a></li>
						<li class="breadcrumb-item active">拣货任务</li>
					</ol>

			<%
				if(session.getAttribute("myPickGoodList") != null)
				{
					List<OrderItemInfo> myPickGoodList = (List<OrderItemInfo>) session.getAttribute("myPickGoodList");
			%>

					<div class="card mb-4">
						<div class="card text-center">
							<div class="card-header">
								待拣货
							</div>
						  	<div class="card-body">
							
							<table class="table table-bordered text-center">
								  	<thead>
									    <tr  class="table-primary">
										    <th scope="col">#</th>
										    <th scope="col">订单ID</th>
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
	  							  		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  							  		df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	  							  		for(OrderItemInfo g : myPickGoodList)
	  							  		{
		  							  		row++;
								  	%>
									    	<tr>
									      		<th scope="row"><%=row %></th>
									      		<td><%=g.getOrder_id() %></td>
									      		<td><%=g.getClothingID() %></td>
									      		<td><a href="user/PickGoodsServlet?action=search&ClothingID=<%=g.getClothingID() %>">查看</a></td>
									      		<td><%=g.getNumber() %></td>
									      		<td>未拣货</td>
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
						  	</div>
						</div>
					</div>

					<div class="card mb-4">
						<div class="card text-center">
						  	<div class="card-header">
						    	输入产品ID或者扫描产品标签
						  	</div>
						  	<div class="card-body">
								<form method="post" action="user/PickGoodsServlet?action=pickGood" onsubmit="return pickSubmit();">
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
    
    <script type="text/javascript" src="js/view/admin/order.js"></script>
    
    <!-- Modal -->
	<!--消息提示框 -->
    <jsp:include page="/modal/modal_message.jsp"></jsp:include>
  </body>
</html>
