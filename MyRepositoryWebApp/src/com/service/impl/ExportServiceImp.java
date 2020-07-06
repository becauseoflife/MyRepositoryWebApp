package com.service.impl;

import java.io.OutputStream;
import java.util.LinkedHashSet;
import com.entity.Check;
import com.mapper.CheckTaskRecord;
import com.service.ExportService;
import com.util.ExportExcelUtil;

public class ExportServiceImp implements ExportService {

	@Override
	public void exportExcel(String title, String[] headerTitle, LinkedHashSet<CheckTaskRecord> dataSet, OutputStream out, String version) {
		// TODO Auto-generated method stub

		// 创建导出工具类
		ExportExcelUtil<CheckTaskRecord> excelUtil = new ExportExcelUtil<CheckTaskRecord>();
		excelUtil.ExportExcel(title, headerTitle, dataSet, out, version);
	}

}
