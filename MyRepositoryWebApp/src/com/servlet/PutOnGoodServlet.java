package com.servlet;

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
@WebServlet("/PutOnGoodServlet")
public class PutOnGoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String action;	// 请求的动作
	
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
			if(action.equals("getEmptyPositon"))	// 获得空位置
			{
				getEmptyPositon(request, response);
			}
			if(action.equals("putOn"))
			{
				putOnGood(request, response);
				
				
				request.getRequestDispatcher("/WEB-INF/view/user/put_on_good.jsp").forward(request, response);
			}
		}

	}

	private void putOnGood(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		// 获得上架的位置
		String position = request.getParameter("select_position");
		// 获取上架服装的ID和数量
		String clothingID = request.getParameter("clothingId");
		int num = Integer.parseInt(request.getParameter("number"));
		
		// 查询服饰是否已经存在
		List<ClothingInfo> c = warehouseService.findAllById(clothingID);
		if(c.size()!=0)
		{
			String message = "服装: <strong>" + clothingID + "</strong>"
					+ "<br/>已经存在仓库位置: <strong>"+c.get(0).getShelves()+"-"+c.get(0).getLocation() +"</strong>"
					+ "<br/>请到盘点功能进行更新！";
			session.setAttribute("message", message);
			return;
		}
		
		// 服饰不存在，存放到仓库数据表中
		if(c.size()==0)
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
			// 更新新数据库
			if(warehouseService.updateSetIdAndNumber(updateClo))
			{
				String message = "上架成功！";
				session.setAttribute("message", message);
				return;
			}
			else
			{
				String message = "上架失败！";
				session.setAttribute("message", message);
				return;
			}
		}
	}

	private void getEmptyPositon(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		// 获取空位置
		List<ClothingInfo> emptyPositionList = warehouseService.findAllWithEmpty();
		
		// 有无空位置标志
		if(emptyPositionList.size()==0)
		{
			request.getSession().setAttribute("hasEmptyPosition", false);
		}
		else
		{
			request.getSession().setAttribute("hasEmptyPosition", true);
		}
		
		PrintWriter writer;
		String jsonStr = JSON.toJSONString(emptyPositionList);
		//System.out.println(jsonStr);
		try {
			writer = response.getWriter();
			writer.write(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
