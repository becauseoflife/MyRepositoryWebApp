<%@page import="com.entity.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.entity.OrderGoods"%>
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
    
    <script src="js/all.min.home.js" crossorigin="anonymous"></script>
    <script>
    	function getOrderId(){
			$.ajax({
				url: "PickGoodsServlet?action=getSelectOption",
				type: "GET",
				dataType: "json",
				async: false,
				success: function(res){
					console.log(res);
					if(res.length == 0)
					{
						$("#orderSelect").append("<option>无</option>");
					}
					else
					{
						$("#orderSelect").append("<option>--请选择--</option>");
						for(let i=0; i<res.length; i++)
						{
							$("#orderSelect").append("<option value="+ res[i].order_id +">" + res[i].order_id + "</option>");
						}
					}
				},
				error: function(res){
					console.log("请求错误");
					$("#orderSelect").append("<option>请求失败</option>");
				} 
			})
		};
    </script>
    
  </head>
  
  <body class="sb-nav-fixed" onload="getOrderId();">
    <nav class="sb-topnav navbar navbar-expand navbar-dark bg-darks">
        <a class="navbar-brand" href="pages/home.jsp">仓库管理</a>
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
                        	<a id="my-nav-link" class="nav-link" href="pages/home.jsp">
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
                                	<a class="nav-link" href="pages/create_order.jsp">创建订单</a>
                                	<a class="nav-link" href="pages/pick_good.jsp">拣货操作</a>
                                </nav>
                            </div>
	                        <a id="my-nav-link"  class="nav-link" href="pages/check.jsp">
	                        	<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
	                           	<text>库存盘点</text>
	                        </a>
                        <div class="sb-sidenav-menu-heading">其他</div>
                    </div>
                </div>
                <div class="sb-sidenav-footer">
                    <div class="small">用户:</div>
                    <% User user = (User)session.getAttribute("user"); %>
                    <text>&emsp;<%=user.getUserName() %></text>
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
						  		<form method="post" action="PickGoodsServlet?action=getOrderInfo">
						  		
									<select name="select_orderId" id="orderSelect" class="custom-select">
									</select>
									<div style="height: 3vh;"></div>
									<button type="submit" class="btn btn-primary myAdd-btn">查看</button>
									
								</form>
							</div>
						</div>
					</div>

					<div class="card mb-4">
						<div class="card text-center">
							<div class="card-header">
								订单详情
							</div>
						  	<div class="card-body">
							
							<%
								if(session.getAttribute("order_goods_list") != null){
									List<OrderGoods> goods = (List<OrderGoods>)session.getAttribute("order_goods_list");
							 %>
							
							<table class="table table-bordered text-center">
								  	<thead>
									    <tr>
									    	<th class="table-primary"></th>
										    <th class="table-primary" scope="col" colspan="1">订单ID</th>
										    <th scope="col" colspan="4"><%=goods.get(0).getOrder_id() %></th>
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
								  		for(OrderGoods g : goods){
								  		row++;
								  		if(g.getPick_sign() == OrderGoods.NOT_PICK)
								  		{
								  			pickSign = "未拣货";
								  		}
								  		else if(g.getPick_sign() == OrderGoods.PICK)
								  		{
								  			pickSign = "已拣货";
								  		}
								  		else if(g.getPick_sign() == OrderGoods.PICK_FAILED)
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
								      		if(g.getPick_time() != null){
								      	 %>
								      		<td><%=df.format(g.getPick_time()) %></td>
								      	<%
								      		}else{
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
								<form method="post" action="PickGoodsServlet?action=pickGood">
									<div class="input-group mb-3">
									  	<label for="staticEmail" class="col-sm-1 col-form-label">服装ID</label>
										<input type="text" name="clothingId" class="form-control" placeholder="请输入服装ID" aria-label="" aria-describedby="button-addon2">
									</div>
	
									<button type="submit" class="btn btn-primary myAdd-btn">拣货</button>
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
    
    
    <!-- Modal -->
	<div id="myModal" class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalCenterTitle">提示信息</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div id="message" class="modal-body">
	        
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">知道啦</button>
	      </div>
	    </div>
	  </div>
	</div>
    <!-- JQuery -->
    <script type="text/javascript" src="resources/jquery-3.4.1/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="resources/bootstrap-4.5.0-dist/js/bootstrap.min.js"></script>
    
    <script src="resources/bootstrap-4.5.0-dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="js/scripts.home.js"></script>	
    <script type="text/javascript">
    	$('#myModal').modal('hide');
    </script>
    <%
    	Object message = session.getAttribute("message");
    	if(message!=null && !message.equals("")){
     %>
      <script type="text/javascript">
      	$('#message').html("<%=message%>");
          $('#myModal').modal('show');
      </script>
	<%
		}
		session.removeAttribute("message");
	 %>
  </body>
</html>
