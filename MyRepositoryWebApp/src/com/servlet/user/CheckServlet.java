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

	private String action; // �̵����Ķ���
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
			if(action.equals("getCheckTask"))	// ��ȡ�������
			{
				getCheckTask(request, response);
			}
			if(action.equals("start"))	// ��ʼ�̵�����
			{
				startCheckTask(request, response);
			}
			if (action.equals("query")) // ͨ���ֿ�λ�ò��ҷ�װ
			{
				queryClothingByLocation(request, response);
			}
			if (action.equals("update")) // �̵��װ����
			{
				updateClothingNumber(request, response);
			}
			if (action.equals("insert")) // �̵��װ����,δ���ڷ�װ���и���
			{
				insertClothingNumber(request, response);
			}
			if (action.equals("null")) // �̵��װ����,δ���ڷ�װ����ȷ��
			{
				nullClothingNumber(request, response);
			}
			if (action.equals("delete")) // ɾ���̵�����ĳ��
			{
				deleteFormCheckTable(request, response);
			}
			if (action.equals("exportExcel")) // �̵��񵼳�Excel
			{
				exportExcel(request, response);

			}
		}
	}

	private void nullClothingNumber(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ������Ϣ
		CheckTask task = (CheckTask) session.getAttribute("task");
		// ��ȡ�����µķ�װ
		ClothingInfo c = (ClothingInfo) session.getAttribute("clothingInfo");
		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable) session.getAttribute("checkTable");

		if (c != null && checkTable != null) {

			// ��ӯ���̿���ֵ
			int surplus = 0;
			int loss = 0;

			// �̵����
			CheckTaskRecord taskRecord = new CheckTaskRecord();
			taskRecord.setId(task.getId());
			taskRecord.setClothingID("��");
			taskRecord.setPosition(c.getShelves()+"-"+c.getLocation());
			taskRecord.setNumber(0);
			taskRecord.setCheckNum(0);
			taskRecord.setSurplus(surplus);
			taskRecord.setLoss(loss);
			taskRecord.setCheck_time(new Date());

			// �̵����¼
			checkTable.addCheck(taskRecord);
			
			// �����̵��¼
			CheckTaskRecord record = checkTaskService.findByIdAndPosition(task.getId(), taskRecord.getPosition());
			if(record != null)
			{
				checkTaskService.updateTaskRecord(taskRecord);
			}
			if(record == null)
			{
				checkTaskService.addTaskRecord(taskRecord);
			}
			
			
			// ������ɾ���������ظ�����
			session.removeAttribute("clothingInfo");

		}
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void insertClothingNumber(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		// ��ȡ��װID
		String clothingID = request.getParameter("clothingId");
		int number = Integer.parseInt(request.getParameter("number"));
		System.out.println(clothingID + " ������" + request.getParameter("number"));
		// ��ȡ�����µķ�װ
		ClothingInfo c = (ClothingInfo) session.getAttribute("clothingInfo");
		
		c.setClothingID(clothingID);
		c.setNumber(number);
		
		// ���²ֿ�
		warehouseService.updateSetIdAndNumber(c);
		
		session.setAttribute("message", "���³ɹ�!");
		

		// ��ȡ������Ϣ
		CheckTask task = (CheckTask) session.getAttribute("task");

		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable) session.getAttribute("checkTable");

		if (c != null && checkTable != null) {

			// ��ӯ���̿���ֵ
			int surplus = number;
			int loss = 0;

			// �̵����
			CheckTaskRecord taskRecord = new CheckTaskRecord();
			taskRecord.setId(task.getId());
			taskRecord.setClothingID(c.getClothingID());
			taskRecord.setPosition(c.getShelves()+"-"+c.getLocation());
			taskRecord.setNumber(0);
			taskRecord.setCheckNum(number);
			taskRecord.setSurplus(surplus);
			taskRecord.setLoss(loss);
			taskRecord.setCheck_time(new Date());

			// �̵����¼
			checkTable.addCheck(taskRecord);
			
			// �����̵��¼
			CheckTaskRecord record = checkTaskService.findByIdAndPosition(task.getId(), taskRecord.getPosition());
			if(record != null)
			{
				checkTaskService.updateTaskRecord(taskRecord);
			}
			if(record == null)
			{
				checkTaskService.addTaskRecord(taskRecord);
			}
			
			
			// ������ɾ���������ظ�����
			session.removeAttribute("clothingInfo");

		}
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void startCheckTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ����
		String id = request.getParameter("select_task");
		CheckTask task = checkTaskService.findTaskById(id);
		
		// ��ȡ��¼
		List<CheckTaskRecord> taskRecordsList = checkTaskService.findAllTaskRecordById(id);
		
		// �̵������Ϊ�գ��򴴽�
		CheckTable checkTable = new CheckTable();
		// �������
		for (CheckTaskRecord taskRecord : taskRecordsList) {
			checkTable.addCheck(taskRecord);
		}
		// ���̵��ŵ�session��
		session.setAttribute("checkTable", checkTable); 


		// ���񱣴浽session��
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
		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable) session.getAttribute("checkTable");

/*		if (checkTable == null) {
			session.setAttribute("message", "�ѵ�����");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}*/
		
		try {
			CheckTable exportTable = (CheckTable) checkTable.deepClone();

			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=check.xlsx");// Ҫ������ļ���
			response.setContentType("application/octet-stream; charset=utf-8");
	
			exportService.exportExcel("�̵㱨��", exportTable.getTitle(), exportTable.getDataSet(), response.getOutputStream(),
					ExportExcelUtil.EXCEl_FILE_2007);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*		// ���̵�������session��ɾ��
		session.removeAttribute("checkTable");*/
	}

	private void deleteFormCheckTable(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡɾ��������
		String row = request.getParameter("row");

		// ȡ��ɾ���Ķ���
		CheckTaskRecord taskRecord = (CheckTaskRecord) session.getAttribute(row);

		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable) session.getAttribute("checkTable");

		// ɾ��
		checkTable.deleteCheck(taskRecord);

		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void updateClothingNumber(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ������Ϣ
		CheckTask task = (CheckTask) session.getAttribute("task");
		// ��ȡ���µ�����
		int updateNum = Integer.parseInt(request.getParameter("updateNum"));
		// ��ȡ�����µķ�װ
		ClothingInfo c = (ClothingInfo) session.getAttribute("clothingInfo");
		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable) session.getAttribute("checkTable");

		if (c != null && checkTable != null) {

			// ��ӯ���̿���ֵ
			int surplus = 0;
			int loss = 0;

			if (c.getNumber() > updateNum) // �̿�
			{
				loss = Math.abs(c.getNumber() - updateNum);
			} else if (c.getNumber() < updateNum) // ��ӯ
			{
				surplus = Math.abs(c.getNumber() - updateNum);
			}

			// �̵����
			CheckTaskRecord taskRecord = new CheckTaskRecord();
			taskRecord.setId(task.getId());
			taskRecord.setClothingID(c.getClothingID());
			taskRecord.setPosition(c.getShelves()+"-"+c.getLocation());
			taskRecord.setNumber(c.getNumber());
			taskRecord.setCheckNum(updateNum);
			taskRecord.setSurplus(surplus);
			taskRecord.setLoss(loss);
			taskRecord.setCheck_time(new Date());

			// �̵����¼
			checkTable.addCheck(taskRecord);
			
			// �������ݿ��е�����
			c.setNumber(updateNum);
			warehouseService.updateSetNumber(c);
			
			// �����̵��¼
			CheckTaskRecord record = checkTaskService.findByIdAndPosition(task.getId(), taskRecord.getPosition());
			if(record != null)
			{
				checkTaskService.updateTaskRecord(taskRecord);
			}
			if(record == null)
			{
				checkTaskService.addTaskRecord(taskRecord);
			}
			
			
			// ������ɾ���������ظ�����
			session.removeAttribute("clothingInfo");

		}
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void queryClothingByLocation(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ�����λ����Ϣ
		CheckTask task = (CheckTask) session.getAttribute("task");
		
		String shelves = task.getShelves();
		String location = request.getParameter("location");
		System.out.println("λ����Ϣ��"+ shelves + "-" + location);


		// �Ӳֿ��л�ȡ������Ϣ
		ClothingInfo c = warehouseService.findByPosition(shelves, location);
		// �����ζ������session��
		session.setAttribute("clothingInfo", c);
		
		if (c != null && c.getClothingID() != null) 
		{
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		} 
		else if (c != null && (c.getClothingID() == null || c.getNumber()==0) )
		{
			session.setAttribute("messageTip", "λ��: <strong>" + c.getShelves() + "-" + c.getLocation() + "</strong>δ��ŷ���");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		} 
		else 
		{
			session.setAttribute("messageTip", "λ����������<br/>���������룡<br/><br/>��λ:  1-12 ");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}
	}

}
