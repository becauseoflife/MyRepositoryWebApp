<%@page import="com.entity.User"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>主页</title>
    
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
						<h1 class="mt-4">库存查询</h1>
						<ol class="breadcrumb mb-4">
							<li class="breadcrumb-item active">库存查询</li>
						</ol>

						<div class="card mb-4">
							<div class="card-body">输入服装ID查询该服装的数量和位置</div>
						</div>
						<form method="post" action="SearchServlet" onsubmit="return checkSearchInput(this);">
							<div class="input-group mb-3">
								<div class="input-group-append">
									<button id="searchBtn" type="submit" class="btn btn-primary mySearch-btn" style="padding: 0 40px;">查询</button>
							  	</div>
							  	<div class="btn-input-space"></div>
								<input id="clothingIdInput" type="text" name="clothingId" class="form-control" placeholder="请输入服装ID" aria-label="Recipient's username">
									
							</div>
						</form>
						
						<div class="search-result-space"></div>
						
						<%
							String clothingID = (String)session.getAttribute("clothingID");
							Object sum = session.getAttribute("clothingSum");
							List<String> list = (List<String>)session.getAttribute("indexList");
							Object isSearch = session.getAttribute("isSearch");
							String str = "";
							if(isSearch!=null){
								if( (sum!=null) && (list!=null) && (list.size()!=0) ){								  	
						 %>
									<div class="card">
									  	<div class="card-header">
									    	服装ID:&nbsp;<strong><%=clothingID %></strong>&nbsp;的查询结果
									  	</div>
									  	<div class="card-body">
									    	<blockquote class="blockquote mb-0">
									      	<p>服装数量: <%=sum %> 件</p>
									      	
									      	<p>库存位置:
									      	<%for(String s : list) 
									      		str = s;
									      	%>
									      	<%=str%>&nbsp;
									      	</p>
									    	</blockquote>
										</div> 
									</div>
							  <%
							  	}else{
							   %>	
							   		<div class="card">
									  	<div class="card-header">
									    	查询结果
									  	</div>
									  	<div class="card-body">
									   		<blockquote class="blockquote mb-0">
									      	<p>仓库中没有找到该服饰</p>
									    	</blockquote>
										</div> 
									</div>
							<%
								}
							}
							session.removeAttribute("clothingID");
							session.removeAttribute("isSearch");
							session.removeAttribute("clothingSum");
							session.removeAttribute("indexList");
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
    <script>
  	   /*错误class  form-control is-invalid
  		正确class  form-control is-valid*/
  		let searchFlag = false;
  		$(function(){
  			let flagInput = false;
  			let clothingId;
  			
  			$("#clothingIdInput").change(function()
  			{
  				clothingId=$("#clothingIdInput").val();
  				if(clothingId==""||clothingId==null)
  				{
  					$("#clothingIdInput").removeClass("form-control is-valid")
    				$("#clothingIdInput").addClass("form-control is-invalid");
    				flagInput=false;
  				}
  				else
  				{
  					$("#clothingIdInput").removeClass("form-control is-invalid")
    				$("#clothingIdInput").addClass("form-control  is-valid");
    				flagInput=true;
  				}
  			})
  			$("#searchBtn").click(function(){
  				if(flagInput)
  				{
  					searchFlag=true;
  				}
  				else
  				{
  					$("#clothingIdInput").addClass("form-control is-invalid");
  				}	
  			})
  		})
  		
  		function checkSearchInput(obj)
  		{
  			return searchFlag;
  		}
  		
    </script>
  </body>
</html>
