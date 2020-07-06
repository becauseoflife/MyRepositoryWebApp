/*
 * 获取拣货订单ID列表
 */

function getAllOrderId(){
	$.ajax({
		url: "admin/ReturnOrderServlet?action=getSelectOption",
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