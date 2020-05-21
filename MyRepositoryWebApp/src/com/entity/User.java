package com.entity;

import java.sql.Date;

/* 用户实体类 */
public class User {

	private String userName;	// 账号
	
	private String password;	// 密码
	
	private String telphone;	// 电话
	
	private String email;		// 电子邮件
	
	private Date regDate;		// 注册时间

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
