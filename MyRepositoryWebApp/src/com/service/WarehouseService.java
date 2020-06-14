package com.service;

import java.util.List;

import com.mapper.ClothingInfo;
import com.mapper.OrderInfo;

public interface WarehouseService {
	
	public List<ClothingInfo> findAllById(String clothingID);				// ��ѯ��װ
	
	public ClothingInfo findById(String clothingID);						// ��ѯ��װ
	
	public ClothingInfo findByPosition(String shelves, String location);	//  ��ѯ��װ
	
	public boolean updateSetNumber(ClothingInfo c);							// ���²ֿ��װ����
	
	public List<ClothingInfo> findAllWithEmpty();							// ��ѯ�ֿ���Ϊ�����Ʒ�ʹ����Ʒ����Ϊ0����Ϣ
	
	public boolean updateSetIdAndNumber(ClothingInfo c);					// �ϼܸ���λ�õķ�װ������
}
