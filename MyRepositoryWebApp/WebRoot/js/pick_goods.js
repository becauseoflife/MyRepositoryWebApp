/**
 * 
 */

function getOrderInfo(obj){
	var order_id = obj.options[obj.selectedIndex].value;
	var url = "PickGoodsServlet?action=getOrderInfo&order_id=" + order_id;
	console.log(url);
	$.ajax({
		url: url,
		type: "GET",
		dataType: "json",
		async: false,
		success: function(res){
			console.log(res);
		},
		error: function(res){
			console.log("请求失败");
		}
		
	});
}