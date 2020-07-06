package com.service;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.VoiceStatus;

import com.entity.Cart;
import com.entity.Check;
import com.entity.Order;
import com.entity.SearchResult;
import com.mapper.CheckTaskRecord;
import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;
import com.mapper.OrderItemInfo;
import com.mapper.UserInfo;

public interface ExportService {
	
	public void exportExcel(String title, 
			String[] headerTitle, 
			LinkedHashSet<CheckTaskRecord> dataSet, 
			OutputStream out, 
			String version);// 导出报表

}
