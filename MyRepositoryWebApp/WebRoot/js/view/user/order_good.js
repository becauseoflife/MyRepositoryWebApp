
function delcfm(){
 	if(!window.confirm("确认要删除？")){
 		window.event.returnValue = false;
 	}
 }
/*	
  * 输入框检查
  * 
  * 错误class  form-control is-invalid
  *	正确class  form-control is-valid
  **/

let addSubmitFlag = false;
$(function(){
	let flagClothingIdInput = false;
	let flagnumInput = false;
	
	let clothingId, num;
	
	/* 服饰ID输入框 */
	$("#clothingIdInput").change(function(){
		clothingId = $("#clothingIdInput").val();
		if(clothingId==""||clothingId==null)
		{
			$("#clothingIdInput").removeClass("form-control is-valid")
			$("#clothingIdInput").addClass("form-control is-invalid");
			flagClothingIdInput=false;
		}
		else
		{
		  	$("#clothingIdInput").removeClass("form-control is-invalid")
			$("#clothingIdInput").addClass("form-control  is-valid");
			flagClothingIdInput=true;
		}
	})
	
	$("#numInput").change(function(){
		num = $("#numInput").val();
		if(num==""||num==null || num<=0)
		{
			$("#numInput").removeClass("form-control is-valid")
			$("#numInput").addClass("form-control is-invalid");
			flagnumInput=false;
		}
		else
		{
			$("#numInput").removeClass("form-control is-invalid")
			$("#numInput").addClass("form-control  is-valid");
			flagnumInput=true;
		}
	})
	
	$("#addBtn").click(function(){
		if(flagClothingIdInput&&flagnumInput)
		{
			addSubmitFlag=true;
		}
		else
		{
			if(!flagClothingIdInput)
				$("#clothingIdInput").addClass("form-control is-invalid");
			if(!flagnumInput)
				$("#numInput").addClass("form-control is-invalid");
		}
	})
})

function checkInput(){
	return addSubmitFlag;
}