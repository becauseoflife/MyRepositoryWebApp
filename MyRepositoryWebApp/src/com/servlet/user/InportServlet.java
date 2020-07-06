package com.servlet.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.entity.Order;
import com.mapper.OrderItemInfo;
import com.service.OrderService;
import com.service.impl.OrderServiceImpl;
import com.util.Inport;
import com.util.InportFactory;

/**
 * Servlet implementation class InportServlet
 */
@WebServlet("/user/InportServlet")
public class InportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrderService orderService = new OrderServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InportServlet() {
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

		InportOrder(request, response);

	}

	private void InportOrder(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		// ���������ࣺ
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// ������������
		ServletFileUpload upload = new ServletFileUpload(factory);
		// �����ļ��ϴ��Ĵ�С
		// �������е��ļ��ϴ��Ĵ�С
		// upload.setFileSizeMax(1024*5*16);
		// ���Ƶ����ļ��ϴ��Ĵ�С
		// upload.setSizeMax(1024*16);
		// �����ϴ������ĸ�ʽ
		upload.setHeaderEncoding("utf-8");

		// ʹ�ý���������request����
		List<FileItem> list = null;
		try {
			list = upload.parseRequest(request);
			for (FileItem fileItem : list) {
				// ��ȡ�ļ�
				if (!fileItem.isFormField()) {
					// System.out.println("�ļ�����" + fileItem.getName());
					String filename = fileItem.getName(); // "123.txt"
					// String
					// nameSuffix=filename.substring(filename.lastIndexOf("."));
					// System.out.println(nameSuffix);//.txt
					String nameSuffix = filename.substring(filename.lastIndexOf(".") + 1);
					// System.out.println(nameSuffix);//txt

					// ���ݺ�׺������Ӧ�ĵ��뷽��
					Inport inportUtil = InportFactory.produceInport(nameSuffix);

					List<Order> orderList = inportUtil.inportOrder(fileItem.getInputStream());

					String message = "";
					// ���뵽���ݿ���
					for (Order order : orderList) {
						if (orderService.add(order.getOrderInfo())) {
							for (OrderItemInfo itemInfo : order.getGoodsList()) {
								orderService.add(itemInfo);
							}
						} else {
							message += "����ID:<strong>" + order.getOrderInfo().getOrder_id()
									+ "</strong>	�Ѵ���!����ʧ��</br>";
						}
					}

					if (message.equals("")) {
						session.setAttribute("message", "����ɹ�!</br>���붩����: " + orderList.size());
					} else {
						session.setAttribute("message", message);
					}
					request.getRequestDispatcher("/WEB-INF/view/user/inport_order.jsp").forward(request, response);
					return;
				}
			}

		} catch (FileUploadException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
