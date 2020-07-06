<%@page import="com.mapper.UserInfo"%>
<%@page import="com.entity.Check"%>
<%@page import="com.entity.CheckTable"%>
<%@page import="com.mapper.ClothingInfo"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>


<!-- Modal -->
<div id="re-order-modal" class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">提示信息</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      
    <form action="admin/ReturnHandleServlet?action=puton" method="post">
      <div id="" class="modal-body">
        <div class="text-center" style="margin-bottom: 10px; color:red;">
        	<strong >仓库不存在该商品！请选择放置货架位置进行退货！</strong>
        </div>
 
        	<div class="container-fluid">
				<%
				if(session.getAttribute("allEmptyPosition")!=null)
				{
					List<ClothingInfo> emptyPositionList = (List<ClothingInfo>)session.getAttribute("allEmptyPosition");
					if(emptyPositionList.size()!=0)
					{
				 %>
						<div class="">
							<div class="card text-center">
								<div class="card-header">
									请选择上架位置
								</div>
							  	<div class="card-body">
									<select name="position" id="emptyPosition" class="custom-select">
									<%
										for(ClothingInfo c:emptyPositionList)
										{
											String position = c.getShelves() + "-" + c.getLocation();
									 %>
											<option value="<%=position%>"><%=position%></option>
									<%
										}
									 %>
									</select>
								</div>
							</div>
						</div>
				<%
					}
				}
				else
				{
				 %>	
				 	<div class="">
						<div class="card text-center">
							<div class="card-header">
								提示信息
							</div>
						  	<div class="card-body">
								仓库没有空位置		
							</div>
						</div>
					</div>
				 <%
				 }
				  %>
			</div>
        
      </div>

	      <div class="modal-footer">
	        <button type="submit" class="btn btn-primary">确认</button>
	      </div>
      </form>
    </div>
  </div>
</div>
