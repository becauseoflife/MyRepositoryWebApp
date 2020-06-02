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
                    </div>
                  </form>
                  <br />
                  <small>没有账号?</small><a href="register.jsp" class="signup">&nbsp;注册</a>
                </div>
              </div>
            </div>
          </div>
          
        </div>
      </div>
    </div>
    
    <!-- Modal -->
	<div id="myModal" class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalCenterTitle">提示信息</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div id="message" class="modal-body">
	        
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" data-dismiss="modal">知道啦</button>
	      </div>
	    </div>
	  </div>
	</div>
	<!-- 登录提示 -->
	<script type="text/javascript">
    	$('#myModal').modal('hide');
    </script>
    <%
    	Object message = session.getAttribute("message");
    	if(message!=null && !message.equals("")){
     %>
      <script type="text/javascript">
      	$('#message').html("<%=message%>");
          $('#myModal').modal('show');
      </script>
	<%
		}
		session.removeAttribute("message");
	 %>
    <!-- JavaScript files-->
    <!-- Main File-->
    <script>
    	$(function(){
    		/*判断上次是否勾选记住密码和自动登录*/
    		var check1s=localStorage.getItem("check1");
    		var check2s=localStorage.getItem("check2");
    		var oldName=localStorage.getItem("userName");
    		var oldPass=localStorage.getItem("passWord");
    		if(check1s=="true"){
    			 $("#login-username").val(oldName);
    			 $("#login-password").val(oldPass);
    			 $("#check1").prop('checked',true);
    		}else{
    			 $("#login-username").val('');
    			 $("#login-password").val('');
    			 $("#check1").prop('checked',false);
    		}
    		if(check2s=="true"){
    			$("#check2").prop('checked',true);
    			$("#loginFrom").submit();
    			//location="https://www.baidu.com?userName="+oldName+"&passWord="+oldPass;//添加退出当前账号功能
    		}else{
    			$("#check2").prop('checked',false);
    		}
    		/*拿到刚刚注册的账号*/
    		/*if(localStorage.getItem("name")!=null){
    			$("#login-username").val(localStorage.getItem("name"));
    		}*/
    		/*登录*/
    		$("#login").click(function(){
    			var userName=$("#login-username").val();
    			var passWord=$("#login-password").val();
    			/*获取当前输入的账号密码*/
    			localStorage.setItem("userName",userName)
    			localStorage.setItem("passWord",passWord)
    			/*获取记住密码  自动登录的 checkbox的值*/
    			var check1 = $("#check1").prop('checked');
	    		var check2 = $('#check2').prop('checked');
	    		localStorage.setItem("check1",check1);
	    		localStorage.setItem("check2",check2);
    		})
    		
    		/*$("#check2").click(function(){
    			var flag=$('#check2').prop('checked');
    			if(flag){
    				var userName=$("#login-username").val();
	    			var passWord=$("#login-password").val();
	    			$.ajax({
	    				type:"post",
	    				url:"http://localhost:8080/powers/pow/regUsers",
	    				data:{"userName":userName,"passWord":passWord},
	    				async:true,
	    				success:function(res){
	    					alert(res);
	    				}
	    			});
    			}
    		})*/
    	})
    </script>

  </body>
  
</html>
