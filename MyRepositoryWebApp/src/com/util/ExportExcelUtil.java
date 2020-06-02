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

/* ������������ */
public class ExportExcelUtil<T> {
	// 2007 �汾���� ���֧��1048576��
	public  final static String  EXCEl_FILE_2007 = "2007";
	// 2003 �汾 ���֧��65536 ��
	public  final static String  EXCEL_FILE_2003 = "2003";

	public void ExportExcel(String title, String[] HeaderTitle, Collection<T> dataSet, OutputStream out, String version)
	{
		if(version.equals("") || version.equals(null) || EXCEL_FILE_2003.equals(version.trim()))
			ExportExcel2003(title,HeaderTitle, dataSet, out, "yyyy-MM-dd HH:mm:ss");
		else 
			ExportExcel2007(title,HeaderTitle, dataSet, out, "yyyy-MM-dd HH:mm:ss");
	}

	
	/**
	 * ����һ��ͨ�õķ�����������JAVA�ķ�����ƣ�
	 * ���Խ�������JAVA�����в��ҷ���һ��������������EXCEL ����ʽ�����ָ��IO�豸��
	 * �˰汾����2007���ϰ汾���ļ� (�ļ���׺��xls)
	 * @param title	
	 * 			��������
	 * @param HeaderTitle
	 * 			 ���ͷ�����⼯��
	 * @param dataSet
	 * 			��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���JavaBean������Ķ��󡣴˷���֧�ֵ�
	 *            JavaBean���Ե����������л����������ͼ�String,Date
	 * @param out
	 * 			 ������豸�����������󣬿��Խ�EXCEL�ĵ������������ļ�����������
	 * @param pattern
	 *            �����ʱ�����ݣ��趨�����ʽ��Ĭ��Ϊ"yyyy-MM-dd hh:mm:ss"
	 */
	// ����Excel
	public void ExportExcel2003(String title, String[] HeaderTitle, Collection<T> dataSet, OutputStream out, String pattern)
	{
		// ����һ��Excel�ĵ�����
		HSSFWorkbook workbook = new HSSFWorkbook();
		// ����һ�����
		HSSFSheet sheet = workbook.createSheet(title);
		// ���ñ��Ĭ���п��Ϊ15���ֽ�
		sheet.setDefaultColumnWidth((short)15);
		sheet.setDefaultRowHeight((short)600);
		
		// ���ɱ������е���ʽ
		HSSFCellStyle styleHeaderTitle = workbook.createCellStyle();
		
		// ������ʽ
		styleHeaderTitle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());	// ������ɫ
		styleHeaderTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);			//����ͼ����ʽ
		styleHeaderTitle.setBorderBottom(BorderStyle.DOTTED);							//�±߿� 
		styleHeaderTitle.setBorderLeft(BorderStyle.DOTTED);
		styleHeaderTitle.setBorderRight(BorderStyle.THIN);
		styleHeaderTitle.setBorderTop(BorderStyle.THIN);
		styleHeaderTitle.setAlignment(HorizontalAlignment.CENTER);		//���Ҿ���
		styleHeaderTitle.setVerticalAlignment(VerticalAlignment.CENTER);	// ���¾���
		
		// ����һ��������ʽ
		HSSFFont fontHeaderTitle = workbook.createFont();
		
		// ��������
		fontHeaderTitle.setBold(true);
		fontHeaderTitle.setFontName("����");
		fontHeaderTitle.setColor(IndexedColors.BLACK.getIndex());
		fontHeaderTitle.setFontHeightInPoints((short)11);
		
		// �ѵ�ǰ������Ӧ�õ���ǰ��ʽ
		styleHeaderTitle.setFont(fontHeaderTitle);
		
		// ���ɱ��������ʽ
		HSSFCellStyle styleContent = workbook.createCellStyle();
		
		// ������ʽ
		styleContent.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		styleContent.setFillPattern(FillPatternType.SOLID_FOREGROUND);			//����ͼ����ʽ
		styleContent.setBorderBottom(BorderStyle.DOTTED);							//�±߿� 
		styleContent.setBorderLeft(BorderStyle.DOTTED);
		styleContent.setBorderRight(BorderStyle.THIN);
		styleContent.setBorderTop(BorderStyle.THIN);
		styleContent.setAlignment(HorizontalAlignment.CENTER);			// ���Ҿ���
		styleContent.setVerticalAlignment(VerticalAlignment.CENTER);	// ���¾���
		
		// ��������
		HSSFFont fontContent = workbook.createFont();
		
		// ��������
		fontContent.setBold(false);
		
		// ������Ӧ�õ���ʽ��
		styleContent.setFont(fontContent);
		
		// ����������
		HSSFRow row = sheet.createRow(0);
		for (short i = 0; i < HeaderTitle.length; i++) {
			HSSFCell cellHeaderTitle = row.createCell(i);
			cellHeaderTitle.setCellStyle(styleHeaderTitle);
			cellHeaderTitle.setCellValue(new HSSFRichTextString(HeaderTitle[i]));
		}
		
		// �������ݼ��ϣ����������
		Iterator<T> it = dataSet.iterator();
		int rowIndex = 0;	// 
		while(it.hasNext())
		{
			rowIndex++;
			row = sheet.createRow(rowIndex);
			T t = it.next();
			// ���÷��䣬����JavaBean���Ե��Ⱥ�˳�򣬶�̬����getXxx()�����õ�����ֵ
			// ��ȡ�������е�����(public��protected��default��private)�����������̳е����ԣ����� Field �����һ������
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++)
			{
				HSSFCell cellContent = row.createCell(i);
				cellContent.setCellStyle(styleContent);
				
				Field field = fields[i];
				// ��ȡ���Ե�����
				String fieldName = field.getName();
				// get����
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					//�����T���(��)Class������ͨ�����ص�Class�����ȡPerson�������Ϣ�����磺��ȡPerson�Ĺ��췽������������������Щ�ȵ���Ϣ��
					Class tClass = t.getClass();
					// getMethod�����ǻ�ö����������Ĺ�������
					Method getMethod = tClass.getMethod(getMethodName, new Class[]{});
					Object value = getMethod.invoke(t, new Object[]{});
					String textValue = null;
					//�ж�ֵ�����ͺ����ǿ������ת��
					if(value instanceof Boolean)
					{
						textValue = "��";
						if(!(Boolean)value)
							textValue = "��";
					}
					else if (value instanceof Date)
					{
						Date date = (Date) value;  
	                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
	                    textValue = sdf.format(date);
					}
					else //�����������Ͷ������ַ����򵥴���
					{
						textValue = value.toString();
					}
					//����������ʽ�ж�textValue�Ƿ�ȫ�����������
					if(textValue!=null)
					{
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");     
	                    Matcher matcher = p.matcher(textValue); 
	                    if(matcher.matches())//�����ֵ���double���� 
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
	                    		sheet.setColumnWidth(0, (short)(textValue.toString().getBytes().length * 256 + 400));	// ��һ�п��
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
		// д�������
		try {
			workbook.write(out);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ����һ��ͨ�õķ�����������JAVA�ķ�����ƣ�
	 * ���Խ�������JAVA�����в��ҷ���һ��������������EXCEL ����ʽ�����ָ��IO�豸��
	 * �˰汾����2007���ϰ汾���ļ� (�ļ���׺��xlsx)
	 * @param title	
	 * 			��������
	 * @param HeaderTitle
	 * 			 ���ͷ�����⼯��
	 * @param dataSet
	 * 			��Ҫ��ʾ�����ݼ���,������һ��Ҫ���÷���JavaBean������Ķ��󡣴˷���֧�ֵ�
	 *            JavaBean���Ե����������л����������ͼ�String,Date
	 * @param out
	 * 			 ������豸�����������󣬿��Խ�EXCEL�ĵ������������ļ�����������
	 * @param pattern
	 *            �����ʱ�����ݣ��趨�����ʽ��Ĭ��Ϊ"yyyy-MM-dd hh:mm:ss"
	 */
	public void ExportExcel2007(String title, String[] HeaderTitle, Collection<T> dataSet, OutputStream out, String pattern)
	{
		// ����һ��Excel�ĵ�����
		XSSFWorkbook workbook = new XSSFWorkbook();
		// ����һ�����
		XSSFSheet sheet = workbook.createSheet(title);
		// ���ñ��Ĭ���п��Ϊ15���ֽ�
		sheet.setDefaultColumnWidth((short)15);
		sheet.setDefaultRowHeight((short)600);
		
		// ���ɱ������е���ʽ
		XSSFCellStyle styleHeaderTitle = workbook.createCellStyle();
		
		// ������ʽ
		styleHeaderTitle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());	// ������ɫ
		styleHeaderTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);			//����ͼ����ʽ
		styleHeaderTitle.setBorderBottom(BorderStyle.DOTTED);							//�±߿� 
		styleHeaderTitle.setBorderLeft(BorderStyle.DOTTED);
		styleHeaderTitle.setBorderRight(BorderStyle.THIN);
		styleHeaderTitle.setBorderTop(BorderStyle.THIN);
		styleHeaderTitle.setAlignment(HorizontalAlignment.CENTER);		//���Ҿ���
		styleHeaderTitle.setVerticalAlignment(VerticalAlignment.CENTER);	// ���¾���
		
		// ����һ��������ʽ
		XSSFFont fontHeaderTitle = workbook.createFont();
		
		// ��������
		fontHeaderTitle.setBold(true);
		fontHeaderTitle.setFontName("����");
		fontHeaderTitle.setColor(IndexedColors.BLACK.getIndex());
		fontHeaderTitle.setFontHeightInPoints((short)11);
		
		// �ѵ�ǰ������Ӧ�õ���ǰ��ʽ
		styleHeaderTitle.setFont(fontHeaderTitle);
		
		// ���ɱ��������ʽ
		XSSFCellStyle styleContent = workbook.createCellStyle();
		
		// ������ʽ
		styleContent.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		styleContent.setFillPattern(FillPatternType.SOLID_FOREGROUND);			//����ͼ����ʽ
		styleContent.setBorderBottom(BorderStyle.DOTTED);							//�±߿� 
		styleContent.setBorderLeft(BorderStyle.DOTTED);
		styleContent.setBorderRight(BorderStyle.THIN);
		styleContent.setBorderTop(BorderStyle.THIN);
		styleContent.setAlignment(HorizontalAlignment.CENTER);			// ���Ҿ���
		styleContent.setVerticalAlignment(VerticalAlignment.CENTER);	// ���¾���
		
		// ��������
		XSSFFont fontContent = workbook.createFont();
		
		// ��������
		fontContent.setBold(false);
		
		// ������Ӧ�õ���ʽ��
		styleContent.setFont(fontContent);
		
		// ����������
		XSSFRow row = sheet.createRow(0);
		for (short i = 0; i < HeaderTitle.length; i++) {
			XSSFCell cellHeaderTitle = row.createCell(i);
			cellHeaderTitle.setCellStyle(styleHeaderTitle);
			cellHeaderTitle.setCellValue(new XSSFRichTextString(HeaderTitle[i]));
		}
		
		
		// �������ݼ��ϣ����������
		Iterator<T> it = dataSet.iterator();
		int rowIndex = 0;	// 
		while(it.hasNext())
		{
			rowIndex++;
			row = sheet.createRow(rowIndex);
			T t = it.next();
			// ���÷��䣬����JavaBean���Ե��Ⱥ�˳�򣬶�̬����getXxx()�����õ�����ֵ
			// ��ȡ�������е�����(public��protected��default��private)�����������̳е����ԣ����� Field �����һ������
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++)
			{
				XSSFCell cellContent = row.createCell(i);
				cellContent.setCellStyle(styleContent);
				
				Field field = fields[i];
				// ��ȡ���Ե�����
				String fieldName = field.getName();
				// get����
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					//�����T���(��)Class������ͨ�����ص�Class�����ȡPerson�������Ϣ�����磺��ȡPerson�Ĺ��췽������������������Щ�ȵ���Ϣ��
					Class tClass = t.getClass();
					// getMethod�����ǻ�ö����������Ĺ�������
					Method getMethod = tClass.getMethod(getMethodName, new Class[]{});
					Object value = getMethod.invoke(t, new Object[]{});
					String textValue = null;
					//�ж�ֵ�����ͺ����ǿ������ת��
					if(value instanceof Boolean)
					{
						textValue = "��";
						if(!(Boolean)value)
							textValue = "��";
					}
					else if (value instanceof Date)
					{
						Date date = (Date) value;  
	                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
	                    textValue = sdf.format(date);
					}
					else //�����������Ͷ������ַ����򵥴���
					{
						textValue = value.toString();
					}
					//����������ʽ�ж�textValue�Ƿ�ȫ�����������
					if(textValue!=null)
					{
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");     
	                    Matcher matcher = p.matcher(textValue); 
	                    if(matcher.matches())//�����ֵ���double���� 
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
	                    		sheet.setColumnWidth(0, (short)(textValue.toString().getBytes().length * 256 + 400));	// ��һ�п��
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
		// д�������
		try {
			workbook.write(out);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	// ����
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
			
			String[] headerTitle = new String[]{"��װID", "λ��", "����", "�̵�", "��ӯ", "�̿�"};
			
			try {
				excelUtil.ExportExcel("����Excel����", headerTitle, list, new FileOutputStream("D:/Desktop/������/���ϵͳ����ʵ��/texts.xlsx"), ExportExcelUtil.EXCEl_FILE_2007);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ʧ��");
			}
	}*/
	
}
