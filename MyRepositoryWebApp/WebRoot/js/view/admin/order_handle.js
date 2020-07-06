/*
 * 获取拣货订单ID列表
 */

/*function getUser(){
	$.ajax({
		url: "OrderHandleServlet?action=getUser",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(res){
			console.log(res);
			if(res.length == 0)
			{
				$("#userSelect").append("<option>无</option>");
			}
			else
			{
				$("#userSelect").append("<option>--请选择--</option>");
				for(let i=0; i<res.length; i++)
				{
					$("#userSelect").append("<option value="+ res[i].userName +">" + res[i].userName + "</option>");
				}
			}
		},
		error: function(res){
			console.log("请求错误");
			console.log(res);
			$("#userSelect").append("<option>请求失败</option>");
		} 
	})
};*/