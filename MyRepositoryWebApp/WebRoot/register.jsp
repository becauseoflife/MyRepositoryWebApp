<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>注册</title>
    
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">  
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="robots" content="all,follow">
    

    <link rel="stylesheet" href="css/styles.login.register.css" id="theme-stylesheet">
    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.0-dist/css/bootstrap.css">
    
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
                    <h1>欢迎注册</h1>
                  </div>
                  <p>仓库管理管理系统</p>
                </div>
              </div>
            </div>
            <!-- Form Panel    -->
            <div class="col-lg-6 bg-white">
              <div class="form d-flex align-items-center">
                <div class="content">
                	<form method="post" action="RegisterServlet" class="form-validate" id="registerFrom" onsubmit="return checkAll(this);">
	                    <div class="form-group">
	                      <input id="register-username" class="input-material" type="text" name="registerUsername" placeholder="请输入用户名/姓名" >
									      <div class="invalid-feedback">
									        	用户名必须在2~10位之间
									      </div>
	                    </div>
	                    <div class="form-group">
	                      <input id="register-password" class="input-material" type="password" name="registerPassword" placeholder="请输入密码"   >
	                    				  <div class="invalid-feedback">
									        	密码必须在6~10位之间
									      </div>
	                    </div>
	                    <div class="form-group">
	                      <input id="register-passwords" class="input-material" type="password" name="registerPasswords" placeholder="确认密码"   >
	                    				  <div class="invalid-feedback">
									        	两次密码必须相同 且在6~10位之间
									      </div>
	                    </div>
	                    <div class="form-group">
	                      <input id="register-telephone" class="input-material" type="text" maxlength="11" name="registerTelephone" placeholder="请输入联系电话" >
									      <div class="invalid-feedback">
									        	请输入11位的电话号码
									      </div>
	                    </div>
	                    <div class="form-group">
	                      <input id="register-email" class="input-material" type="email" name="registerEmail" placeholder="请输入电子邮件" >
									      <div class="invalid-feedback">
									        	请输入电子邮件
									      </div>
	                    </div>
	                    <div class="form-group">
	                      <button id="regbtn" type="submit" name="registerSubmit" class="btn btn-primary">注册</button>
	                    </div>
	                 </form>
                  <small>已有账号?</small><a href="login.jsp" class="signup">&nbsp;登录</a>
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
	<script src="js/view/register.js"></script>

  </body>
  
</html>
