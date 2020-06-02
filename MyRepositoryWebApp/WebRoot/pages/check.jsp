<%@page import="com.entity.User"%>
<%@page import="com.entity.Check"%>
<%@page import="com.entity.CheckTable"%>
<%@page import="com.entity.ClothingInfo"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>盘点</title>
    
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    
    <link href="css/style.check.css" rel="stylesheet" />
    <link href="css/styles.home.css" rel="stylesheet" />
    <link href="css/myStyles.home.css" rel="stylesheet" />
   	<!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.0-dist/css/bootstrap.min.css">
    
    <script src="js/all.min.js" crossorigin="anonymous"></script>
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
                    <text><%=user.getUserName() %></text>
                </div>
            </nav>
        </div>

        <div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
					<h1 class="mt-4">库存盘点</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item active">库存盘点</li>
					</ol>

					<form method="post" action="CheckServlet?action=query" onsubmit="return checkSearchInput();">
						<div class="card mb-4">
							<div class="card-header">
								输入库存位置信息查询该位置产品的ID和数量
							</div>
							<div class="card-body">
								<div class="input-group mb-3">
								  	<label for="staticEmail" class="col-sm-1 col-form-label">货架</label>
									<input id="shelvesInput" type="text" name="shelves" class="form-control" placeholder="请输入货架" aria-label="" aria-describedby="button-addon2">
								</div>
								<div class="input-group mb-3">
								  	<label for="staticEmail" class="col-sm-1 col-form-label">货位</label>
									<input id="locationInput" type="text" name="location" class="form-control" placeholder="请输入货位" aria-label="" aria-describedby="button-addon1">
								</div>
								<div class="input-group mb-3">
									<button id="searchBtn" type="submit" class="btn btn-primary mySearchBtn">查询</button>
								</div>
							</div>
						</div>
					</form>	
					
					<%
						ClothingInfo c = (ClothingInfo)session.getAttribute("clothingInfo");
						if(c!=null){
						
					 %>
					
						<div class="card mb-4">
							<div class="card">
							  	<div class="card-header">
							    	库存位置: <strong><%=c.getShelves() %>-<%=c.getLocation() %></strong> 的查询结果
							  	</div>
							  	<div class="card-body">
							   		<blockquote class="blockquote mb-0">
							      	<p>服装ID&nbsp;:&nbsp;<%=c.getClothingID() %></p>
							      	<p>&nbsp;数&nbsp;量&nbsp;&nbsp;:&nbsp;<%=c.getNumber() %>&emsp;件</p>
							    	</blockquote>
							    	<form method="post" action="CheckServlet?action=update" onsubmit="return checkUpdateInput();"> 
							    		<div class="input-group mb-3">
											<button id="updateBtn" type="submit" class="btn btn-primary  myUpdate-btn" style="padding: 0 40px;">更新</button>
										
											<input id="numInput" type="text" name="updateNum" class="form-control" placeholder="请输入盘点数量" aria-label="Recipient's username" aria-describedby="button-addon2">
										</div>
									</form>
								</div>
							</div>
						</div>
						
						<%
							}
						 %>
					
						<%
							CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
							if(checkTable!=null && checkTable.getDataSet().size()!=0)
							{
						 %>
							<div class="card mb-4">
								<div class="card text-center">
									<div class="card-header">
										盘点表格
									</div>
								  	<div class="card-body">

											<table class="table table-bordered text-center">
											  	<thead class="table-primary">
											    	<tr>
											      		<th scope="col">#</th>
													    <th scope="col">服装ID</th>
													    <th scope="col">位置</th>
													    <th scope="col">数量</th>
													    <th scope="col">盘点</th>
													    <th scope="col">盘盈</th>
													    <th scope="col">盘亏</th>
													    <!-- <th scope="col">操作</th> -->
											    	</tr>
											  	</thead>
											  	<tbody>
										<%
											int row = 0;
											for(Check check : checkTable.getDataSet())
											{
												row++;
												session.setAttribute(row+"", check);
										 %>
											    	<tr>
											      		<th scope="row"><%=row %></th>
											      		<td><%=check.getClothingID() %></td>
											      		<td><%=check.getLocation() %></td>
											      		<td><%=check.getNumber() %></td>
											      		<td><%=check.getCheckNum() %></td>
											      		<td><%=check.getSurplus() %></td>
											      		<td><%=check.getLoss() %></td>
											      		<%-- <td><a href="CheckServlet?action=delete&row=<%=row %>" onclick="return delcfm();">删除</a></td> --%>
											    	</tr>
										<%
											}
										 %>
											  	</tbody>
											</table>
			
										</div>
										<form method="post" action="CheckServlet?action=exportExcel">
											<div class="card-footer text-muted text-left">
												<button type="submit" class="btn btn-success">导出Excel</button>
											</div>
										</form>
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
    <script type="text/javascript">
      /*错误class  form-control is-invalid
  		正确class  form-control is-valid*/
  		let searchSubmit = false;
  		let updateSubmit = false;
  		
  		$(function(){
  			let flagShelvesInput = false;
  			let flagLocationInput = false;
  			
  			let shelves, location;
  			
  			$("#shelvesInput").change(function(){
  				shelves = $("#shelvesInput").val();
  				if(shelves==""||shelves==null)
  				{
  					$("#shelvesInput").removeClass("form-control is-valid")
    				$("#shelvesInput").addClass("form-control is-invalid");
    				flagShelvesInput=false;
  				}
  				else
  				{
  					$("#shelvesInput").removeClass("form-control is-invalid")
    				$("#shelvesInput").addClass("form-control  is-valid");
    				flagShelvesInput=true;
  				}
  			})
  			
  			$("#locationInput").change(function(){
  				location = $("#locationInput").val();
  				if(location==""||location==null)
  				{
  					$("#locationInput").removeClass("form-control is-valid")
    				$("#locationInput").addClass("form-control is-invalid");
    				flagLocationInput=false;
  				}
  				else
  				{
  					$("#locationInput").removeClass("form-control is-invalid")
    				$("#locationInput").addClass("form-control  is-valid");
    				flagLocationInput=true;
  				}
  			})
  			
  			$("#searchBtn").click(function(){
  				if(flagShelvesInput&&flagLocationInput)
  				{
  					searchSubmit=true;
  				}
  				else
  				{
  					if(!flagShelvesInput)
  						$("#shelvesInput").addClass("form-control is-invalid");
  					if(!flagLocationInput)
  						$("#locationInput").addClass("form-control is-invalid");
  				}
  			})
  			
  			let flagNumInput=false;
  			
  			let numInput;
  			$("#numInput").change(function(){
  				numInput = $("#numInput").val();
  				if(numInput==""||numInput==null)
  				{
  					$("#numInput").removeClass("form-control is-valid")
    				$("#numInput").addClass("form-control is-invalid");
    				flagNumInput=false;
  				}
  				else
  				{
  					$("#numInput").removeClass("form-control is-invalid")
    				$("#numInput").addClass("form-control  is-valid");
    				flagNumInput=true;
  				}
  			})
  			
  			$("#updateBtn").click(function(){
  				if(flagNumInput)
  					updateSubmit=true;
  				else
  					$("#numInput").addClass("form-control is-invalid");
  			})
  		})
  		
  		function checkSearchInput(){
  			return searchSubmit;
  		}
  		
  		function checkUpdateInput(){
  			return updateSubmit;
  		}
    </script>
  </body>
</html>
