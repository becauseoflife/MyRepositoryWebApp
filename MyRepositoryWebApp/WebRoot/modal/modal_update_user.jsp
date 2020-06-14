<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>

<!-- Modal -->
<div id="updateUserModal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="false">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">修改用户</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form method="post" action="UserManagementServlet?action=updateUser" class="form-validate" id="update-form" onsubmit="return checkAllUpdate(this);">
	      <div class="modal-body" style="background-color: rgb(248, 249, 250);">
	          <div class="form-group">
	          		<span>用户名:</span>
	            	<input id="update-username" class="input-material" type="text" name="updateUsername" placeholder="请输入用户名/姓名" >
			     		<div class="invalid-feedback">
			       			用户名必须在2~10位之间
			     		</div>
	          </div>
	          <div class="form-group">
	          		<span>密码:</span>
	             	<input id="update-password" class="input-material" type="text" name="updatePassword" placeholder="请输入密码"   >
	           			<div class="invalid-feedback">
	       					密码必须在6~10位之间
	     				</div>
	          </div>
	          <div class="form-group">
	          		<span>联系电话:</span>
	             	<input id="update-telephone" class="input-material" type="text" maxlength="11" name="updateTelephone" placeholder="请输入联系电话" >
					     <div class="invalid-feedback">
					       	请输入11位的电话号码
					     </div>
	          </div>
	          <div class="form-group">
	          		<span>电子邮件:</span>
	             	<input id="update-email" class="input-material" type="email" name="updateEmail" placeholder="请输入电子邮件" >
					     <div class="invalid-feedback">
					       	请输入电子邮件
					     </div>
	          </div>
	      </div>
	      <div class="modal-footer">
	      	<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
	        <button id="update-btn" type="submit" class="btn btn-primary" >修改</button>
	      </div>
		</form>
    </div>
  </div>
</div>

<script type="text/javascript" src="js/modal/modal_update_user.js"></script>