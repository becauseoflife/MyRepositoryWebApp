<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.mapper.CheckTaskRecord"%>
<%@page import="com.mapper.CheckTask"%>
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
    
    <title>盘点任务</title>
    
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    
    <link href="css/style.check.css" rel="stylesheet" />
    <link href="css/styles.home.css" rel="stylesheet" />
    <link href="css/myStyles.home.css" rel="stylesheet" />
    <link href="css/check_good.css" rel="stylesheet"/>
   	<!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.0-dist/css/bootstrap.min.css">
    
    
    <script src="js/all.min.js" crossorigin="anonymous"></script>
    <script type="text/javascript" language="javascript">

	 </script>
    
  </head>
  
  <body class="sb-nav-fixed" onload="getCheckTasks();">
 		<!-- 引入工具栏 -->
		<jsp:include page="/template/user_menu.jsp"></jsp:include>

        <div id="layoutSidenav_content">
			<main>
				<div class="container-fluid">
					<h1 class="mt-4">盘点任务</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="user/SearchUIServlet">仓库管理</a></li>
						<li class="breadcrumb-item active">盘点任务</li>
					</ol>

					<div class="card mb-4">
						<div class="card text-center">
							<div class="card-header">
								盘点任务
							</div>
						  	<div class="card-body">
						  		<form method="post" action="user/CheckServlet?action=start" onsubmit="return checkTaskSelect();"> 
						  		
									<select name="select_task" id="check-task-select" class="custom-select">
									</select>
									<div style="height: 3vh;"></div>
									<button id="start-btn" type="submit" class="btn btn-primary myAdd-btn">开始</button>
									
								</form>
							</div>
						</div>
					</div>
	
			<%
				if(session.getAttribute("task")!=null)
				{
					CheckTask task = (CheckTask)session.getAttribute("task");
					CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
					Double progress = Double.valueOf(String.format("%.2f", checkTable.getDataSet().size() / 12.0));
					double percentage = progress * 100;
					Double percentageD = Double.valueOf(String.format("%.2f",percentage));
			 %>
			 		<!-- 进度 -->
			 		<div class="card mb-4">					
						<div class="card-body row">
							<div class="col-1">任务进度:</div>
							<div class="col-11">
								<div class="progress">	
								  	<div class="progress-bar progress-bar-striped bg-success" role="progressbar" style="width: <%=percentageD %>%" aria-valuenow="<%=(int)percentage %>" aria-valuemin="0" aria-valuemax="100"><%=percentageD %>%</div>
								</div>
							</div>
						</div>
					</div>

					<form method="post" action="user/CheckServlet?action=query" onsubmit="return checkSearchInput();">
						<div class="card mb-4">
							<div class="card-header">
								输入仓库1-12货架位置信息查询该位置产品的ID和数量,然后进行12个货架的盘点更新
							</div>
							<div class="card-body">
						
							
								<div class="input-group mb-3">
								  	<label for="staticEmail" class="col-sm-1 col-form-label">货架</label>
									<input id="shelvesInput" disabled="disabled" type="text" name="task_shelves" value="<%=task.getShelves() %>" class="form-control" aria-label="" aria-describedby="button-addon2">
								</div>
								<div class="input-group mb-3">
								  	<label for="staticEmail" class="col-sm-1 col-form-label">货位</label>
									<input id="locationInput" type="text" name="location" class="form-control" placeholder="请输入货位" >
											<div class="invalid-feedback" style="margin-left: 100px;">
								       			货位1-12
								     		</div>
								</div>
								<div class="input-group mb-3">
									<button id="searchBtn" type="submit" class="btn btn-primary mySearchBtn">查询</button>
								</div>
							</div>
						</div>
					</form>	
					
					<%
						ClothingInfo c = (ClothingInfo)session.getAttribute("clothingInfo");
						if(c!=null && c.getClothingID() != null)
						{
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
							    	<form method="post" action="user/CheckServlet?action=update" onsubmit="return checkUpdateInput();"> 
							    		<div class="input-group mb-3">
											<button id="updateBtn" type="submit" class="btn btn-primary  myUpdate-btn" style="padding: 0 40px;">更新</button>
										
											<input id="numInput" type="text" name="updateNum" class="form-control" placeholder="请输入盘点数量" aria-label="Recipient's username" aria-describedby="button-addon2">
										</div>
									</form>
								</div>
							</div>
						</div>
						
						<%
							} // if -> c
						%>
						<% 
							Object message = session.getAttribute("messageTip");
							if(message!=null){
						 %>
							<div class="card mb-4">
								<div class="card">
								  	<div class="card-header">
								    	查询结果
								  	</div>
								  	<div class="card-body">
								   		<blockquote class="blockquote mb-0">
								      	<p><%=message %> </br>如果存在请输入</p>
								    	</blockquote>
								    	<form action="user/CheckServlet?action=insert" method="post" onsubmit="return checkPutOnInput();"> 
											<div class="input-group mb-3">
											  	<label for="staticEmail" class="col-sm-1 col-form-label">服装ID</label>
												<input id="clothingIdInput" type="text" name="clothingId" class="form-control" placeholder="请输入服装ID" aria-label="" aria-describedby="button-addon2">
											</div>
											<div class="input-group mb-3">
											  	<label for="staticEmail" class="col-sm-1 col-form-label">数量</label>
												<input id="numInput" type="text" name="number" class="form-control" placeholder="请输入数量" aria-label="" aria-describedby="button-addon1">
											</div>
											<div class="input-group mb-3">
												<button id="putOnBtn" type="submit" class="btn btn-primary mySearchBtn">更新</button>
												<a id="non-existent-btn" href="user/CheckServlet?action=null"><button id="putOnBtn" type="button" style="margin-left: 20px;" class="btn btn-primary mySearchBtn">不存在</button></a>
											</div>
										</form>
									</div>
								</div>
							</div>
						<%
							session.removeAttribute("messageTip");
							}
						 %>
						 
						<%
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
													    <th scope="col">原数量</th>
													    <th scope="col">盘点</th>
													    <th scope="col">盘盈</th>
													    <th scope="col">盘亏</th>
													    <th scope="col">盘点时间</th>
													    <!-- <th scope="col">操作</th> -->
											    	</tr>
											  	</thead>
											  	<tbody>
										<%
											int row = 0;
											for(CheckTaskRecord record : checkTable.getDataSet())
											{
												row++;
												session.setAttribute(row+"", record);
												DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
										 %>
											    	<tr>
											      		<th scope="row"><%=row %></th>
											      		<td><%=record.getClothingID() %></td>
											      		<td><%=record.getPosition()%></td>
											      		<td><%=record.getNumber() %></td>
											      		<td><%=record.getCheckNum() %></td>
											      		<td><%=record.getSurplus() %></td>
											      		<td><%=record.getLoss() %></td>
											      		<td><%=df.format(record.getCheck_time()) %></td>
											      		<%-- <td><a href="user/CheckServlet?action=delete?row=<%=row %>" onclick="return delcfm();">删除</a></td> --%>
											    	</tr>
										<%
											}
										 %>
											  	</tbody>
											</table>
			
										</div>
										<form method="post" action="user/CheckServlet?action=exportExcel">
											<div class="card-footer text-muted text-left">
												<button type="submit" class="btn btn-success">导出Excel</button>
											</div>
										</form>
									</div>
								</div>
						<%
							}
						 %>
						 
						 
						 
						 
						 
			<%
				}	// if -> task != null
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
    <script src="js/view/user/check_good.js"></script>
    
    <!-- Modal -->
	<!--消息提示框 -->
    <jsp:include page="/modal/modal_message.jsp"></jsp:include>
    
  </body>
</html>
