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
	
	// ���캯��
	public CheckTable(){
		this.title = new String[]{"�̵�����ID", "��װID", "λ��", "����", "�̵�", "��ӯ", "�̿�", "�̵�ʱ��"};
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

	// ���һ����¼
	public boolean addCheck(CheckTaskRecord taskRecord){
		// �Ƿ��Ѿ��̵��
		if(dataSet.contains(taskRecord))
		{
			dataSet.remove(taskRecord);		// ɾ���ɵ�����
			dataSet.add(taskRecord);			// �����µ�����
		}
		else
		{
			dataSet.add(taskRecord);
		}
		return true;
	}

	// ɾ��һ����¼
	public boolean deleteCheck(CheckTaskRecord taskRecord){
		dataSet.remove(taskRecord);
		return true;
	}
	
	
	// ���¡
	public Object deepClone() throws IOException, ClassNotFoundException
	{
		//������д������
		ByteArrayOutputStream bao=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(bao);
		oos.writeObject(this);
		
		//�����������ȡ��
		ByteArrayInputStream bis=new ByteArrayInputStream(bao.toByteArray());
		ObjectInputStream ois=new ObjectInputStream(bis);
		return(ois.readObject());
	}
	
	
	// ����
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
		System.out.println("������ݣ�");
		while (it.hasNext()) {
			System.out.println("����:" + it.next().getPosition());
		}
		
		checkTable.addCheck(check3);
		
		it = checkTable.getDataSet().iterator();
		System.out.println("����ظ����ݣ�");
		while (it.hasNext()) {
			System.out.println("����:" + it.next().toString());
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
			System.out.println("��¡������:" + it.next().toString());
		}
		
	}*/
}
