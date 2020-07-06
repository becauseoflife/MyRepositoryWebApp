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
    
    <title>订单导入</title>
    
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    
    <link href="css/style.check.css" rel="stylesheet" />
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

					<h1 class="mt-4">订单导入</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="user/SearchUIServlet">仓库管理</a></li>
						<li class="breadcrumb-item active">订单导入</li>
					</ol>
					
				<form action="user/InportServlet" enctype="multipart/form-data" method="post">
					<div class="card mb-4">
						<div class="card text-center">
							<div class="card-header">
								订单文件导入模块
							</div>
						  	<div class="card-body">
								<div class="form-group mb-3">									
								    <div class="input-group  mb-3">
								  		<div class="input-group-prepend">
								  			<label class="input-group-btn"  style="margin-right: 15px;">
								    			<button class="btn btn-primary" type="submit" id="inputGroupFileAddon03">订单导入</button>
								    		</label>
								  		</div>										       	
								       	<input id='location' class="form-control" placeholder="请选择文件" onclick="$('#i-file').click();">
								           <label class="input-group-btn">
								               <input type="button" id="i-check" value="浏览文件" class="btn btn-primary" onclick="$('#i-file').click();">
								           </label>
								    </div>
								   	<input type="file" name="file" id='i-file'  accept=".xls, .xlsx, .xml" onchange="loadFile(this.files[0])" style="display: none">
								</div>										
							</div>
						</div>
					</div>

				</form>
				
				
				
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
    <script src="js/view/user/inport_order.js"></script>
    
	
	<!-- Modal -->
	<!--消息提示框 -->
    <jsp:include page="/modal/modal_message.jsp"></jsp:include>
	
  </body>
</html>
