/*
 * 获取拣货订单ID列表
 */

function getReturnOrderId(){
	$.ajax({
		url: "user/ReturnHandleServlet?action=getSelectOption",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(res){
			console.log(res);
			if(res.length == 0)
			{
				$("#re-order-select").append("<option>无</option>");
			}
			else
			{
				$("#re-order-selectt").append("<option>--请选择--</option>");
				for(let i=0; i<res.length; i++)
				{
					$("#re-order-select").append("<option value="+ res[i].order_id +">" + res[i].order_id + "</option>");
				}
			}
		},
		error: function(res){
			console.log("请求错误");
			$("#re-order-select").append("<option>请求失败</option>");
		} 
	})
};