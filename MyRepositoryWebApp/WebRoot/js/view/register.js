
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
	
	/* 点击注册按钮 */
	$("#regbtn").click(function(){
		if(flagName&&flagPas&&flagPass&&flagTele&&flagEmail){
			formFlag = true;
			localStorage.setItem("userName",name);
			localStorage.setItem("passWord",passWord);
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

function checkAll(obj){
	return formFlag;
}