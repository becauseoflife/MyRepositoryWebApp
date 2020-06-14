<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>登录</title>
    	
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">  
	<meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="robots" content="all,follow">
	
	<link rel="stylesheet" href="css/styles.login.register.css" id="theme-stylesheet">
	
	<!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.0-dist/css/bootstrap.min.css">
    <!-- JQuery -->
    <script type="text/javascript" src="resources/jquery-3.4.1/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="resources/bootstrap-4.5.0-dist/js/bootstrap.min.js"></script>
    
  </head>

  <body>
  
     <div class="page login-page">
      <div class="container d-flex align-items-center">
        <div class="form-holder has-shadow">
          <div class="row">
            <!-- Logo & Information Panel-->
            <div class="col-lg-6">
              <div class="info d-flex align-items-center">
                <div class="content">
                  <div class="logo">
                    <h1>欢迎登录</h1>
                  </div>
                  <p>仓库管理管理系统</p>
                </div>
              </div>
            </div>
            <!-- Form Panel    -->
            
            <div class="col-lg-6 bg-white">
              <div class="form d-flex align-items-center">
                <div class="content">
                  <form method="post" action="LoginServlet" class="form-validate" id="loginFrom">
                    <div class="form-group">
                      <input id="login-username" type="text" name="userName" required data-msg="请输入用户名" placeholder="用户名" value="admin" class="input-material">
                    </div>
                    <div class="form-group">
                      <input id="login-password" type="password" name="passWord" required data-msg="请输入密码" placeholder="密码" class="input-material">
                    </div>
                    <button id="login" type="submit" class="btn btn-primary">登录</button>
                    <div style="margin-top: -40px;"> 
                    	<!-- <input type="checkbox"  id="check1"/>&nbsp;<span>记住密码</span>
                    	<input type="checkbox" id="check2"/>&nbsp;<span>自动登录</span> -->
                    	<div class="custom-control custom-checkbox " style="float: right;">
							<input type="checkbox" class="custom-control-input" id="check2" >
							<label class="custom-control-label" for="check2">自动登录</label>
						</div>
						<div class="custom-control custom-checkbox " style="float: right;">
							<input type="checkbox" class="custom-control-input" id="check1" >
							<label class="custom-control-label" for="check1">记住密码&nbsp;&nbsp;</label>
						</div>
						<div class="custom-control custom-checkbox " style="float: right;margin-right: 8px;">
							<input name="role" type="checkbox" class="custom-control-input" id="check3" >
							<label class="custom-control-label" for="check3">管理员登录</label>
						</div> 
                    </div>
                  </form>
<!--                   <br />
                  <small>没有账号?</small><a href="register.jsp" class="signup">&nbsp;注册</a> -->
                </div>
              </div>
            </div>
          </div>
          
        </div>
      </div>
    </div>
    
	<!--消息提示框 -->
    <jsp:include page="/modal/modal_message.jsp"></jsp:include>
    
    <!-- JavaScript files-->
    <!-- Main File-->
    <script src="js/view/login.js"></script>

  </body>
  
</html>
