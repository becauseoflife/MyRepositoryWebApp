/*
 * 更新 form的 action
 */

$("#ItemHandleModal").on("show.bs.modal", function(event){
	console.log(event)
	var btnThis = $(event.relatedTarget); //触发事件的按钮
	var modal = $(this);  //当前模态框
	let index = btnThis.closest('tr').find('th').eq(0).text();//获取a标签所在行的某一列的内容,eq括号里面的数值表示列数
	console.log(index)
	
	// 更新的用户名
	let action = "admin/OrderHandleServlet?action=allocation&index=" + (index-1)
	console.log("action=" + action)
	document.getElementById("item_handle_form").action = action
})