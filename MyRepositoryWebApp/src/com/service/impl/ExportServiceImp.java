package com.service.impl;

import java.io.OutputStream;
import java.util.LinkedHashSet;
import com.entity.Check;
import com.service.ExportService;
import com.util.ExportExcelUtil;

public class ExportServiceImp implements ExportService {

	@Override
	public void exportExcel(String title, String[] headerTitle, LinkedHashSet<Check> dataSet, OutputStream out, String version) {
		// TODO Auto-generated method stub

		// 创建导出工具类
		ExportExcelUtil<Check> excelUtil = new ExportExcelUtil<Check>();
		excelUtil.ExportExcel(title, headerTitle, dataSet, out, version);
	}

}
