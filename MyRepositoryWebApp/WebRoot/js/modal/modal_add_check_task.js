/*
 * 时间选择
 */
$(function () {
    $('#start-time').datetimepicker({
    	language:  'zh-cn',//语言
    	minDate: new Date(),
    	format:'YYYY-MM-DD HH:mm',
        buttons: {
            showToday: true,
            showClear: true,
            showClose: true
        },
        icons: {
            time: 'fa fa-clock',
            today: 'fa fa-calendar-check',
            clear: 'fa fa-trash-o',
            close: 'fa fa-times'
        },
        tooltips:{
            today: '今天',
            clear: '清除',
            close: '关闭',
            selectTime : '选择时间',
            selectMonth: '选择月份',
            prevMonth: '上个月',
            nextMonth: '下个月',
            selectYear: '选择年份',
            prevYear: '上一年',
            nextYear: '下一年',
            selectDecade: '选择时期',
            prevDecade: '上个年代',
            nextDecade: '下个年代',
            prevCentury: '上个世纪',
            nextCentury: '下个世纪',
            incrementHour: '增加一小时',
            pickHour: '选择小时',
            decrementHour:'减少一小时',
            incrementMinute: '增加一分钟',
            pickMinute: '选择分',
            decrementMinute:'减少一分钟',
            incrementSecond: '增加一秒',
            pickSecond: '选择秒',
            decrementSecond:'减少一秒'
        },//这会将tooltips每个图标上方的内容更改为自定义字符串。
        locale: 'zh-cn'
    	});
    
    $('#end-time').datetimepicker({
    	language:  'zh-cn',//语言
    	minDate: new Date(),
    	format:'YYYY-MM-DD HH:mm',
        buttons: {
            showToday: true,
            showClear: true,
            showClose: true
        },
        icons: {
            time: 'fa fa-clock',
            today: 'fa fa-calendar-check',
            clear: 'fa fa-trash-o',
            close: 'fa fa-times'
        },
        tooltips:{
            today: '今天',
            clear: '清除',
            close: '关闭',
            selectTime : '选择时间',
            selectMonth: '选择月份',
            prevMonth: '上个月',
            nextMonth: '下个月',
            selectYear: '选择年份',
            prevYear: '上一年',
            nextYear: '下一年',
            selectDecade: '选择时期',
            prevDecade: '上个年代',
            nextDecade: '下个年代',
            prevCentury: '上个世纪',
            nextCentury: '下个世纪',
            incrementHour: '增加一小时',
            pickHour: '选择小时',
            decrementHour:'减少一小时',
            incrementMinute: '增加一分钟',
            pickMinute: '选择分',
            decrementMinute:'减少一分钟',
            incrementSecond: '增加一秒',
            pickSecond: '选择秒',
            decrementSecond:'减少一秒'
        },//这会将tooltips每个图标上方的内容更改为自定义字符串。
        locale: 'zh-cn'
    	});
});

/*
 * 注册框
 */
$('#addCheckTaskModal').modal('hide');

/*
 * 新增用户点击按钮
 */
$("#add-check-task").click(function(){
	$("#start-time").removeClass("form-control is-invalid")
	$("#start-time").removeClass("form-control is-valid");
	
	$("#end-time").removeClass("form-control is-invalid")
	$("#end-time").removeClass("form-control is-valid");
	
	$('#addCheckTaskModal').modal('show')
})

/*
 * 隐藏
 */

$("#addCheckTaskModal").on("hide.bs.modal", function(e){
	document.getElementById("task-form").reset()
})

/*	
  * 输入框检查
  * 
  * 错误class  form-control is-invalid
  *	正确class  form-control is-valid
  **/
/*let formFlag = false;
$(function(){
	错误class  form-control is-invalid
	正确class  form-control is-valid

	let startTime = false;
	let endTime = false;
	
	let start, end;

	$("#start-time").change(function(){
		start = $("#start-time").val();
		if(start==""){
			$("#start-time").removeClass("form-control is-valid")
			$("#start-time").addClass("form-control is-invalid");
			startTime=false;
		}else{
			$("#start-time").removeClass("form-control is-invalid")
			$("#start-time").addClass("form-control is-valid");
			startTime=true;
		}
	})

	$("end-time").change(function(){
		end = $("#end-time").val();
		if(end == ""){
			$("#end-time").removeClass("form-control is-valid")
			$("#end-time").addClass("form-control is-invalid");
			endTime=false;
		}else{
			$("#end-time").removeClass("form-control is-invalid")
			$("#end-time").addClass("form-control is-valid");
			endTime=true;
		}
	})
	
	
	$("#add-task-btn").click(function(){
	if(startTime&&endTime){
		formFlag = true;
	}else{
		if(!startTime){
			$("#start-time").addClass("form-control is-invalid");
		}
		if(!endTime){
			$("#end-time").addClass("form-control is-invalid");
		}
	}
})
})

 点击新增按钮 



 * 提交检查函数
 
function checkAlltime(obj){
	return formFlag;
}
*/


