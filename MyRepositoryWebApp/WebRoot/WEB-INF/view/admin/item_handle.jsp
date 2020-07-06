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
    
    <title>拣货分配</title>
    
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    
    <link href="css/styles.home.css" rel="stylesheet" />
    <link rel="stylesheet" href="css/styles.login.register.css" id="theme-stylesheet">
   	<!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.0-dist/css/bootstrap.min.css">
    
    <script src="js/all.min.js" crossorigin="anonymous"></script>

    
  </head>
  
  <body class="sb-nav-fixed">
		<!-- 菜单列表 -->
		<jsp:include page="/template/admin_menu.jsp"></jsp:include>

        <div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
                       <h1 class="mt-4">拣货分配</h1>
                       <ol class="breadcrumb mb-4">
                           <li class="breadcrumb-item"><a href="admin/UserManagementServlet?action=getUser">仓库管理</a></li>
                           <li class="breadcrumb-item active">拣货分配</li>
                       </ol>

			<%
				if(session.getAttribute("itemInfoList")!=null && session.getAttribute("userList") != null)
				{
					List<OrderItemInfo> itemInfoList = (List<OrderItemInfo>)session.getAttribute("itemInfoList");
					
			 %>

					<div class="">
						<div class="card text-center">
							<div class="card-header">
								待处理订单
							</div>
						  	<div class="card-body">
								<table class="table table-bordered text-center">
								  	<thead>
									    <tr  class="table-primary">
										    <th scope="col">#</th>
										    <th scope="col">订单ID</th>
										    <th scope="col">服装ID</th>
										    <th scope="col">数量</th>
										    <th scope="col">分配人员</th>
										    <th scope="col">分配</th>
									    </tr>
								  	</thead>
									<tbody>
									<%
										int row = 0;
										for(OrderItemInfo item : itemInfoList)
										{
											row++;
									 %>
											<tr>
									      		<th scope="row"><%=row %></th>
									      		<td><%=item.getOrder_id() %></td>
									      		<td><%=item.getClothingID() %></td>
									      		<td><%=item.getNumber() %></td>
									      		<td><%=item.getPick_user() %></td>
									      		<td>
									      		<a href="#" data-toggle="modal" data-target="#ItemHandleModal" style="margin: 3px;">
									    			<image src="images\edit.png" />
									    		</a>
									      		</td>
									    	</tr>
								    <%
								    	}
								     %>	
								    	
								  	</tbody>
								</table>	
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
	
	<script type="text/javascript" src="js/view/admin/order_handle.js"></script>
	
	<jsp:include page="/modal/modal_message.jsp"></jsp:include>
	<jsp:include page="/modal/modal_Item_handle.jsp"></jsp:include>

	<script type="text/javascript">
		
	</script>
	
  </body>
</html>
