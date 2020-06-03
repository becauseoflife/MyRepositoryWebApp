package com.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.VoiceStatus;

public interface UserService {

	public boolean login(HttpServletRequest request);  		// �û���¼
	public boolean register(HttpServletRequest request);	// �û�ע��
	
	public boolean queryClothingById(HttpServletRequest request);	// ��ѯ��װλ�ú�����
	
	public void addToCart(HttpServletRequest request, HttpServletResponse response);		// �����Ʒ��������
	public void deleteFormCart(HttpServletRequest request, HttpServletResponse response);	// �Ӷ�����ɾ����Ʒ
	public void createOrder(HttpServletRequest request, HttpServletResponse response);		// ��������
	
	public void getOrderSelectOptions(HttpServletRequest request, HttpServletResponse response);	// ��ȡδ�������б�
	public void getOrderInfo(HttpServletRequest request, HttpServletResponse response);				// ��ȡ��������
	public void pickGood(HttpServletRequest request, HttpServletResponse response);					// �������
	public void orderResolved(HttpServletRequest request, HttpServletResponse response);			// ���������
	
	public void queryClothingByLocation(HttpServletRequest request, HttpServletResponse response);	// �̵��ѯ
	public void updateClothingNumber(HttpServletRequest request, HttpServletResponse response);		// �̵����
	public void deleteFormCheckTable(HttpServletRequest request, HttpServletResponse response);		// �̵�����ɾ��
	public boolean exportExcel(HttpServletRequest request, HttpServletResponse response);				// ��������
	
	public void outLogin(HttpServletRequest request, HttpServletResponse response);		// �˳���¼
	
	public void getEmptyPositon(HttpServletRequest request, HttpServletResponse response);	// ����ϼܵĿ�λ��
	public void putOnGood(HttpServletRequest request, HttpServletResponse response);		// ��Ʒ�ϼ�
}
