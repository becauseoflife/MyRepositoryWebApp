/*	
  * 输入框检查
  * 
  * 错误class  form-control is-invalid
  *	正确class  form-control is-valid
  **/

let searchFlag = false;
$(function(){
	let flagInput = false;
	let clothingId;
	
	$("#clothingIdInput").change(function()
	{
		clothingId=$("#clothingIdInput").val();
		if(clothingId==""||clothingId==null)
		{
			$("#clothingIdInput").removeClass("form-control is-valid")
			$("#clothingIdInput").addClass("form-control is-invalid");
			flagInput=false;
		}
		else
		{
			$("#clothingIdInput").removeClass("form-control is-invalid")
			$("#clothingIdInput").addClass("form-control  is-valid");
			flagInput=true;
		}
	})
	$("#searchBtn").click(function(){
		if(flagInput)
		{
			searchFlag=true;
		}
		else
		{
			$("#clothingIdInput").addClass("form-control is-invalid");
		}	
	})
})

function checkSearchInput(obj)
{
	return searchFlag;
}