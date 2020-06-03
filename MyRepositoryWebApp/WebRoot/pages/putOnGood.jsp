<%@page import="com.entity.User"%>
<%@page import="com.entity.Check"%>
<%@page import="com.entity.CheckTable"%>
<%@page import="com.entity.ClothingInfo"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>盘点</title>
    
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    
    <link href="css/style.check.css" rel="stylesheet" />
    <link href="css/styles.home.css" rel="stylesheet" />
    <link href="css/myStyles.home.css" rel="stylesheet" />
   	<!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/bootstrap-4.5.0-dist/css/bootstrap.min.css">
    
    <script src="js/all.min.js" crossorigin="anonymous"></script>
    <script type="text/javascript" language="javascript">
    	function getEmptyPosition(){
			$.ajax({
				url: "PutOnGood?action=getEmptyPositon",
				type: "GET",
				dataType: "json",
				async: false,
				success: function(res){
					console.log(res);
					if(res.length == 0)
					{
						$("#emptyPosition").append("<option>无</option>");
					}
					else
					{
						$("#emptyPosition").append("<option>--请选择--</option>");
						for(let i=0; i<res.length; i++)
						{
							let position = res[i].shelves +"-"+ res[i].location;
							$("#emptyPosition").append("<option value="+ position +">" + position + "</option>");
						}
					}
				},
				error: function(res){
					console.log("请求错误");
					$("#orderSelect").append("<option>请求失败</option>");
				} 
			})
		};
    </script>
  </head>
  
  <body class="sb-nav-fixed" onload="return getEmptyPosition();">
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
                            <a id="my-nav-link" class="nav-link" href="pages/putOnGood.jsp">
                        		<div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            	<text>货品上架</text>
                        	</a>
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
				<form method="post" action="PutOnGood?action=putOn" onsubmit="return checkPutOnInput();">
					<h1 class="mt-4">货品上架</h1>
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item active">货品上架</li>
					</ol>
					
					<div class="card mb-4">
						<div class="card text-center">
							<div class="card-header">
								请选择上架位置
							</div>
						  	<div class="card-body">
								<select name="select_position" id="emptyPosition" class="custom-select">
								</select>
							</div>
						</div>
					</div>

				<%
					Object sign = session.getAttribute("hasEmptyPosition");
					if(sign!=null && (Boolean)sign){
				 %>

					<div class="card mb-4">
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
									<button id="putOnBtn" type="submit" class="btn btn-primary mySearchBtn">上架</button>
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
   
    <!-- JQuery -->
    <script type="text/javascript" src="resources/jquery-3.4.1/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="resources/bootstrap-4.5.0-dist/js/bootstrap.min.js"></script>
    
    <script src="resources/bootstrap-4.5.0-dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="js/scripts.js"></script>
    <script type="text/javascript">
      /*错误class  form-control is-invalid
  		正确class  form-control is-valid*/
  		let putOnSubmit = false;
  		let flagOrderSelect = false;
  		$(function(){
  			let flagClothingId = false;
  			let flagNumInput = false;
  			let clothingId, num;
  			$("#clothingIdInput").change(function(){
  				clothingId = $("#clothingIdInput").val();
  				if(clothingId==""||clothingId==null)
  				{
  					$("#clothingIdInput").removeClass("form-control is-valid")
    				$("#clothingIdInput").addClass("form-control is-invalid");
    				flagClothingId=false;
  				}
  				else
  				{
  					$("#clothingIdInput").removeClass("form-control is-invalid")
    				$("#clothingIdInput").addClass("form-control  is-valid");
    				flagClothingId=true;
  				}
  			})
  			
  			$("#numInput").change(function(){
  				num = $("#numInput").val();
  				if(num==""||num==null || num <=0)
  				{
  					$("#numInput").removeClass("form-control is-valid")
    				$("#numInput").addClass("form-control is-invalid");
    				flagNumInput=false;
  				}
  				else
  				{
  					$("#numInput").removeClass("form-control is-invalid")
    				$("#numInput").addClass("form-control  is-valid");
    				flagNumInput=true;
  				}
  			})
  			
  			/*选择框*/
  			let flagSelect = false;
  			let option;
  			$("#emptyPosition").change(function(){
  				option = $("#emptyPosition").val();
  				if(option=="--请选择--"||option=="无")
  				{
  					$("#emptyPosition").removeClass("form-control is-valid")
    				$("#emptyPosition").addClass("form-control is-invalid");
    				flagSelect=false;
  				}
  				else
  				{
  					$("#emptyPosition").removeClass("form-control is-invalid")
    				$("#emptyPosition").addClass("form-control  is-valid");
    				flagSelect=true;
  				}
  			})
  			
  			$("#putOnBtn").click(function(){
  				if(flagClothingId&&flagNumInput&&flagSelect)
  				{
  					putOnSubmit=true;
  					flagOrderSelect=true;
  				}
  				else
  				{
  					if(!flagClothingId)
  						$("#clothingIdInput").addClass("form-control is-invalid");
  					if(!flagNumInput)
  						$("#numInput").addClass("form-control is-invalid");
  					if(!flagSelect)
  						$("#emptyPosition").addClass("form-control is-invalid");
  				}
  			})
  			
  		})
  		
  		function checkPutOnInput(){
  			return putOnSubmit&&flagOrderSelect;
  		}
    </script>
    <!-- 模态弹框信息 -->
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
  </body>
</html>
