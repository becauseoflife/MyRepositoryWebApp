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
		
		// 订单列表
		List<Order> orderList = new ArrayList<Order>();
		
		try {
			// 声明一个Excel文档对象
			XSSFWorkbook workbook = new XSSFWorkbook(path);

			// 循环工作表 
			for(int sheet=0; sheet<workbook.getNumberOfSheets(); sheet++)
			{
				XSSFSheet xssfSheet = workbook.getSheetAt(sheet);
				//System.out.println("总列数:"+xssfSheet.getLastRowNum());
				if(xssfSheet == null)
				{
					continue;
				}
				
				OrderInfo orderInfo = null;							// 订单信息
				List<OrderItemInfo> goodsList = new ArrayList<OrderItemInfo>(); // 订购商品
				String orderID = null;		// 订单ID
				
				// 循环行 从第一行开始
				for(int rowIndex=1; rowIndex < xssfSheet.getLastRowNum()+1; rowIndex++)
				{
					XSSFRow xssfRow = xssfSheet.getRow(rowIndex);
				
					String idcellVal = xssfRow.getCell(0).getStringCellValue();
					if(xssfRow != null)
					{
						if(rowIndex != 1)
						{
							// 不同的订单
							if(!idcellVal.equals(orderID) && !idcellVal.equals(""))	
							{
								// 生成一份订单
								Order order = new Order(orderInfo, goodsList);
								// 重置
								orderInfo = null;
								goodsList = new ArrayList<OrderItemInfo>();
								// 添加到结果中
								orderList.add(order);
								//System.out.println("订单生成！");
							}
						}
						
						// 订单ID
						if(!idcellVal.equals(""))
						{
							orderID = xssfRow.getCell(0).getStringCellValue();
							//System.out.println("ID:" + orderID);
							
							// 设置订单信息
							orderInfo = OrderInfo.builder()
									.order_id(orderID)
									.state(OrderInfo.UNRESOLVED)
									.create_time(new Date())
									.build();
						}
						
						// 订购列表
						String clothingID = xssfRow.getCell(1).getStringCellValue();
						int number = Integer.parseInt(getCellValue(xssfRow.getCell(2)));
						//System.out.println(" : " + clothingID + " : " + number);
						
						OrderItemInfo itemInfo = OrderItemInfo.builder()
													.order_id(orderID)
													.clothingID(clothingID)
													.number(number)
													.pick_sign(OrderItemInfo.NOT_PICK)
													.build();
						// 订购列表	
						goodsList.add(itemInfo);		
	
						
						// 最后一份订单生成
						if(rowIndex == xssfSheet.getLastRowNum())
						{
							// 生成一份订单
							Order order = new Order(orderInfo, goodsList);
							// 重置
							orderInfo = null;
							goodsList = new ArrayList<OrderItemInfo>();
							// 添加到结果中
							orderList.add(order);
							//System.out.println("订单生成！");
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
	 * 生成订单
	 */
	
	/*
	 * 获得Cell中的值
	 */
	public String getCellValue(Cell cell) {
        String cellValue = "";
        // 以下是判断数据的类型
        switch (cell.getCellType()) {
            case NUMERIC: // 数字
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                } else {
                    DataFormatter dataFormatter = new DataFormatter();
                    cellValue = dataFormatter.formatCellValue(cell);
                }
                break;
            case STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case FORMULA: // 公式
                cellValue = cell.getCellFormula() + "";
                break;
            case BLANK: // 空值
                cellValue = "";
                break;
            case ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
	
}
