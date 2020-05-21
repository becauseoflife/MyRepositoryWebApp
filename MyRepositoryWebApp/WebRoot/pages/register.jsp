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
    
    <link rel="stylesheet" href="css/style.default.css" id="theme-stylesheet">
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
                	<form method="post" action="RegisterServlet" class="form-validate" id="registerFrom">
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
	                      <input id="register-email" class="input-material" type="text" name="registerEmail" placeholder="请输入电子邮件" >
									      <div class="invalid-feedback">
									        	请输入电子邮件
									      </div>
	                    </div>
	                    <div class="form-group">
	                      <button id="regbtn" type="submit" name="registerSubmit" class="btn btn-primary">注册</button>
	                    </div>
	                 </form>
                  <small>已有账号?</small><a href="pages/login.jsp" class="signup">&nbsp;登录</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- JavaScript files-->
    <script>
    	$(function(){
    		/*错误class  form-control is-invalid
    		正确class  form-control is-valid*/
    		var flagName = false;
    		var flagPas = false;
    		var flagPass = false;
    		var flagTele = false;
    		var flagEmail = false;
    		
    		var name,passWord,passWords,telephone, email;
    		/*验证用户名*/
    		$("#register-username").change(function(){
    			name=$("#register-username").val();
    			if(name.length<2||name.length>10){
    				$("#register-username").removeClass("form-control is-valid")
    				$("#register-username").addClass("form-control is-invalid");
    				flagName=false;
    			}else{
    				$("#register-username").removeClass("form-control is-invalid")
    				$("#register-username").addClass("form-control is-valid");
    				flagName=true;
    			}
    		})
    		/*验证密码*/
    		$("#register-password").change(function(){
    			passWord=$("#register-password").val();
    			if(passWord.length<6||passWord.length>18){
    				$("#register-password").removeClass("form-control is-valid")
    				$("#register-password").addClass("form-control is-invalid");
    				flagPas=false;
    			}else{
    				$("#register-password").removeClass("form-control is-invalid")
    				$("#register-password").addClass("form-control is-valid");
    				flagPas=true;
    			}
    		})
    		/*验证确认密码*/
    		$("#register-passwords").change(function(){
    			passWords=$("#register-passwords").val();
    			if((passWord!=passWords)||(passWords.length<6||passWords.length>18)){
    				$("#register-passwords").removeClass("form-control is-valid")
    				$("#register-passwords").addClass("form-control is-invalid");
    				flagPass=false;
    			}else{
    				$("#register-passwords").removeClass("form-control is-invalid")
    				$("#register-passwords").addClass("form-control is-valid");
    				flagPass=true;
    			}
    		})
    		/* 验证联系号码 */
    		$("#register-telephone").change(function(){
    			telephone = $("#register-telephone").val();
    			if(telephone.length != 11){
    				$("#register-telephone").removeClass("form-control is-valid")
    				$("#register-telephone").addClass("form-control is-invalid");
    				flagTele=false;
    			}else{
    				$("#register-telephone").removeClass("form-control is-invalid")
    				$("#register-telephone").addClass("form-control is-valid");
    				flagTele=true;
    			}
    		})
    		/* 验证电子邮件 */
    		$("#register-email").change(function(){
    			email = $("#register-email").val();
    			if(email == ""){
    				$("#register-email").removeClass("form-control is-valid")
    				$("#register-email").addClass("form-control is-invalid");
    				flagEmail=false;
    			}else{
    				$("#register-email").removeClass("form-control is-invalid")
    				$("#register-email").addClass("form-control is-valid");
    				flagEmail=true;
    			}
    		})
    		
    		/* 点击注册按钮 */
    		$("#regbtn").click(function(){
    			if(flagName&&flagPas&&flagPass&&flagTele&&flagEmail){
    				localStorage.setItem("name",name);
    				localStorage.setItem("passWord",passWord);
    				location="pages/login.jsp"
    			}else{
    				if(!flagName){
    					$("#register-username").addClass("form-control is-invalid");
    				}
    				if(!flagPas){
    					$("#register-password").addClass("form-control is-invalid");
    				}
    				if(!flagPass){
    					$("#register-passwords").addClass("form-control is-invalid");
    				}
    				if(!flagTele){
    					$("#register-telephone").addClass("form-control is-invalid")
    				}
    				if(!flagEmail){
    					$("#register-email").addClass("form-control is-invalid");
    				}
    			}
    		})
    	})
    </script>
    
    <!-- 注册提示 --> 
	<%
     	Object message = session.getAttribute("message");
     	if(message!=null && !message.equals("")){
	%>
      <script type="text/javascript">
          alert("<%=message%>");
      </script>
  	<%
  		session.setAttribute("message", null);
  	 } %>  
  	 
  </body>
  
</html>
