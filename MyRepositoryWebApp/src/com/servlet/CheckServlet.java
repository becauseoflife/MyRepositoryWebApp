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
       
	private String action;		// �̵����Ķ���
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
			if(action.equals("query"))		// ͨ���ֿ�λ�ò��ҷ�װ
			{
				queryClothingByLocation(request, response);
			}
			if (action.equals("update")) 	// �̵��װ����
			{
				updateClothingNumber(request, response);
			}
			if(action.equals("delete"))		// ɾ���̵�����ĳ��
			{
				deleteFormCheckTable(request, response);
			}
			if(action.equals("exportExcel"))	// �̵��񵼳�Excel
			{
				exportExcel(request, response);

			}
		}
	}

	private void exportExcel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		if(checkTable == null)
		{
			session.setAttribute("message", "�ѵ�����");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}
		

		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=check.xlsx");//Ҫ������ļ���
		response.setContentType("application/octet-stream; charset=utf-8");
		

		service.exportExcel("��ӯ���̿�����", checkTable.getTitle(), checkTable.getDataSet(), response.getOutputStream(), ExportExcelUtil.EXCEl_FILE_2007);

		// ���̵�������session��ɾ��
		session.removeAttribute("checkTable");
	}

	private void deleteFormCheckTable(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡɾ��������
		String row = request.getParameter("row");
		
		// ȡ��ɾ���Ķ���
		Check check = (Check)session.getAttribute(row);
			
		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		// ɾ��
		checkTable.deleteCheck(check);
		
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void updateClothingNumber(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡ���µ�����
		int updateNum = Integer.parseInt(request.getParameter("updateNum"));
		// ��ȡ�����µķ�װ
		ClothingInfo c = (ClothingInfo)session.getAttribute("clothingInfo");
		// ��ȡ�̵������
		CheckTable checkTable = (CheckTable)session.getAttribute("checkTable");
		
		if(c!=null && checkTable!=null){

			c.setNumber(updateNum);
			// �������ݿ��е�����
			warehouseService.updateSetNumber(c);
			
			// ��ӯ���̿���ֵ
			int surplus = 0;
			int loss = 0;
	
			if(c.getNumber() > updateNum)	// �̿�
			{
				loss = Math.abs(c.getNumber()-updateNum);
			}
			else if(c.getNumber() < updateNum)	// ��ӯ
			{
				surplus = Math.abs(c.getNumber()-updateNum);
			}
			
			// �̵����
			Check check = new Check(
					c.getClothingID(),
					c.getShelves()+"-"+c.getLocation(),
					c.getNumber(),
					updateNum,
					surplus,
					loss);
			
			// �̵����¼
			checkTable.addCheck(check);
			
			// ������ɾ���������ظ�����
			session.removeAttribute("clothingInfo");
			
		}	
		request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
	}

	private void queryClothingByLocation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ��ȡλ����Ϣ
		String shelves = request.getParameter("shelves");
		String location = request.getParameter("location");
 		//System.out.println("λ����Ϣ��"+ shelves + "-" + location);
		
		// �̵������Ϊ�գ��򴴽�
		if(session.getAttribute("checkTable")== null)
		{
			CheckTable checkTable = new CheckTable();
			session.setAttribute("checkTable", checkTable);		// ���̵��ŵ�session��
		}
		
		// �Ӳֿ��л�ȡ������Ϣ
		ClothingInfo c = warehouseService.findByPosition(shelves, location);

		// �����ζ������session��
		if(c!=null && c.getClothingID()!=null)
		{
			session.setAttribute("clothingInfo", c);
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}
		else if(c != null && c.getClothingID()==null)
		{
			session.setAttribute("message", "λ��: <strong>"+c.getShelves()+"-"+c.getLocation()+"</strong>δ��ŷ���");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}
		else
		{
			session.setAttribute("message", "λ����������<br/>���������룡<br/>��: <br/>����:  A<br/>��λ:  1 ");
			request.getRequestDispatcher("/WEB-INF/view/user/check_good.jsp").forward(request, response);
		}
	}


}
