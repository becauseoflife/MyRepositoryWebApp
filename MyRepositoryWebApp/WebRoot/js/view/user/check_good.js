 


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

let searchSubmit = false;
let updateSubmit = false;

$(function(){
	let flagShelvesInput = false;
	let flagLocationInput = false;
	
	let shelves, location;
	
	$("#shelvesInput").change(function(){
		shelves = $("#shelvesInput").val();
		if(shelves==""||shelves==null)
		{
			$("#shelvesInput").removeClass("form-control is-valid")
			$("#shelvesInput").addClass("form-control is-invalid");
			flagShelvesInput=false;
		}
		else
		{
			$("#shelvesInput").removeClass("form-control is-invalid")
			$("#shelvesInput").addClass("form-control  is-valid");
			flagShelvesInput=true;
		}
	})
	
	$("#locationInput").change(function(){
		location = $("#locationInput").val();
		if(location==""||location==null|| location<=0 || location>12)
		{
			$("#locationInput").removeClass("form-control is-valid")
			$("#locationInput").addClass("form-control is-invalid");
			flagLocationInput=false;
		}
		else
		{
			$("#locationInput").removeClass("form-control is-invalid")
			$("#locationInput").addClass("form-control  is-valid");
			flagLocationInput=true;
		}
	})
	
	$("#searchBtn").click(function(){
		if(flagShelvesInput&&flagLocationInput)
		{
			searchSubmit=true;
		}
		else
		{
			if(!flagShelvesInput)
				$("#shelvesInput").addClass("form-control is-invalid");
			if(!flagLocationInput)
				$("#locationInput").addClass("form-control is-invalid");
		}
	})
	
	let flagNumInput=false;
	
	let numInput;
	$("#numInput").change(function(){
		numInput = $("#numInput").val();
		if(numInput==""||numInput==null||numInput<0)
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
	
	$("#updateBtn").click(function(){
		if(flagNumInput)
			updateSubmit=true;
		else
			$("#numInput").addClass("form-control is-invalid");
	})
})

function checkSearchInput(){
	return searchSubmit;
}

function checkUpdateInput(){
	return updateSubmit;
}