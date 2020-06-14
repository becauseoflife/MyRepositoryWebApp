package com.mapper;

import java.util.Date;

/* 用户实体类 */
public class UserInfo {

	private String userName;	// 账号
	
	private String password;	// 密码
	
	private String telephone;	// 电话
	
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

	
	public UserInfo() {}

	public UserInfo(String userName, String password, String telephone, String email, Date regDate) {
		this.userName = userName;
		this.password = password;
		this.telephone = telephone;
		this.email = email;
		this.regDate = regDate;
	}
	
	public static UserInfo builder(){
		return new UserInfo();
	}
	
	public UserInfo userName(String userName){
		this.userName = userName;
		return this;
	}
	
	public UserInfo password(String password){
		this.password = password;
		return this;
	}
	
	public UserInfo telephone(String telephone){
		this.telephone = telephone;
		return this;
	}
	
	public UserInfo email(String email){
		this.email = email;
		return this;
	}
	
	public UserInfo regDate(Date regDate){
		this.regDate = regDate;
		return this;
	}
	
	public UserInfo build(){
		return new UserInfo(userName, password, telephone, email, regDate);
	}
	
	
}
