package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.entity.Order;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;

public class InportExcel implements Inport {

	@SuppressWarnings("unused")
	@Override
	public List<Order> inportOrder(InputStream path) {
		// TODO Auto-generated method stub
		
		// �����б�
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			// ����һ��Excel�ĵ�����
			XSSFWorkbook workbook = new XSSFWorkbook(path);

			// ѭ�������� 
			for(int sheet=0; sheet<workbook.getNumberOfSheets(); sheet++)
			{
				XSSFSheet xssfSheet = workbook.getSheetAt(sheet);
				//System.out.println("������:"+xssfSheet.getLastRowNum());
				if(xssfSheet == null)
				{
					continue;
				}
				
				OrderInfo orderInfo = null;							// ������Ϣ
				List<OrderItemInfo> goodsList = new ArrayList<OrderItemInfo>(); // ������Ʒ
				String orderID = null;		// ����ID
				
				// ѭ���� �ӵ�һ�п�ʼ
				for(int rowIndex=1; rowIndex < xssfSheet.getLastRowNum()+1; rowIndex++)
				{
					XSSFRow xssfRow = xssfSheet.getRow(rowIndex);
				
					String idcellVal = xssfRow.getCell(0).getStringCellValue();
					if(xssfRow != null)
					{
						if(rowIndex != 1)
						{
							// ��ͬ�Ķ���
							if(!idcellVal.equals(orderID) && !idcellVal.equals(""))	
							{
								// ����һ�ݶ���
								Order order = new Order(orderInfo, goodsList);
								// ����
								orderInfo = null;
								goodsList = new ArrayList<OrderItemInfo>();
								// ��ӵ������
								orderList.add(order);
								//System.out.println("�������ɣ�");
							}
						}
						
						// ����ID
						if(!idcellVal.equals(""))
						{
							orderID = xssfRow.getCell(0).getStringCellValue();
							//System.out.println("ID:" + orderID);
							
							// ���ö�����Ϣ
							orderInfo = OrderInfo.builder()
									.order_id(orderID)
									.state(OrderInfo.UNRESOLVED)
									.create_time(new Date())
									.build();
						}
						
						// �����б�
						String clothingID = xssfRow.getCell(1).getStringCellValue();
						int number = Integer.parseInt(getCellValue(xssfRow.getCell(2)));
						//System.out.println(" : " + clothingID + " : " + number);
						
						OrderItemInfo itemInfo = OrderItemInfo.builder()
													.order_id(orderID)
													.clothingID(clothingID)
													.number(number)
													.pick_sign(OrderItemInfo.NOT_PICK)
													.build();
						// �����б�	
						goodsList.add(itemInfo);		
	
						
						// ���һ�ݶ�������
						if(rowIndex == xssfSheet.getLastRowNum())
						{
							// ����һ�ݶ���
							Order order = new Order(orderInfo, goodsList);
							// ����
							orderInfo = null;
							goodsList = new ArrayList<OrderItemInfo>();
							// ��ӵ������
							orderList.add(order);
							//System.out.println("�������ɣ�");
						}
					}
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orderList;
	}

	/*
	 * ���ɶ���
	 */
	
	/*
	 * ���Cell�е�ֵ
	 */
	public String getCellValue(Cell cell) {
        String cellValue = "";
        // �������ж����ݵ�����
        switch (cell.getCellType()) {
            case NUMERIC: // ����
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                } else {
                    DataFormatter dataFormatter = new DataFormatter();
                    cellValue = dataFormatter.formatCellValue(cell);
                }
                break;
            case STRING: // �ַ���
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case FORMULA: // ��ʽ
                cellValue = cell.getCellFormula() + "";
                break;
            case BLANK: // ��ֵ
                cellValue = "";
                break;
            case ERROR: // ����
                cellValue = "�Ƿ��ַ�";
                break;
            default:
                cellValue = "δ֪����";
                break;
        }
        return cellValue;
    }
	
}
