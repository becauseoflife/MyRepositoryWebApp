package com.entity;

import java.util.Date;

/* �û�ʵ���� */
public class User {

	private String userName;	// �˺�
	
	private String password;	// ����
	
	private String telephone;	// �绰
	
	private String email;		// �����ʼ�
	
	private Date regDate;		// ע��ʱ��

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telphone) {
		this.telephone = telphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
}
