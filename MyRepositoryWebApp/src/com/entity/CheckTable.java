package com.entity;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class CheckTable {

	private String[] title;
	private LinkedHashSet<Check> dataSet;
	
	// 构造函数
	public CheckTable(){
		this.title = new String[]{"服装ID", "位置", "数量", "盘点", "盘盈", "盘亏"};
		dataSet = new LinkedHashSet<Check>();
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public LinkedHashSet<Check> getDataSet() {
		return dataSet;
	}

	public void setDataSet(LinkedHashSet<Check> dataSet) {
		this.dataSet = dataSet;
	}

	// 添加一条记录
	public boolean addCheck(Check check){
		// 是否已经盘点过
		if(dataSet.contains(check))
		{
			dataSet.remove(check);		// 删除旧的数据
			dataSet.add(check);			// 新增新的数据
		}
		else
		{
			dataSet.add(check);
		}
		return true;
	}

	// 删除一条记录
	public boolean deleteCheck(Check check){
		dataSet.remove(check);
		return true;
	}
	
/*	public static void main(String[] args) {

		CheckTable checkTable = new CheckTable();
		Check check1 = new Check(
				"4a507db7-7c52-2c22-d6a6-77ade48625bd",
				"A-5", 9, 10, 1, 0);
		Check check2 = new Check(
				"4a507db7-7c52-2c22-d6a6-77ade48625bd",
				"A-6", 9, 10, 1, 0);
		Check check3 = new Check(
				"4a507db7-7c52-2c22-d6a6-77ade48625bd",
				"A-5", 9, 8, 0, 1);
		
		checkTable.addCheck(check1);
		checkTable.addCheck(check2);
		
		Iterator<Check> it = checkTable.getDataSet().iterator();
		System.out.println("添加数据：");
		while (it.hasNext()) {
			System.out.println("数据:" + it.next().getLocation());
		}
		
		checkTable.addCheck(check3);
		
		it = checkTable.getDataSet().iterator();
		System.out.println("添加重复数据：");
		while (it.hasNext()) {
			System.out.println("数据:" + it.next().toString());
		}
	}*/
}
