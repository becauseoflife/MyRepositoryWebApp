<%@page import="com.mapper.UserInfo"%>
<%@page import="com.entity.Check"%>
<%@page import="com.entity.CheckTable"%>
<%@page import="com.mapper.ClothingInfo"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>货品入库</title>
    
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
  
  <body class="sb-nav-fixed" >
  
		<!-- 引入工具栏 -->
		<jsp:include page="/template/user_menu.jsp"></jsp:include>

        <div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
				<form method="post" action="user/PutOnGoodServlet?action=putOn" onsubmit="return checkPutOnInput();">
					<h1 class="mt-4">货品入库</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="user/SearchUIServlet">仓库管理</a></li>
						<li class="breadcrumb-item active">货品入库</li>
					</ol>

				<%
				if(session.getAttribute("emptyPositionList")!=null)
				{
					List<ClothingInfo> emptyPositionList = (List<ClothingInfo>)session.getAttribute("emptyPositionList");
					if(emptyPositionList.size()!=0)
					{
				 %>
						<div class="card mb-4">
							<div class="card text-center">
								<div class="card-header">
									请选择上架位置
								</div>
							  	<div class="card-body">
									<select name="select_position" id="emptyPosition" class="custom-select">
										<option>--请选择--</option>
									<%
										for(ClothingInfo c:emptyPositionList)
										{
											String position = c.getShelves() + "-" + c.getLocation();
									 %>
											<option value="<%=position%>"><%=position%></option>
									<%
										}
									 %>
									</select>
								</div>
							</div>
						</div>


					<div id="put-on-input" class="card mb-4">
						<div class="card-header">
							请输入输入上架服装的ID和数量
						</div>
						<div class="card-body">
							
								<div class="input-group mb-3">
								  	<label for="staticEmail" class="col-sm-1 col-form-label">服装ID</label>
									<input id="clothingIdInput" type="text" name="clothingId" class="form-control" placeholder="请输入上架服装ID" aria-label="" aria-describedby="button-addon2">
								</div>
								<div class="input-group mb-3">
								  	<label for="staticEmail" class="col-sm-1 col-form-label">数量</label>
									<input id="numInput" type="text" name="number" class="form-control" placeholder="请输入上架数量" aria-label="" aria-describedby="button-addon1">
								</div>
								<div class="input-group mb-3">
									<button id="putOnBtn" type="submit" class="btn btn-primary mySearchBtn">入库</button>
								</div>
						
						</div>
					</div>
				<%
					}
				}
				else
				{
				 %>	
				 	<div class="card mb-4">
						<div class="card text-center">
							<div class="card-header">
								提示信息
							</div>
						  	<div class="card-body">
								仓库没有空位置		
							</div>
						</div>
					</div>
				 <%
				 }
				  %>
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
    
	<script type="text/javascript" src="js/view/user/put_on_good.js" charset="utf-8"></script>
	
	<!-- Modal -->
	<!--消息提示框 -->
    <jsp:include page="/modal/modal_message.jsp"></jsp:include>
	
  </body>
</html>
