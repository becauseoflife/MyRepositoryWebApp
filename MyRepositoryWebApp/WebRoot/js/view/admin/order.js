/*
 * 获取拣货订单ID列表
 */

function getOrderId(){
	$.ajax({
		url: "admin/OrderSearchServlet?action=getSelectOption",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(res){
			console.log(res);
			if(res.length == 0)
			{
				$("#orderSelect").append("<option>无</option>");
			}
			else
			{
				$("#orderSelect").append("<option>--请选择--</option>");
				for(let i=0; i<res.length; i++)
				{
					$("#orderSelect").append("<option value="+ res[i].order_id +">" + res[i].order_id + "</option>");
				}
			}
		},
		error: function(res){
			console.log("请求错误");
			$("#orderSelect").append("<option>请求失败</option>");
		} 
	})
};


/*	
  * 输入框检查
  * 
  * 错误class  form-control is-invalid
  *	正确class  form-control is-valid
  **/

let flagPickGood = false;
let flagOrderSelect = false;	
$(function(){
	/* 拣货输入框 */
	let flagPickInput = false;
	let clothingId;
	$("#pickIdInput").change(function(){
		clothingId = $("#pickIdInput").val();
		if(clothingId==""||clothingId==null)
		{
			$("#pickIdInput").removeClass("form-control is-valid")
		$("#pickIdInput").addClass("form-control is-invalid");
		flagPickInput=false;
		}
		else
		{
			$("#pickIdInput").removeClass("form-control is-invalid")
		$("#pickIdInput").addClass("form-control  is-valid");
		flagPickInput=true;
		}
	})
	
	$("#pickBtn").click(function(){
		if(flagPickInput)
		{
			flagPickGood = true;
		}
		else
		{
			$("#pickIdInput").addClass("form-control is-invalid");
		}
	})
	
	/* 订单选择框 */
	let flagSelect = false;
	let option;
	$("#orderSelect").change(function(){
		option = $("#orderSelect").val();
		if(option=="--请选择--"||option=="无")
		{
			$("#orderSelect").removeClass("form-control is-valid")
			$("#orderSelect").addClass("form-control is-invalid");
			flagSelect=false;
		}
		else
		{
			$("#orderSelect").removeClass("form-control is-invalid")
			$("#orderSelect").addClass("form-control  is-valid");
			flagSelect=true;
		}
	})
	
	$("#lookBtn").click(function(){
		if(flagSelect)
		{
			flagOrderSelect = true;
		}
		else
		{
			$("#orderSelect").addClass("form-control is-invalid");
		}
	})
	
})


function pickSubmit(){
	return flagPickGood;
}

function checkSelectSubmit(){
	return flagOrderSelect;
}