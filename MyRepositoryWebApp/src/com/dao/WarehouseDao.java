package com.dao;

import java.util.List;

import com.mapper.ClothingInfo;

/* �ֿ����ݿ����� */
public interface WarehouseDao {

	public List<ClothingInfo> findAllById(String clothingID);				//	��ѯ��װ�б�
	public ClothingInfo findById(String clothingID);						//  ��ѯ��װ
	public ClothingInfo findByPosition(String shelves, String location);	//  ��ѯ��װ
	
	public boolean updateSetNumber(ClothingInfo c);							// ���·�װ
	
	public List<ClothingInfo> findAllWithEmpty();							// ��ѯ�ֿ���Ϊ�����Ʒ�ʹ����Ʒ����Ϊ0����Ϣ
	public boolean updateSetIdAndNumber(ClothingInfo c);					// �ϼܸ���λ�õķ�װ������
	
}
