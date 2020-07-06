 /*
 * 获取盘点任务
 */

function getCheckTasks(){
	$.ajax({
		url: "user/CheckServlet?action=getCheckTask",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(res){
			console.log(res);
			if(res.length == 0)
			{
				$("#check-task-select").append("<option>暂无任务</option>");
			}
			else
			{
				$("#check-task-select").append("<option>--请选择--</option>");
				for(let i=0; i<res.length; i++)
				{
					var str = "盘点货架: " + res[i].shelves + " 结束时间: " + formatDate(res[i].end_time)
					$("#check-task-select").append("<option value="+ res[i].id +">" + str + "</option>");
				}
			}
		},
		error: function(res){
			console.log("请求错误");
			$("#check-task-select").append("<option>请求失败</option>");
		} 
	})
};


//时间戳转换方法    date:时间戳数字
function formatDate(timestamp) {

var date = new Date(timestamp);

var YY = date.getFullYear() + '-';
var MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
var DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
var hh = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
var mm = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes());
var ss = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
return YY + MM + DD +" "+ hh + mm;
}



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
	let flagShelvesInput = true;
	let flagLocationInput = false;
	
	let shelves, location;
	
/*	$("#shelvesInput").change(function(){
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
	})*/
	
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
/*			if(!flagShelvesInput)
				$("#shelvesInput").addClass("form-control is-invalid");*/
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


/*	
 * 输入框检查
 * 
 * 错误class  form-control is-invalid
 *	正确class  form-control is-valid
 **/

let flagTask = false;	
$(function(){
	/* 订单选择框 */
	let flagSelect = false;
	let option;
	$("#check-task-select").change(function(){
		option = $("#check-task-select").val();
		if(option=="--请选择--"||option=="无")
		{
			$("#check-task-select").removeClass("form-control is-valid")
			$("#check-task-select").addClass("form-control is-invalid");
			flagSelect=false;
		}
		else
		{
			$("#check-task-select").removeClass("form-control is-invalid")
			$("#check-task-select").addClass("form-control  is-valid");
			flagSelect=true;
		}
	})
	
	$("#start-btn").click(function(){
		if(flagSelect)
		{
			flagTask = true;
		}
		else
		{
			$("#check-task-select").addClass("form-control is-invalid");
		}
	})
	
})

function checkTaskSelect()
{
	return flagTask
}




/*	
 * 输入框检查
 * 
 * 错误class  form-control is-invalid
 *	正确class  form-control is-valid
 **/

let putOnSubmit = false;
$(function(){
	let flagClothingId = false;
	let flagNumInput = false;
	let clothingId, num;
	$("#clothingIdInput").change(function(){
		clothingId = $("#clothingIdInput").val();
		if(clothingId==""||clothingId==null)
		{
			$("#clothingIdInput").removeClass("form-control is-valid")
			$("#clothingIdInput").addClass("form-control is-invalid");
			flagClothingId=false;
		}
		else
		{
			$("#clothingIdInput").removeClass("form-control is-invalid")
			$("#clothingIdInput").addClass("form-control  is-valid");
			flagClothingId=true;
		}
	})
	
	$("#numInput").change(function(){
		num = $("#numInput").val();
		if(num==""||num==null || num <=0)
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
	
	
	$("#putOnBtn").click(function(){
		if(flagClothingId&&flagNumInput)
		{
			putOnSubmit=true;
		}
		else
		{
			if(!flagClothingId)
				$("#clothingIdInput").addClass("form-control is-invalid");
			if(!flagNumInput)
				$("#numInput").addClass("form-control is-invalid");
		}
	})
	
})

function checkPutOnInput(){
	return putOnSubmit;
}