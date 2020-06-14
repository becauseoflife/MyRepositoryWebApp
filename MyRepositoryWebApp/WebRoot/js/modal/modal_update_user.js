
/*
 * 注册框
 */
$('#updateUserModal').modal('hide');

/*
 * 新增用户点击按钮
 */
$("#update-btn").click(function(){
	$("#update-username").removeClass("form-control is-invalid")
	$("#update-username").removeClass("form-control is-valid");
	
	$("#update-password").removeClass("form-control is-invalid")
	$("#update-password").removeClass("form-control is-valid");
	
	$("#update-telephone").removeClass("form-control is-invalid")
	$("#update-telephone").removeClass("form-control is-valid");
	
	$("#update-email").removeClass("form-control is-invalid")
	$("#update-email").removeClass("form-control is-valid");
	
	$('#updateUserModal').modal('show')
})

let changeFlag = false // 是否修改标志

$("#updateUserModal").on("show.bs.modal", function(event){
	
	console.log(event)
	var btnThis = $(event.relatedTarget); //触发事件的按钮
	var modal = $(this);  //当前模态框
	let username = btnThis.closest('tr').find('td').eq(0).text();//获取a标签所在行的某一列的内容,eq括号里面的数值表示列数
	$("#update-username").val(username)
	let password = btnThis.closest('tr').find('td').eq(1).text();
	$("#update-password").val(password)
	$("#update-passwords").val(password)
	let telephone = btnThis.closest('tr').find('td').eq(2).text();
	$("#update-telephone").val(telephone)
	let email = btnThis.closest('tr').find('td').eq(3).text();
	$("#update-email").val(email)
	
	// 更新的用户名
	let action = "UserManagementServlet?action=updateUser&username=" + username
	console.log("action" + action)
	document.getElementById("update-form").action = action
	
	
	// 检查输入框
	checkInput()
})

/*
 * 隐藏
 */

$("#updateUserModal").on("hide.bs.modal", function(e){
	document.getElementById("update-form").reset()
})

/*	
  * 输入框检查
  * 
  * 错误class  form-control is-invalid
  *	正确class  form-control is-valid
  **/
let updateFormFlag = false;
function checkInput(){
	/*错误class  form-control is-invalid
	正确class  form-control is-valid*/
	let flagName = true;
	let flagPas = true;
	let flagTele = true;
	let flagEmail = true;
	
	let name, passWord, telephone, email

	/*验证用户名*/
	$("#update-username").change(function(){
		name=$("#update-username").val();
		if(name.length<2||name.length>10||name==""){
			$("#update-username").removeClass("form-control is-valid")
			$("#update-username").addClass("form-control is-invalid");
			flagName=false;
		}else{
			$("#update-username").removeClass("form-control is-invalid")
			$("#update-username").addClass("form-control is-valid");
			flagName=true;
			changeFlag=true
		}
	})
	/*验证密码*/
	$("#update-password").change(function(){
		passWord=$("#update-password").val();
		if(passWord.length<6||passWord.length>18||passWord==""){
			$("#update-password").removeClass("form-control is-valid")
			$("#update-password").addClass("form-control is-invalid");
			flagPas=false;
		}else{
			$("#update-password").removeClass("form-control is-invalid")
			$("#update-password").addClass("form-control is-valid");
			flagPas=true;
			changeFlag=true
		}
	})
	/* 验证联系号码 */
	$("#update-telephone").change(function(){
		telephone = $("#update-telephone").val();
		if(telephone.length != 11||telephone==""){
			$("#update-telephone").removeClass("form-control is-valid")
			$("#update-telephone").addClass("form-control is-invalid");
			flagTele=false;
		}else{
			$("#update-telephone").removeClass("form-control is-invalid")
			$("#update-telephone").addClass("form-control is-valid");
			flagTele=true;
			changeFlag=true
		}
	})
	/* 验证电子邮件 */
	$("#update-email").change(function(){
		email = $("#update-email").val();
		if(email == ""||email==null){
			$("#update-email").removeClass("form-control is-valid")
			$("#update-email").addClass("form-control is-invalid");
			flagEmail=false;
		}else{
			$("#update-email").removeClass("form-control is-invalid")
			$("#update-email").addClass("form-control is-valid");
			flagEmail=true;
			changeFlag=true
		}
	})
	
	
	$("#update-btn").click(function(){
	if(flagName&&flagPas&&flagTele&&flagEmail){
		updateFormFlag = true;
	}else{
		if(!flagName){
			$("#update-username").addClass("form-control is-invalid");
		}
		if(!flagPas){
			$("#update-password").addClass("form-control is-invalid");
		}
		if(!flagTele){
			$("#update-telephone").addClass("form-control is-invalid");
		}
		if(!flagEmail){
			$("#update-email").addClass("form-control is-invalid");
		}
	}
})
}


/*
 * 提交检查函数
 */
function checkAllUpdate(obj){
	if(!changeFlag)
	{
		alert("未修改")
		return changeFlag
	}
	return updateFormFlag;
}



