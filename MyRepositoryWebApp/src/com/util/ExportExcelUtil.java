package com.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.entity.Check;

/* 报表导出工具类 */
public class ExportExcelUtil<T> {
	// 2007 版本以上 最大支持1048576行
	public  final static String  EXCEl_FILE_2007 = "2007";
	// 2003 版本 最大支持65536 行
	public  final static String  EXCEL_FILE_2003 = "2003";

	public void ExportExcel(String title, String[] HeaderTitle, Collection<T> dataSet, OutputStream out, String version)
	{
		if(version.equals("") || version.equals(null) || EXCEL_FILE_2003.equals(version.trim()))
			ExportExcel2003(title,HeaderTitle, dataSet, out, "yyyy-MM-dd HH:mm:ss");
		else 
			ExportExcel2007(title,HeaderTitle, dataSet, out, "yyyy-MM-dd HH:mm:ss");
	}

	
	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，
	 * 可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 此版本生成2007以上版本的文件 (文件后缀：xls)
	 * @param title	
	 * 			表格标题名
	 * @param HeaderTitle
	 * 			 表格头部标题集合
	 * @param dataSet
	 * 			需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
	 *            JavaBean属性的数据类型有基本数据类型及String,Date
	 * @param out
	 * 			 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
	 */
	// 导出Excel
	public void ExportExcel2003(String title, String[] HeaderTitle, Collection<T> dataSet, OutputStream out, String pattern)
	{
		// 声明一个Excel文档对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short)15);
		sheet.setDefaultRowHeight((short)600);
		
		// 生成表格标题行的样式
		HSSFCellStyle styleHeaderTitle = workbook.createCellStyle();
		
		// 设置样式
		styleHeaderTitle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());	// 背景颜色
		styleHeaderTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);			//设置图案样式
		styleHeaderTitle.setBorderBottom(BorderStyle.DOTTED);							//下边框 
		styleHeaderTitle.setBorderLeft(BorderStyle.DOTTED);
		styleHeaderTitle.setBorderRight(BorderStyle.THIN);
		styleHeaderTitle.setBorderTop(BorderStyle.THIN);
		styleHeaderTitle.setAlignment(HorizontalAlignment.CENTER);		//左右居中
		styleHeaderTitle.setVerticalAlignment(VerticalAlignment.CENTER);	// 上下居中
		
		// 生成一个字体样式
		HSSFFont fontHeaderTitle = workbook.createFont();
		
		// 设置字体
		fontHeaderTitle.setBold(true);
		fontHeaderTitle.setFontName("宋体");
		fontHeaderTitle.setColor(IndexedColors.BLACK.getIndex());
		fontHeaderTitle.setFontHeightInPoints((short)11);
		
		// 把当前的字体应用到当前样式
		styleHeaderTitle.setFont(fontHeaderTitle);
		
		// 生成表格内容样式
		HSSFCellStyle styleContent = workbook.createCellStyle();
		
		// 设置样式
		styleContent.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		styleContent.setFillPattern(FillPatternType.SOLID_FOREGROUND);			//设置图案样式
		styleContent.setBorderBottom(BorderStyle.DOTTED);							//下边框 
		styleContent.setBorderLeft(BorderStyle.DOTTED);
		styleContent.setBorderRight(BorderStyle.THIN);
		styleContent.setBorderTop(BorderStyle.THIN);
		styleContent.setAlignment(HorizontalAlignment.CENTER);			// 左右居中
		styleContent.setVerticalAlignment(VerticalAlignment.CENTER);	// 上下居中
		
		// 生成字体
		HSSFFont fontContent = workbook.createFont();
		
		// 设置字体
		fontContent.setBold(false);
		
		// 把字体应用到样式中
		styleContent.setFont(fontContent);
		
		// 产生表格标题
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < HeaderTitle.length; i++) {
			HSSFCell cellHeaderTitle = row.createCell(i);
			cellHeaderTitle.setCellStyle(styleHeaderTitle);
			cellHeaderTitle.setCellValue(new HSSFRichTextString(HeaderTitle[i]));
		}
		
		// 遍历数据集合，获得行数据
		Iterator<T> it = dataSet.iterator();
		int rowIndex = 0;	// 
		while(it.hasNext())
		{
			rowIndex++;
			row = sheet.createRow(rowIndex);
			T t = it.next();
			// 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
			// 获取类中所有的属性(public、protected、default、private)，但不包括继承的属性，返回 Field 对象的一个数组
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++)
			{
				HSSFCell cellContent = row.createCell(i);
				cellContent.setCellStyle(styleContent);
				
				Field field = fields[i];
				// 获取属性的名字
				String fieldName = field.getName();
				// get方法
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					//获得了T这个(类)Class，进而通过返回的Class对象获取Person的相关信息，比如：获取Person的构造方法，方法，属性有哪些等等信息。
					Class tClass = t.getClass();
					// getMethod作用是获得对象所声明的公开方法
					Method getMethod = tClass.getMethod(getMethodName, new Class[]{});
					Object value = getMethod.invoke(t, new Object[]{});
					String textValue = null;
					//判断值的类型后进行强制类型转换
					if(value instanceof Boolean)
					{
						textValue = "是";
						if(!(Boolean)value)
							textValue = "否";
					}
					else if (value instanceof Date)
					{
						Date date = (Date) value;  
	                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
	                    textValue = sdf.format(date);
					}
					else //其它数据类型都当作字符串简单处理
					{
						textValue = value.toString();
					}
					//利用正则表达式判断textValue是否全部由数字组成
					if(textValue!=null)
					{
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");     
	                    Matcher matcher = p.matcher(textValue); 
	                    if(matcher.matches())//是数字当作double处理 
	                    {
	                    	cellContent.setCellValue(Double.parseDouble(textValue));
	                    }
	                    else 
	                    {
	                    	HSSFRichTextString richTextString = new HSSFRichTextString(textValue);
	                    	short AtuoWidth = (short)(textValue.toString().getBytes().length * 256);
	                    	//System.out.println("width:" + (short)(textValue.toString().getBytes().length * 256));
	                    	if( AtuoWidth > (short)9000)
	                    	{
	                    		sheet.setColumnWidth(0, (short)(textValue.toString().getBytes().length * 256 + 400));	// 第一列宽度
	                    	}
							cellContent.setCellValue(richTextString);
						}
						
					}
					
				} catch (NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		// 写到输出流
		try {
			workbook.write(out);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，
	 * 可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 此版本生成2007以上版本的文件 (文件后缀：xlsx)
	 * @param title	
	 * 			表格标题名
	 * @param HeaderTitle
	 * 			 表格头部标题集合
	 * @param dataSet
	 * 			需要显示的数据集合,集合中一定要放置符合JavaBean风格的类的对象。此方法支持的
	 *            JavaBean属性的数据类型有基本数据类型及String,Date
	 * @param out
	 * 			 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyyy-MM-dd hh:mm:ss"
	 */
	public void ExportExcel2007(String title, String[] HeaderTitle, Collection<T> dataSet, OutputStream out, String pattern)
	{
		// 声明一个Excel文档对象
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short)15);
		sheet.setDefaultRowHeight((short)600);
		
		// 生成表格标题行的样式
		XSSFCellStyle styleHeaderTitle = workbook.createCellStyle();
		
		// 设置样式
		styleHeaderTitle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());	// 背景颜色
		styleHeaderTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);			//设置图案样式
		styleHeaderTitle.setBorderBottom(BorderStyle.DOTTED);							//下边框 
		styleHeaderTitle.setBorderLeft(BorderStyle.DOTTED);
		styleHeaderTitle.setBorderRight(BorderStyle.THIN);
		styleHeaderTitle.setBorderTop(BorderStyle.THIN);
		styleHeaderTitle.setAlignment(HorizontalAlignment.CENTER);		//左右居中
		styleHeaderTitle.setVerticalAlignment(VerticalAlignment.CENTER);	// 上下居中
		
		// 生成一个字体样式
		XSSFFont fontHeaderTitle = workbook.createFont();
		
		// 设置字体
		fontHeaderTitle.setBold(true);
		fontHeaderTitle.setFontName("宋体");
		fontHeaderTitle.setColor(IndexedColors.BLACK.getIndex());
		fontHeaderTitle.setFontHeightInPoints((short)11);
		
		// 把当前的字体应用到当前样式
		styleHeaderTitle.setFont(fontHeaderTitle);
		
		// 生成表格内容样式
		XSSFCellStyle styleContent = workbook.createCellStyle();
		
		// 设置样式
		styleContent.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		styleContent.setFillPattern(FillPatternType.SOLID_FOREGROUND);			//设置图案样式
		styleContent.setBorderBottom(BorderStyle.DOTTED);							//下边框 
		styleContent.setBorderLeft(BorderStyle.DOTTED);
		styleContent.setBorderRight(BorderStyle.THIN);
		styleContent.setBorderTop(BorderStyle.THIN);
		styleContent.setAlignment(HorizontalAlignment.CENTER);			// 左右居中
		styleContent.setVerticalAlignment(VerticalAlignment.CENTER);	// 上下居中
		
		// 生成字体
		XSSFFont fontContent = workbook.createFont();
		
		// 设置字体
		fontContent.setBold(false);
		
		// 把字体应用到样式中
		styleContent.setFont(fontContent);
		
		// 产生表格标题
		XSSFRow row = sheet.createRow(0);
		for (short i = 0; i < HeaderTitle.length; i++) {
			XSSFCell cellHeaderTitle = row.createCell(i);
			cellHeaderTitle.setCellStyle(styleHeaderTitle);
			cellHeaderTitle.setCellValue(new XSSFRichTextString(HeaderTitle[i]));
		}
		
		
		// 遍历数据集合，获得行数据
		Iterator<T> it = dataSet.iterator();
		int rowIndex = 0;	// 
		while(it.hasNext())
		{
			rowIndex++;
			row = sheet.createRow(rowIndex);
			T t = it.next();
			// 利用反射，根据JavaBean属性的先后顺序，动态调用getXxx()方法得到属性值
			// 获取类中所有的属性(public、protected、default、private)，但不包括继承的属性，返回 Field 对象的一个数组
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++)
			{
				XSSFCell cellContent = row.createCell(i);
				cellContent.setCellStyle(styleContent);
				
				Field field = fields[i];
				// 获取属性的名字
				String fieldName = field.getName();
				// get方法
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					//获得了T这个(类)Class，进而通过返回的Class对象获取Person的相关信息，比如：获取Person的构造方法，方法，属性有哪些等等信息。
					Class tClass = t.getClass();
					// getMethod作用是获得对象所声明的公开方法
					Method getMethod = tClass.getMethod(getMethodName, new Class[]{});
					Object value = getMethod.invoke(t, new Object[]{});
					String textValue = null;
					//判断值的类型后进行强制类型转换
					if(value instanceof Boolean)
					{
						textValue = "是";
						if(!(Boolean)value)
							textValue = "否";
					}
					else if (value instanceof Date)
					{
						Date date = (Date) value;  
	                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
	                    textValue = sdf.format(date);
					}
					else //其它数据类型都当作字符串简单处理
					{
						textValue = value.toString();
					}
					//利用正则表达式判断textValue是否全部由数字组成
					if(textValue!=null)
					{
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");     
	                    Matcher matcher = p.matcher(textValue); 
	                    if(matcher.matches())//是数字当作double处理 
	                    {
	                    	cellContent.setCellValue(Double.parseDouble(textValue));
	                    }
	                    else 
	                    {
	                    	XSSFRichTextString richTextString = new XSSFRichTextString(textValue);
	                    	short AtuoWidth = (short)(textValue.toString().getBytes().length * 256);
	                    	//System.out.println("width:" + (short)(textValue.toString().getBytes().length * 256));
	                    	if( AtuoWidth > (short)9000)
	                    	{
	                    		sheet.setColumnWidth(0, (short)(textValue.toString().getBytes().length * 256 + 400));	// 第一列宽度
	                    	}
							cellContent.setCellValue(richTextString);
						}
						
					}
					
				} catch (NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		// 写到输出流
		try {
			workbook.write(out);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	// 测试
/*	public static void main(String[] args){
			ExportExcelUtil<Check> excelUtil = new ExportExcelUtil<Check>();
			
			List<Check> list = new ArrayList<Check>(); 
			Check check1 = new Check(
					"4a507db7-7c52-2c22-d6a6-77ade48625bd",
					"A-5", 9, 10, 1, 0);
			Check check2 = new Check(
					"4a507db7-7c52-2c22-d6a6-77ade48625bd",
					"A-6", 9, 10, 1, 0);
			Check check3 = new Check(
					"4a507db7-7c52-2c22-d6a6-77ade48625bd",
					"A-5", 9, 8, 0, 1);
			list.add(check1);
			list.add(check2);
			list.add(check3);
			
			String[] headerTitle = new String[]{"服装ID", "位置", "数量", "盘点", "盘盈", "盘亏"};
			
			try {
				excelUtil.ExportExcel("导出Excel测试", headerTitle, list, new FileOutputStream("D:/Desktop/大三下/软件系统分析实验/texts.xlsx"), ExportExcelUtil.EXCEl_FILE_2007);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("失败");
			}
	}*/
	
}
