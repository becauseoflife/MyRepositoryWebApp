package com.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {

	public boolean login(HttpServletRequest request);  		// �û���¼
	public boolean register(HttpServletRequest request);	// �û�ע��
	
	public void queryClothing(HttpServletRequest request);	// ��ѯ��װλ�ú�����
	
	public void addToCart(HttpServletRequest request, HttpServletResponse response);		// �����Ʒ��������
	public void deleteFormCart(HttpServletRequest request, HttpServletResponse response);	// �Ӷ�����ɾ����Ʒ
	public void createOrder(HttpServletRequest request, HttpServletResponse response);		// ��������
}
