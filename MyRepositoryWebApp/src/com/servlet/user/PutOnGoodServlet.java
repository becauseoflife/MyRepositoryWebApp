package com.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.enterprise.inject.New;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.mapper.ClothingInfo;
import com.service.ExportService;
import com.service.WarehouseService;
import com.service.impl.ExportServiceImp;
import com.service.impl.WarehouseServiceImpl;

/**
 * Servlet implementation class PutOnGood
 */
@WebServlet("/user/PutOnGoodServlet")
public class PutOnGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String action;	// ����Ķ���
	
	private WarehouseService warehouseService = new WarehouseServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PutOnGoodServlet() {
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
			if(action.equals("getEmptyPositon"))	// ��ÿ�λ��
			{
				getEmptyPositon(request, response);
			}
			if(action.equals("putOn"))
			{
				putOnGood(request, response);
				
				request.getRequestDispatcher("PutOnGoodServlet?action=getEmptyPositon").forward(request, response);
			}
		}

	}

	private void putOnGood(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// ����ϼܵ�λ��
		String position = request.getParameter("select_position");
		// ��ȡ�ϼܷ�װ��ID������
		String clothingID = request.getParameter("clothingId");
		int num = Integer.parseInt(request.getParameter("number"));
		
		// ��ѯ�����Ƿ��Ѿ�����
		List<ClothingInfo> cList = warehouseService.findAllById(clothingID);
		
		if(cList.size()!=0)
		{
			ClothingInfo c = cList.get(0);
			// ��������
			c.setNumber(c.getNumber() + num);
			warehouseService.updateSetNumber(c);
			
			String p = c.getShelves() + "-" + c.getLocation();
			
			session.setAttribute("message", "���ɹ�!<br/>��ŵ��ֿ�� <strong>" + p + "</strong> ��" );
			return;
		}
		
		// ���β����ڣ���ŵ��ֿ����ݱ���
		if(cList.size()==0)
		{
			String[] p = position.split("-");
			ClothingInfo updateClo = ClothingInfo.builder()
										.clothingID(clothingID)
										.shelves(p[0])
										.location(p[1])
										.number(num)
										.build();
			
/*			updateClo.setClothingID(clothingID);
			updateClo.setShelves(p[0]);
			updateClo.setLocation(p[1]);
			updateClo.setNumber(num);*/
			// ���������ݿ�
			if(warehouseService.updateSetIdAndNumber(updateClo))
			{
				
				String message = "���ɹ�!<br/>��ŵ��ֿ�� <strong>" + position + "</strong> ��";
				session.setAttribute("message", message);
				return;
			}
			else
			{
				String message = "���ʧ�ܣ�";
				session.setAttribute("message", message);
				return;
			}
		}
	}

	private void getEmptyPositon(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// ��ȡ��λ��
		List<ClothingInfo> emptyPositionList = warehouseService.findAllWithEmpty();
		
		// ���޿�λ�ñ�־
		if(emptyPositionList.size()==0)
		{
			request.getSession().setAttribute("hasEmptyPosition", false);
		}
		else
		{
			request.getSession().setAttribute("hasEmptyPosition", true);
			request.getSession().setAttribute("emptyPositionList", emptyPositionList);
		}
		
		request.getRequestDispatcher("/WEB-INF/view/user/put_on_good.jsp").forward(request, response);
		
		
/*		PrintWriter writer;
		String jsonStr = JSON.toJSONString(emptyPositionList);
		//System.out.println(jsonStr);
		try {
			writer = response.getWriter();
			writer.write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}



}
