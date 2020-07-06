<%@page import="com.mapper.UserInfo"%>
<%@page import="com.entity.SearchResult"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>库存查询</title>
    
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
		<jsp:include page="/template/user_menu.jsp"></jsp:include>

        <div id="layoutSidenav_content">
				<main>
					<div class="container-fluid">
						<h1 class="mt-4">库存查询</h1>
						<ol class="breadcrumb mb-4">
							<li class="breadcrumb-item"><a href="user/SearchUIServlet">仓库管理</a></li>	
							<li class="breadcrumb-item active">库存查询</li>
						</ol>

						<div class="card mb-4">
							<div class="card-body">输入服装ID查询该服装的数量和位置</div>
						</div>
						<form method="post" action="user/SearchServlet?action=search" onsubmit="return checkSearchInput(this);">
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
							if(session.getAttribute("searchResult")!=null){
								SearchResult result = (SearchResult)session.getAttribute("searchResult");
								String str="";
								if(result.getPosition().size()>0){								  	
						 %>
									<div class="card">
									  	<div class="card-header">
									    	服装ID:&nbsp;<strong><%=result.getClothingID() %></strong>&nbsp;的查询结果
									  	</div>
									  	<div class="card-body">
									    	<blockquote class="blockquote mb-0">
									      	<p>服装数量: <%=result.getSum() %> 件</p>
									      	
									      	<p>库存位置:
									      	<%for(String s : result.getPosition()) 
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
									    	服装ID:&nbsp;<strong><%=result.getClothingID() %></strong>&nbsp;的查询结果
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
							session.removeAttribute("searchResult");
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
    <script src="js/view/user/search_good.js"></script>
  </body>
</html>
