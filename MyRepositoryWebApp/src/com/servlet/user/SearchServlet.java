package com.servlet.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.SearchResult;
import com.mapper.ClothingInfo;
import com.service.ExportService;
import com.service.WarehouseService;
import com.service.impl.ExportServiceImp;
import com.service.impl.WarehouseServiceImpl;

/**
 * Servlet implementation class SreachServlet
 */
@WebServlet("/user/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private WarehouseService warehouseService = new WarehouseServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// �û�ҵ���߼�ʵ����
		ExportService exportService = new ExportServiceImp();

		String clothingID = request.getParameter("clothingId");

		List<ClothingInfo> list = warehouseService.findAllById(clothingID);

		int sum = 0; // �·�����
		List<String> indexList = new ArrayList<String>(); // �·�λ��

		for (ClothingInfo c : list) {
			sum += c.getNumber();
			indexList.add(c.getShelves() + "-" + c.getLocation());
		}

		SearchResult result = new SearchResult(clothingID, sum, indexList);

		// ���浽session��
		HttpSession session = request.getSession();
		session.setAttribute("searchResult", result);

		request.getRequestDispatcher("/WEB-INF/view/user/search_good.jsp").forward(request, response);
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

}
