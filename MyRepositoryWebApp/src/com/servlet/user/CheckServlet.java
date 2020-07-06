package com.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.entity.Check;
import com.entity.CheckTable;
import com.mapper.CheckTask;
import com.mapper.CheckTaskRecord;
import com.mapper.ClothingInfo;
import com.mapper.UserInfo;
import com.service.CheckTaskService;
import com.service.ExportService;
import com.service.WarehouseService;
import com.service.impl.CheckTaskServiceImpl;
import com.service.impl.ExportServiceImp;
import com.service.impl.WarehouseServiceImpl;
import com.util.ExportExcelUtil;

/**
 * Servlet implementation class CheckServet
 */
@WebServlet("/user/CheckServlet")
public class CheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String action; // 盘点界面的动作
	private ExportService exportService = new ExportServiceImp();
	private WarehouseService warehouseService = new WarehouseServiceImpl();
	private CheckTaskService checkTaskService = new CheckTaskServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ExportService service = new ExportServiceImp();

		if (request.getParameter("action") != null) {
			this.action = request.getParameter("action");
			if(action.equals("getCheckTask"))	// 获取拣货任务
			{
				getCheckTask(request, response);
			}
			if(action.equals("start"))	// 开始盘点任务
			{
				startCheckTask(request, response);
			}
			if (action.equals("query")) // 通过仓库位置查找服装
			{
				queryClothingByLocation(request, response);
			}
			if (action.equals("update")) // 盘点服装功能
			{
				updateClothingNumber(request, response);
			}
			if (action.equals("insert")) // 盘点服装功能,未存在服装进行更新
			{
				insertClothingNumber(request, response);
			}
			if (action.equals("null")) // 盘点服装功能,未存在服装进行确认
			{
				nullClothingNumber(request, response);
			}
			if (action.equals("delete")) // 删除盘点表格中某项
			{
				deleteFormCheckTable(request, response);
			}
			if (action.equals("exportExcel")) // 盘点表格导出Excel
			{
				exportExcel(request, response);

			}
		}
	}

	private void nullClothingNumber(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取任务信息
		CheckTask task = (CheckTask) session.getAttribute("task");
		// 获取被更新的服装
		ClothingInfo c = (ClothingInfo) session.getAttribute("clothingInfo");
		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable) session.getAttribute("checkTable");

		if (c != null && checkTable != null) {

			// 盘盈和盘亏的值
			int surplus = 0;
			int loss = 0;

			// 盘点对象
			CheckTaskRecord taskRecord = new CheckTaskRecord();
			taskRecord.setId(task.getId());
			taskRecord.setClothingID("无");
			taskRecord.setPosition(c.getShelves()+"-"+c.getLocation());
			taskRecord.setNumber(0);
			taskRecord.setCheckNum(0);
			taskRecord.setSurplus(surplus);
			taskRecord.setLoss(loss);
			taskRecord.setCheck_time(new Date());

			// 盘点表格记录
			checkTable.addCheck(taskRecord);
			
			// 更新盘点记录
			CheckTaskRecord record = checkTaskService.findByIdAndPosition(task.getId(), taskRecord.getPosition());
			if(record != null)
			{
				checkTaskService.updateTaskRecord(taskRecord);
			}
			if(record == null)
			{
				checkTaskService.addTaskRecord(taskRecord);
			}
			
			
			// 将服饰删除，避免重复操作
			session.removeAttribute("clothingInfo");

		}
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void insertClothingNumber(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		// 获取服装ID
		String clothingID = request.getParameter("clothingId");
		int number = Integer.parseInt(request.getParameter("number"));
		System.out.println(clothingID + " 数量：" + request.getParameter("number"));
		// 获取被更新的服装
		ClothingInfo c = (ClothingInfo) session.getAttribute("clothingInfo");
		
		c.setClothingID(clothingID);
		c.setNumber(number);
		
		// 更新仓库
		warehouseService.updateSetIdAndNumber(c);
		
		session.setAttribute("message", "更新成功!");
		

		// 获取任务信息
		CheckTask task = (CheckTask) session.getAttribute("task");

		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable) session.getAttribute("checkTable");

		if (c != null && checkTable != null) {

			// 盘盈和盘亏的值
			int surplus = number;
			int loss = 0;

			// 盘点对象
			CheckTaskRecord taskRecord = new CheckTaskRecord();
			taskRecord.setId(task.getId());
			taskRecord.setClothingID(c.getClothingID());
			taskRecord.setPosition(c.getShelves()+"-"+c.getLocation());
			taskRecord.setNumber(0);
			taskRecord.setCheckNum(number);
			taskRecord.setSurplus(surplus);
			taskRecord.setLoss(loss);
			taskRecord.setCheck_time(new Date());

			// 盘点表格记录
			checkTable.addCheck(taskRecord);
			
			// 更新盘点记录
			CheckTaskRecord record = checkTaskService.findByIdAndPosition(task.getId(), taskRecord.getPosition());
			if(record != null)
			{
				checkTaskService.updateTaskRecord(taskRecord);
			}
			if(record == null)
			{
				checkTaskService.addTaskRecord(taskRecord);
			}
			
			
			// 将服饰删除，避免重复操作
			session.removeAttribute("clothingInfo");

		}
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void startCheckTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取任务
		String id = request.getParameter("select_task");
		CheckTask task = checkTaskService.findTaskById(id);
		
		// 获取记录
		List<CheckTaskRecord> taskRecordsList = checkTaskService.findAllTaskRecordById(id);
		
		// 盘点表格对象为空，则创建
		CheckTable checkTable = new CheckTable();
		// 放入表中
		for (CheckTaskRecord taskRecord : taskRecordsList) {
			checkTable.addCheck(taskRecord);
		}
		// 将盘点表放到session中
		session.setAttribute("checkTable", checkTable); 


		// 任务保存到session中
		session.setAttribute("task", task);
		
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void getCheckTask(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("user");
		
		
		List<CheckTask> taskList = checkTaskService.findAllTaskIsValid(userInfo.getUserName());
		
		String jsonStr = JSON.toJSONString(taskList);
		//System.out.println(jsonStr);

		PrintWriter writer = response.getWriter();
		writer.write(jsonStr);
		
	}

	private void exportExcel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable) session.getAttribute("checkTable");

/*		if (checkTable == null) {
			session.setAttribute("message", "已导出！");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}*/
		
		try {
			CheckTable exportTable = (CheckTable) checkTable.deepClone();

			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=check.xlsx");// 要保存的文件名
			response.setContentType("application/octet-stream; charset=utf-8");
	
			exportService.exportExcel("盘点报表", exportTable.getTitle(), exportTable.getDataSet(), response.getOutputStream(),
					ExportExcelUtil.EXCEl_FILE_2007);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		// 将盘点表格对象从session中删除
		session.removeAttribute("checkTable");*/
	}

	private void deleteFormCheckTable(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取删除的行数
		String row = request.getParameter("row");

		// 取得删除的对象
		CheckTaskRecord taskRecord = (CheckTaskRecord) session.getAttribute(row);

		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable) session.getAttribute("checkTable");

		// 删除
		checkTable.deleteCheck(taskRecord);

		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void updateClothingNumber(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取任务信息
		CheckTask task = (CheckTask) session.getAttribute("task");
		// 获取更新的数量
		int updateNum = Integer.parseInt(request.getParameter("updateNum"));
		// 获取被更新的服装
		ClothingInfo c = (ClothingInfo) session.getAttribute("clothingInfo");
		// 获取盘点表格对象
		CheckTable checkTable = (CheckTable) session.getAttribute("checkTable");

		if (c != null && checkTable != null) {

			// 盘盈和盘亏的值
			int surplus = 0;
			int loss = 0;

			if (c.getNumber() > updateNum) // 盘亏
			{
				loss = Math.abs(c.getNumber() - updateNum);
			} else if (c.getNumber() < updateNum) // 盘盈
			{
				surplus = Math.abs(c.getNumber() - updateNum);
			}

			// 盘点对象
			CheckTaskRecord taskRecord = new CheckTaskRecord();
			taskRecord.setId(task.getId());
			taskRecord.setClothingID(c.getClothingID());
			taskRecord.setPosition(c.getShelves()+"-"+c.getLocation());
			taskRecord.setNumber(c.getNumber());
			taskRecord.setCheckNum(updateNum);
			taskRecord.setSurplus(surplus);
			taskRecord.setLoss(loss);
			taskRecord.setCheck_time(new Date());

			// 盘点表格记录
			checkTable.addCheck(taskRecord);
			
			// 更新数据库中的数量
			c.setNumber(updateNum);
			warehouseService.updateSetNumber(c);
			
			// 更新盘点记录
			CheckTaskRecord record = checkTaskService.findByIdAndPosition(task.getId(), taskRecord.getPosition());
			if(record != null)
			{
				checkTaskService.updateTaskRecord(taskRecord);
			}
			if(record == null)
			{
				checkTaskService.addTaskRecord(taskRecord);
			}
			
			
			// 将服饰删除，避免重复操作
			session.removeAttribute("clothingInfo");

		}
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void queryClothingByLocation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获取任务和位置信息
		CheckTask task = (CheckTask) session.getAttribute("task");
		
		String shelves = task.getShelves();
		String location = request.getParameter("location");
		System.out.println("位置信息："+ shelves + "-" + location);


		// 从仓库中获取服饰信息
		ClothingInfo c = warehouseService.findByPosition(shelves, location);
		// 将服饰对象放入session中
		session.setAttribute("clothingInfo", c);
		
		if (c != null && c.getClothingID() != null) 
		{
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		} 
		else if (c != null && (c.getClothingID() == null || c.getNumber()==0) )
		{
			session.setAttribute("messageTip", "位置: <strong>" + c.getShelves() + "-" + c.getLocation() + "</strong>未存放服饰");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		} 
		else 
		{
			session.setAttribute("messageTip", "位置输入有误！<br/>请重新输入！<br/><br/>货位:  1-12 ");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}
	}

}
