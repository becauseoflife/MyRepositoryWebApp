
/*
 * 注册框
 */
$('#addUserModal').modal('hide');

/*
 * 新增用户点击按钮
 */
$("#add-user").click(function(){
	$("#register-username").removeClass("form-control is-invalid")
	$("#register-username").removeClass("form-control is-valid");
	$("#register-username").val(null);
	
	$("#register-password").removeClass("form-control is-invalid")
	$("#register-password").removeClass("form-control is-valid");
	$("#register-password").val(null)
	
	$("#register-passwords").removeClass("form-control is-invalid")
	$("#register-passwords").removeClass("form-control is-valid");
	$("#register-passwords").val(null)
	
	$("#register-telephone").removeClass("form-control is-invalid")
	$("#register-telephone").removeClass("form-control is-valid");
	$("#register-telephone").val(null)
	
	$("#register-email").removeClass("form-control is-invalid")
	$("#register-email").removeClass("form-control is-valid");
	$("#register-email").val(null)
	
	$('#addUserModal').modal('show')
})

/*
 * 隐藏
 */

$("#addUserModal").on("hide.bs.modal", function(e){
	document.getElementById("user-form").reset()
})

/*	
  * 输入框检查
  * 
  * 错误class  form-control is-invalid
  *	正确class  form-control is-valid
  **/
let formFlag = false;
$(function(){
	/*错误class  form-control is-invalid
	正确class  form-control is-valid*/

	let flagName = false;
	let flagPas = false;
	let flagPass = false;
	let flagTele = false;
	let flagEmail = false;
	
	let name,passWord,passWords,telephone, email;
	/*验证用户名*/
	$("#register-username").change(function(){
		name=$("#register-username").val();
		if(name.length<2||name.length>10||name==""){
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
		if(passWord.length<6||passWord.length>18||passWord==""){
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
		if((passWord!=passWords)||(passWords.length<6||passWords.length>18)||passWords==""){
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
		if(telephone.length != 11||telephone==""){
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
		if(email == ""||email==null){
			$("#register-email").removeClass("form-control is-valid")
			$("#register-email").addClass("form-control is-invalid");
			flagEmail=false;
		}else{
			$("#register-email").removeClass("form-control is-invalid")
			$("#register-email").addClass("form-control is-valid");
			flagEmail=true;
		}
	})
	
	
	$("#add-btn").click(function(){
	if(flagName&&flagPas&&flagPass&&flagTele&&flagEmail){
		formFlag = true;
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
			$("#register-telephone").addClass("form-control is-invalid");
		}
		if(!flagEmail){
			$("#register-email").addClass("form-control is-invalid");
		}
	}
})
})

/* 点击新增按钮 */




/*
 * 提交检查函数
 */
function checkAllAdd(obj){
	return formFlag;
}


/*function getUser(){
	$.ajax({
		url: "UserManagementServlet?action=getUser",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(res){
			console.log(res)
			if(res.length != 0)
			{
				let row = 1; 
				for(let i=0; i<res.length; i++)
				{
					let action = "<a href=\"\"><image src=\"images\\edit.png \"/></a><a href=\"\"><image src=\"images\\delete.png \"/></a>"
					$("#tbody").append(
						"<tr>" +
						"<th scope=\"row\">" + row++ + "</th>" +
						"<td>" + res[i].userName + "</td>" +
						"<td>" + res[i].password + "</td>" +
						"<td>" + res[i].telephone + "</td>" +
						"<td>" + res[i].email + "</td>" +
						"<td>" + action + "</td>" +
						"</tr>"
					)
				}
				
			}
		},
		error: function(res){
			console.log("error: " + res)
		}
	})
};*/


