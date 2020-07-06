package com.util;

public class InportFactory {
	
	public static Inport produceInport(String fileType) throws Exception
	{
		if(fileType.equals("xls") || fileType.equals("xlsx"))
		{
			return new InportExcel();
		}
		else if (fileType.equals("xml"))
		{
			return new InportXml();
		}
		else
		{
			throw new Exception("û�и��ļ��ĵ��빦�ܣ�");
		}
	}
	
}
