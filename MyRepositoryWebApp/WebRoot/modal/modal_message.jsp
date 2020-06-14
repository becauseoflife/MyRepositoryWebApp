<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>

<!-- Modal -->
<div id="myMessageModal" class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">提示信息</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div id="message" class="modal-body">
        
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">知道啦</button>
      </div>
    </div>
  </div>
</div>



<script src="js/modal/modal_message.js"></script>
<%
	if(session.getAttribute("message") != null)
	{
   		Object message = session.getAttribute("message");
   		if(message!=null && !message.equals(""))
   		{
	%>
	     	<script type="text/javascript">
	     		$('#message').html("<%=message%>");
	         	$('#myMessageModal').modal('show');
	     	</script>
	<%
		}
		session.removeAttribute("message");
	}
%>


