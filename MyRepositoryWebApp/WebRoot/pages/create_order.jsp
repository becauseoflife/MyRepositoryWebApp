<%@page import="com.entity.ClothingInfo"%>
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
    
    <script src="js/all.min.home.js" crossorigin="anonymous"></script>
    <script type="text/javascript" language="javascript">
		 function delcfm(){
		 	if(!window.confirm("确认要删除？")){
		 		window.event.returnValue = false;
		 	}
		 }
	 </script>
  </head>
  
  <body class="sb-nav-fixed">
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
                    <a class="dropdown-item" href="#">设置</a>
                    <a class="dropdown-item" href="#">Activity Log</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="login.html">退出登录</a>
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
                    <text>Admin</text>
                </div>
            </nav>
        </div>

        <div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
					<h1 class="mt-4">创建订单</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="index.html">订单拣货</a></li>
						<li class="breadcrumb-item active">创建订单</li>
					</ol>

					<div class="card mb-4">
						<div class="card text-center">
						  	<div class="card-header">
						    	添加商品到订单中
						  	</div>
						  	<div class="card-body">
								<form method="post" action="CartServlet?action=add">
									<div class="input-group mb-3">
									  	<label for="staticEmail" class="col-sm-1 col-form-label">服装ID</label>
										<input type="text" name="clothingId" class="form-control" placeholder="请输入服装ID" aria-label="" aria-describedby="button-addon2">
									</div>
									<div class="input-group mb-3">
									  	<label for="staticEmail" class="col-sm-1 col-form-label">数量</label>
										<input type="text" name="number" class="form-control" placeholder="请输入订购数量" aria-label="" aria-describedby="button-addon1">
									</div>
	
									<button type="submit" class="btn btn-primary myAdd-btn">添加</button>
								</form>
						  	</div>
						</div>
					</div>

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
								  		// 判断订单是否存在
								  		if(request.getSession().getAttribute("order") != null)
								  		{
								  			Cart order = (Cart)request.getSession().getAttribute("order");
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
								      		<td><a href="CartServlet?action=delete&clothingId=<%=c.getClothingID() %>" onclick="return delcfm();">删除</a></td>
								    	</tr>
								  		<%
								  			}
								  		 %>
								  	<%
								  		}
								  	 %>	
								  	 <!-- 循环结束 -->
									</tbody>
								</table>
								<form method="post" action="CartServlet?action=create_order">
									<button type="submit" class="btn btn-primary">生成订单</button>
								</form>
						  	</div>
						</div>
					</div>

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
