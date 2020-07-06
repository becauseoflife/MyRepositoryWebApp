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
    
    <title>用户管理</title>
    
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    
    <link href="css/styles.home.css" rel="stylesheet" />
    <link rel="stylesheet" href="css/styles.login.register.css" id="theme-stylesheet">
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
		<!-- 菜单列表 -->
		<jsp:include page="/template/admin_menu.jsp"></jsp:include>
		
        <div id="layoutSidenav_content">
			<main>

				<div class="container-fluid">
                       <h1 class="mt-4">用户管理</h1>
                       <ol class="breadcrumb mb-4">
                           <li class="breadcrumb-item"><a href="admin/UserManagementServlet?action=getUser">仓库管理</a></li>
                           <li class="breadcrumb-item active">用户管理</li>
                       </ol>
                       <div class="card mb-4">
                           <div class="card-body">
                           		<button id="add-user" type="button" class="btn btn-primary" data-toggle="modal" data-target="#staticBackdrop">新增用户</button>
                           </div>
                       </div>
                       <div class="card mb-4">
				
						<table class="table table-striped text-center">
						  	<thead class="table-primary">
						    	<tr>
							    	<th scope="col">#</th>
							      	<th scope="col">用户名</th>
							      	<th scope="col">密码</th>
							      	<th scope="col">联系方式</th>
							      	<th scope="col">电子邮件</th>
							      	<th scope="col">注册时间</th>
							      	<th scope="col">操作</th>
						    	</tr>
						  	</thead>
						  	<tbody id="tbody">
 						 <%
						  if(session.getAttribute("userList") != null){
						  		List<UserInfo> result = (List<UserInfo>)session.getAttribute("userList");
						  		int row = 0;
						  		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
								df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
						  		for(UserInfo u : result)
						  		{
						  			row++;
						  %>
						   		<tr>
								    <th scope="row"><%=row %></th>
								    <td><%=u.getUserName() %></td>
								    <td><%=u.getPassword() %></td>
								    <td><%=u.getTelephone() %></td>
								    <td><%=u.getEmail() %></td>
								    <td><%=df.format(u.getRegDate() ) %></td>
								    <td>
									    <a href="#" data-toggle="modal" data-target="#updateUserModal" style="margin: 3px;">
									    	<image src="images\edit.png" />
									    </a>
									    <a href="admin/UserManagement?action=deleteUser&username=<%=u.getUserName() %>" style="margin: 3px;" onclick="return delcfm();">
									    	<image src="images\delete.png" />
									    </a>
								    </td>
						    	</tr>
						  <%
						  		}
						  	//session.removeAttribute("userList");
						  	}
						  	
						   %>  
						   	</tbody>
						</table>
                           
                       </div>
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
	
	
	<jsp:include page="/modal/modal_add_user.jsp"></jsp:include>
	<jsp:include page="/modal/modal_update_user.jsp"></jsp:include>
	<jsp:include page="/modal/modal_message.jsp"></jsp:include>

	
  </body>
</html>
