 /*
  * 界面初始化数据
  */

function getEmptyPosition(){
	$.ajax({
		url: "user/PutOnGoodServlet?action=getEmptyPositon",
		type: "GET",
		dataType: "json",
		async: false,
		success: function(res){
			console.log(res);
			if(res.length == 0)
			{
				$("#emptyPosition").append("<option>无</option>");
				$("#put-on-input").addClass("hidden")
				$("#put-on-input").removeClass("show")
			}
			else
			{
				$("#put-on-input").removeClass("hidden")
				$("#put-on-input").addClass("show")
				
				$("#emptyPosition").append("<option>--请选择--</option>");
				for(let i=0; i<res.length; i++)
				{
					let position = res[i].shelves +"-"+ res[i].location;
					$("#emptyPosition").append("<option value="+ position +">" + position + "</option>");
				}
			}
		},
		error: function(res){
			console.log("请求错误");
			$("#orderSelect").append("<option>请求失败</option>");
			$("#put-on-input").addClass("hidden")
			$("#put-on-input").removeClass("show")
		} 
	})
};

/*	
  * 输入框检查
  * 
  * 错误class  form-control is-invalid
  *	正确class  form-control is-valid
  **/

let putOnSubmit = false;
let flagOrderSelect = false;
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
	
	/*选择框*/
	let flagSelect = false;
	let option;
	$("#emptyPosition").change(function(){
		option = $("#emptyPosition").val();
		if(option=="--请选择--"||option=="无")
		{
			$("#emptyPosition").removeClass("form-control is-valid")
			$("#emptyPosition").addClass("form-control is-invalid");
			flagSelect=false;
		}
		else
		{
			$("#emptyPosition").removeClass("form-control is-invalid")
			$("#emptyPosition").addClass("form-control  is-valid");
			flagSelect=true;
		}
	})
	
	$("#putOnBtn").click(function(){
		if(flagClothingId&&flagNumInput&&flagSelect)
		{
			putOnSubmit=true;
			flagOrderSelect=true;
		}
		else
		{
			if(!flagClothingId)
				$("#clothingIdInput").addClass("form-control is-invalid");
			if(!flagNumInput)
				$("#numInput").addClass("form-control is-invalid");
			if(!flagSelect)
				$("#emptyPosition").addClass("form-control is-invalid");
		}
	})
	
})

function checkPutOnInput(){
	return putOnSubmit&&flagOrderSelect;
}