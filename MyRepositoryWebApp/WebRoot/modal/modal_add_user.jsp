<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>

<!-- Modal -->
<div id="addUserModal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">新增用户</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <form method="post" action="UserManagementServlet?action=addUser" class="form-validate" id="user-form" onsubmit="return checkAllAdd(this);">
	      <div class="modal-body" style="background-color: rgb(248, 249, 250);">
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
	      </div>
	      <div class="modal-footer">
	      	<button id="cancel-btn" type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
	        <button id="add-btn" type="submit" class="btn btn-primary" >添加</button>
	      </div>
		</form>
    </div>
  </div>
</div>

<script type="text/javascript" src="js/modal/modal_add_user.js"></script>


