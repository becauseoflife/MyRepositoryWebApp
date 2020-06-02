package com.entity;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class CheckTable {

	private String[] title;
	private LinkedHashSet<Check> dataSet;
	
	// ���캯��
	public CheckTable(){
		this.title = new String[]{"��װID", "λ��", "����", "�̵�", "��ӯ", "�̿�"};
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

	// ���һ����¼
	public boolean addCheck(Check check){
		// �Ƿ��Ѿ��̵��
		if(dataSet.contains(check))
		{
			dataSet.remove(check);		// ɾ���ɵ�����
			dataSet.add(check);			// �����µ�����
		}
		else
		{
			dataSet.add(check);
		}
		return true;
	}

	// ɾ��һ����¼
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
		System.out.println("������ݣ�");
		while (it.hasNext()) {
			System.out.println("����:" + it.next().getLocation());
		}
		
		checkTable.addCheck(check3);
		
		it = checkTable.getDataSet().iterator();
		System.out.println("����ظ����ݣ�");
		while (it.hasNext()) {
			System.out.println("����:" + it.next().toString());
		}
	}*/
}
