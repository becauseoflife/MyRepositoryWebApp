<%@page import="com.mapper.ClothingInfo"%>
<%@page import="com.entity.Cart"%>
<%@page import="java.lang.*"%>
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
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.0-dist/css/bootstrap.min.css">
    
    <script src="js/all.min.js" crossorigin="anonymous"></script>

  </head>
  
  <body class="sb-nav-fixed">
 		<!-- 引入工具栏 -->
		<jsp:include page="/template/admin_menu.jsp"></jsp:include>

        <div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
                       <h1 class="mt-4">创建订单</h1>
                       <ol class="breadcrumb mb-4">
                           <li class="breadcrumb-item"><a href="admin/UserManagementServlet?action=getUser">仓库管理</a></li>
                           <li class="breadcrumb-item active">创建订单</li>
                       </ol>

					<div class="card mb-4">
						<div class="card text-center">
						  	<div class="card-header">
						    	添加商品到订单中
						  	</div>
						  	<div class="card-body">
								<form method="post" action="admin/OrderServlet?action=add" onsubmit="return checkInput();">
									<div class="input-group mb-3">
									  	<label for="staticEmail" class="col-sm-1 col-form-label">服装ID</label>
										<input id="clothingIdInput" type="text" name="clothingId" class="form-control" placeholder="请输入服装ID" aria-label="" aria-describedby="button-addon2">
									</div>
									<div class="input-group mb-3">
									  	<label for="staticEmail" class="col-sm-1 col-form-label">数量</label>
										<input id="numInput" type="text" name="number" class="form-control" placeholder="请输入订购数量" aria-label="" aria-describedby="button-addon1">
									</div>
	
									<button id="addBtn" type="submit" class="btn btn-primary myAdd-btn">添加</button>
								</form>
						  	</div>
						</div>
					</div>

					<%
						// 判断订单是否存在
				  		if(request.getSession().getAttribute("cart") != null)
				  		{
					 %>

					<div class="card mb-4">
						<div class="card text-center">
							<div class="card-header">
								订购详情
							</div>
						  	<div class="card-body">

								<table class="table table-bordered text-center">
								  	<thead class="table-primary">
									    <tr>
										    <th scope="col">#</th>
										    <th scope="col">订购产品</th>
										    <th scope="col">数量</th>
										    <th scope="col">操作</th>
									    </tr>
								  	</thead>
								  	<tbody>
								  	<!-- 循环开始 -->
								  	<%
								  			Cart order = (Cart)request.getSession().getAttribute("cart");
								  			HashMap<ClothingInfo, Integer> goods = order.getGoods();
								  			Set<ClothingInfo> items = goods.keySet();
								  			Iterator<ClothingInfo> it = items.iterator();
								  			int row = 0;
								  			while(it.hasNext()){
								  				ClothingInfo c = it.next();
								  				row++;
								  	 %>
								    	<tr>
								      		<th scope="row"><%=row %></th>
								      		<td><%=c.getClothingID() %></td>
								      		<td><%=goods.get(c) %></td>
								      		<td><a href="admin/OrderServlet?action=delete&clothingId=<%=c.getClothingID() %>" onclick="return delcfm();">删除</a></td>
								    	</tr>
								  		<%
								  			}
								  		 %>
								  	 <!-- 循环结束 -->
									</tbody>
								</table>
								<form method="post" action="admin/OrderServlet?action=create">
									<button type="submit" class="btn btn-primary">生成订单</button>
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
                    <div class="d-flex align-item                 <div class="text-muted">Copyright &copy; PanPan</div>
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
    <script type="text/javascript" src="js/view/user/order_good.js"></script>
    
    <!-- Modal -->
	<!--消息提示框 -->
    <jsp:include page="/modal/modal_message.jsp"></jsp:include>
    
  </body>
</html>
