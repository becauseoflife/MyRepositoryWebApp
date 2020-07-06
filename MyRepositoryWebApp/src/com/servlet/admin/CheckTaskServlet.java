package com.servlet.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entity.CheckTable;
import com.entity.CheckTaskInfo;
import com.mapper.CheckTask;
import com.mapper.CheckTaskRecord;
import com.service.CheckTaskService;
import com.service.ExportService;
import com.service.impl.CheckTaskServiceImpl;
import com.service.impl.ExportServiceImp;
import com.util.ExportExcelUtil;
 
/**
 * Servlet implementation class CheckTaskServlet
 */
@WebServlet("/admin/CheckTaskServlet")
public class CheckTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private String action;		// ����Ķ���
	
	private CheckTaskService checkTaskService = new CheckTaskServiceImpl();
	private ExportService exportService = new ExportServiceImp();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckTaskServlet() {
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
		if(request.getParameter("action") != null)
		{
			this.action = request.getParameter("action");
			if(action.equals("addCheckTask"))	// �����̵�����
			{
				addCheckTask(request, response);
			}
			if(action.equals("deleteTask"))	// �����̵�����
			{
				delete(request, response);
			}
			if(action.equals("export"))	// ����
			{
				export(request, response);
			}
		}
		else
		{
			// ������Ϣ
			// ������Ϣ
			List<CheckTaskInfo> taskInfoList = taskInfoUpdate();

			request.getSession().setAttribute("taskInfoList", taskInfoList);
			request.getRequestDispatcher("/WEB-INF/view/admin/check_task.jsp").forward(request, response);
		}		
	}

	private void export(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		int index = Integer.parseInt(request.getParameter("index"));
		
		List<CheckTaskInfo> taskInfoList = (List<CheckTaskInfo>) request.getSession().getAttribute("taskInfoList");
		
		// �̵��񴴽�
		CheckTable checkTable = new CheckTable();
		// �������
		for (CheckTaskRecord taskRecord : taskInfoList.get(index).getTaskRecords()) {
			checkTable.addCheck(taskRecord);
		}

		CheckTask task = taskInfoList.get(index).getCheckTask();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		
		String fileName = "��ʼʱ��:" + df.format(task.getStart_time()) + 
						" ����ʱ��:" + df.format(task.getEnd_time())+
						" �̵����:" + task.getShelves() +
						" �̵���Ա:" + task.getUsername() + 
						" �̵㱨��.xlsx";
		String filename = new String(fileName.getBytes(), "iso8859-1");
		
		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename="+filename);// Ҫ������ļ���
		response.flushBuffer();
		response.setCharacterEncoding("UTF-8");
		
		exportService.exportExcel("�̵㱨��", checkTable.getTitle(), checkTable.getDataSet(), response.getOutputStream(),
				ExportExcelUtil.EXCEl_FILE_2007);

		
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String id = request.getParameter("id");
		
		if(checkTaskService.deleteTaskById(id))
		{
			checkTaskService.deleteTaskRecordById(id);
			
			request.getSession().setAttribute("message", " ɾ���ɹ�!");
			
			// ������Ϣ
			List<CheckTaskInfo> taskInfoList = taskInfoUpdate();

			request.getSession().setAttribute("taskInfoList", taskInfoList);
		}
		else
		{
			request.getSession().setAttribute("message", " ɾ���ɹ�!");
		}
		request.getRequestDispatcher("/WEB-INF/view/admin/check_task.jsp").forward(request, response);
	}

	private void addCheckTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String startTime = request.getParameter("start_time");
		String endTime =  request.getParameter("end_time");
		String shelves = request.getParameter("select_shelves");
		String username = request.getParameter("select_username");
		
		//System.out.println("s:" + startTime + " e:" + endTime + " s:" + shelves + " u:" + user);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		CheckTask task = new CheckTask();
		try {
			task.setStart_time(df.parse(startTime));
			task.setEnd_time(df.parse(endTime));
			task.setShelves(shelves);
			task.setUsername(username);
			
			checkTaskService.addCheckTask(task);
			
			request.getSession().setAttribute("message", "�½��ɹ�!");
			
			// ������Ϣ
			List<CheckTaskInfo> taskInfoList = taskInfoUpdate();

			request.getSession().setAttribute("taskInfoList", taskInfoList);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		request.getRequestDispatcher("/WEB-INF/view/admin/check_task.jsp").forward(request, response);
	}

	
	private List<CheckTaskInfo> taskInfoUpdate(){
		// ������Ϣ
		List<CheckTaskInfo> taskInfoList = new ArrayList<CheckTaskInfo>();
			
		List<CheckTask> allCheckTaskList = checkTaskService.findAllTask();
		for (CheckTask checkTask : allCheckTaskList)
		{
			List<CheckTaskRecord> records = checkTaskService.findAllTaskRecordById(checkTask.getId());
			
			CheckTaskInfo taskInfo = new CheckTaskInfo(checkTask, records);
			taskInfoList.add(taskInfo);
		}
		
		return taskInfoList;
	}

}
