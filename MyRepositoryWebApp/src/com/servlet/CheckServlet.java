package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Check;
import com.entity.CheckTable;
import com.mapper.ClothingInfo;
import com.service.ExportService;
import com.service.WarehouseService;
import com.service.impl.ExportServiceImp;
import com.service.impl.WarehouseServiceImpl;
import com.util.ExportExcelUtil;

/**
 * Servlet implementation class CheckServet
 */
@WebServlet("/CheckServlet")
public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String action;		// 盘点界面的动作
	private ExportService service = new ExportServiceImp();
	private WarehouseService warehouseService = new WarehouseServiceImpl();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ExportService service = new ExportServiceImp();
		
		if(request.getParameter("action") != null)
		{
			this.action = request.getParameter("action");
			if(action.equals("query"))		// 通过仓库位置查找服装
			{
				queryClothingByLocation(request, response);
			}
			if (action.equals("update")) 	// 盘点服装功能
			{
				updateClothingNumber(request, response);
			}
			if(action.equals("delete"))		// 删除盘点表格中某项
			{
				deleteFormCheckTable(request, response);
			}
			if(action.equals("exportExcel"))	// 盘点表格导出Excel
			{
				exportExcel(request, response);

			}
		}
	}

	private void exportExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		if(checkTable == null)
		{
			session.setAttribute("message", "已导出！");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}
		

		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=check.xlsx");//要保存的文件名
		response.setContentType("application/octet-stream; charset=utf-8");
		

		service.exportExcel("盘盈和盘亏报表", checkTable.getTitle(), checkTable.getDataSet(), response.getOutputStream(), ExportExcelUtil.EXCEl_FILE_2007);

		// 将盘点表格对象从session中删除
		session.removeAttribute("checkTable");
	}

	private void deleteFormCheckTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取删除的行数
		String row = request.getParameter("row");
		
		// 取得删除的对象
		Check check = (Check)session.getAttribute(row);
			
		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		// 删除
		checkTable.deleteCheck(check);
		
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void updateClothingNumber(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取跟新的数量
		int updateNum = Integer.parseInt(request.getParameter("updateNum"));
		// 获取被跟新的服装
		ClothingInfo c = (ClothingInfo)session.getAttribute("clothingInfo");
		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		if(c!=null && checkTable!=null){

			c.setNumber(updateNum);
			// 更新数据库中的数量
			warehouseService.updateSetNumber(c);
			
			// 盘盈和盘亏的值
			int surplus = 0;
			int loss = 0;
	
			if(c.getNumber() > updateNum)	// 盘亏
			{
				loss = Math.abs(c.getNumber()-updateNum);
			}
			else if(c.getNumber() < updateNum)	// 盘盈
			{
				surplus = Math.abs(c.getNumber()-updateNum);
			}
			
			// 盘点对象
			Check check = new Check(
					c.getClothingID(),
					c.getShelves()+"-"+c.getLocation(),
					c.getNumber(),
					updateNum,
					surplus,
					loss);
			
			// 盘点表格记录
			checkTable.addCheck(check);
			
			// 将服饰删除，避免重复操作
			session.removeAttribute("clothingInfo");
			
		}	
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void queryClothingByLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取位置信息
		String shelves = request.getParameter("shelves");
		String location = request.getParameter("location");
 		//System.out.println("位置信息："+ shelves + "-" + location);
		
		// 盘点表格对象为空，则创建
		if(session.getAttribute("checkTable")== null)
		{
			CheckTable checkTable = new CheckTable();
			session.setAttribute("checkTable", checkTable);		// 将盘点表放到session中
		}
		
		// 从仓库中获取服饰信息
		ClothingInfo c = warehouseService.findByPosition(shelves, location);

		// 将服饰对象放入session中
		if(c!=null && c.getClothingID()!=null)
		{
			session.setAttribute("clothingInfo", c);
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}
		else if(c != null && c.getClothingID()==null)
		{
			session.setAttribute("message", "位置: <strong>"+c.getShelves()+"-"+c.getLocation()+"</strong>未存放服饰");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}
		else
		{
			session.setAttribute("message", "位置输入有误！<br/>请重新输入！<br/>例: <br/>货架:  A<br/>货位:  1 ");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}
	}


}
