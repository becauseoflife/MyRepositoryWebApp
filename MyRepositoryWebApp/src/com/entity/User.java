package com.entity;

import java.sql.Date;

/* �û�ʵ���� */
public class User {

	private String userName;	// �˺�
	
	private String password;	// ����
	
	private String telphone;	// �绰
	
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

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
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
