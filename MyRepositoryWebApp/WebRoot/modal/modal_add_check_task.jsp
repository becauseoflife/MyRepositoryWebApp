<%@page import="com.mapper.UserInfo"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>

<!-- Modal -->
<div id="addCheckTaskModal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">新增盘点任务</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form method="post" action="admin/CheckTaskServlet?action=addCheckTask" class="form-validate" id="task-form">
	      <div class="modal-body" style="background-color: rgb(248, 249, 250);">
	          <div class="form-group">
	          		<span>开始时间:</span>
	            	<input id="start-time" class="input-material datetimepicker-input" data-toggle="datetimepicker" data-target="#start-time" type="text" name="start_time" placeholder="请选择开始时间" >
			     		<div class="invalid-feedback">
			       			请选择开始时间
			     		</div>
	          </div>
	          <div class="form-group">
	          		<span>结束时间:</span>
	            	<input id="end-time" class="input-material datetimepicker-input" data-toggle="datetimepicker" data-target="#end-time" type="text" name="end_time" placeholder="请选择结束时间" >
			     		<div class="invalid-feedback">
			       			请选择结束时间
			     		</div>
	          </div>
	          <div class="form-group">
	          		<span>指定货架:</span>
					<select name="select_shelves" id="" class="custom-select">
						<option value="A">A</option>
						<option value="B">B</option>
						<option value="C">C</option>
						<option value="D">D</option>
					</select>
	          </div>
	          <div class="form-group">
					<span>操作人员:</span>
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
	      <div class="modal-footer">
	      	<button id="cancel-btn" type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
	        <button id="add-task-btn" type="submit" class="btn btn-primary" >添加</button>
	      </div>
		</form>
    </div>
  </div>
</div>

<script type="text/javascript" src="js/modal/modal_add_check_task.js"></script>


