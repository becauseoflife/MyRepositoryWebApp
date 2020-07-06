package com.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;

import com.mapper.CheckTaskRecord;

public class CheckTable implements Serializable
{

	private String[] title;
	private LinkedHashSet<CheckTaskRecord> dataSet;
	
	// 构造函数
	public CheckTable(){
		this.title = new String[]{"盘点任务ID", "服装ID", "位置", "数量", "盘点", "盘盈", "盘亏", "盘点时间"};
		dataSet = new LinkedHashSet<CheckTaskRecord>();
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public LinkedHashSet<CheckTaskRecord> getDataSet() {
		return dataSet;
	}

	public void setDataSet(LinkedHashSet<CheckTaskRecord> dataSet) {
		this.dataSet = dataSet;
	}

	// 添加一条记录
	public boolean addCheck(CheckTaskRecord taskRecord){
		// 是否已经盘点过
		if(dataSet.contains(taskRecord))
		{
			dataSet.remove(taskRecord);		// 删除旧的数据
			dataSet.add(taskRecord);			// 新增新的数据
		}
		else
		{
			dataSet.add(taskRecord);
		}
		return true;
	}

	// 删除一条记录
	public boolean deleteCheck(CheckTaskRecord taskRecord){
		dataSet.remove(taskRecord);
		return true;
	}
	
	
	// 深克隆
	public Object deepClone() throws IOException, ClassNotFoundException
	{
		//将对象写入流中
		ByteArrayOutputStream bao=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(bao);
		oos.writeObject(this);
		
		//将对象从流中取出
		ByteArrayInputStream bis=new ByteArrayInputStream(bao.toByteArray());
		ObjectInputStream ois=new ObjectInputStream(bis);
		return(ois.readObject());
	}
	
	
	// 测试
/*	public static void main(String[] args) {

		CheckTable checkTable = new CheckTable();
		CheckTaskRecord check1 = new CheckTaskRecord(
				"1","4a507db7-7c52-2c22-d6a6-77ade48625bd",
				"A-5", 9, 10, 1, 0, new Date());
		CheckTaskRecord check2 = new CheckTaskRecord(
				"1","4a507db7-7c52-2c22-d6a6-77ade48625bd",
				"A-6", 9, 10, 1, 0, new Date());
		CheckTaskRecord check3 = new CheckTaskRecord(
				"1","4a507db7-7c52-2c22-d6a6-77ade48625bd",
				"A-5", 9, 10, 1, 0, new Date());
		
		checkTable.addCheck(check1);
		checkTable.addCheck(check2);
		
		Iterator<CheckTaskRecord> it = checkTable.getDataSet().iterator();
		System.out.println("添加数据：");
		while (it.hasNext()) {
			System.out.println("数据:" + it.next().getPosition());
		}
		
		checkTable.addCheck(check3);
		
		it = checkTable.getDataSet().iterator();
		System.out.println("添加重复数据：");
		while (it.hasNext()) {
			System.out.println("数据:" + it.next().toString());
		}
		
		CheckTable cloneCheckTable = null;
		try {
			cloneCheckTable = (CheckTable) checkTable.deepClone();
			it = cloneCheckTable.getDataSet().iterator();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (it.hasNext()) {
			System.out.println("克隆的数据:" + it.next().toString());
		}
		
	}*/
}
