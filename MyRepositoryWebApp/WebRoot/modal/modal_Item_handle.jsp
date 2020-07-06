<%@page import="com.mapper.UserInfo"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>

<!-- Modal -->
<div id="ItemHandleModal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">提示信息</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
       
      </div>
      <form action="admin/OrderHandleServlet?action=allocation" method="post" id="item_handle_form">
	      <div id="select" class="modal-body">
	      
       			<div class="">
					<div class="card text-center">
						<div class="card-header">
							选择分配人员
						</div>
					  	<div class="card-body">
					  	<%
					  		List<UserInfo> userList = (List<UserInfo>)session.getAttribute("userList");
					  		if(userList.size() != 0)
					  		{
					  	 %>
								<select name="select_username" id="userSelect" class="custom-select">
									
									<%
										for(UserInfo u : userList)
										{
									 %>
										
										<option value="<%=u.getUserName()%>"><%=u.getUserName()%></option>
									<%
										}
									 %>
								</select>
						<%
							}
							else
							{
						%>
								<div>用户数量为0，请先添加用户!</div>
						<%
							}
						 %>
						</div>
					</div>
				</div>
						
	      </div>
	      <div class="modal-footer">
	      	<button id="cancel-btn" type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
	        <button id="" type="submit" class="btn btn-primary" >分配</button>
	      </div>

      </form>
    </div>
  </div>
</div>

<script src="js/modal/modal_Item_handle.js"></script>

