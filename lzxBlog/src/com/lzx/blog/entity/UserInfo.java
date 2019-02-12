package com.lzx.blog.entity;

import java.sql.Timestamp;

/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */

public class UserInfo implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String userNum;
	private String userName;
	private String sex;
	private String userLogo;
	private String email;
	private String telephone;
	private String addressNow;
	private String addressHome;
	private String educational;
	private String job;
	private String introduce;
	private String hobby;
	private String birthday;
	private Timestamp registerTime;
	private Timestamp loginLastTime;
	private Timestamp updateLastTime;

	// Constructors

	/** default constructor */
	public UserInfo() {
	}

	/** minimal constructor */
	public UserInfo(String userNum, String userName, Timestamp registerTime) {
		this.userNum = userNum;
		this.userName = userName;
		this.registerTime = registerTime;
	}

	/** full constructor */
	public UserInfo(String userNum, String userName, String sex, String userLogo, String email, String telephone,
			String addressNow, String addressHome, String educational, String job, String introduce, String hobby,
			String birthday, Timestamp registerTime, Timestamp loginLastTime, Timestamp updateLastTime) {
		this.userNum = userNum;
		this.userName = userName;
		this.sex = sex;
		this.userLogo = userLogo;
		this.email = email;
		this.telephone = telephone;
		this.addressNow = addressNow;
		this.addressHome = addressHome;
		this.educational = educational;
		this.job = job;
		this.introduce = introduce;
		this.hobby = hobby;
		this.birthday = birthday;
		this.registerTime = registerTime;
		this.loginLastTime = loginLastTime;
		this.updateLastTime = updateLastTime;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserNum() {
		return this.userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserLogo() {
		return this.userLogo;
	}

	public void setUserLogo(String userLogo) {
		this.userLogo = userLogo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddressNow() {
		return this.addressNow;
	}

	public void setAddressNow(String addressNow) {
		this.addressNow = addressNow;
	}

	public String getAddressHome() {
		return this.addressHome;
	}

	public void setAddressHome(String addressHome) {
		this.addressHome = addressHome;
	}

	public String getEducational() {
		return this.educational;
	}

	public void setEducational(String educational) {
		this.educational = educational;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getHobby() {
		return this.hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Timestamp getRegisterTime() {
		return this.registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public Timestamp getLoginLastTime() {
		return this.loginLastTime;
	}

	public void setLoginLastTime(Timestamp loginLastTime) {
		this.loginLastTime = loginLastTime;
	}

	public Timestamp getUpdateLastTime() {
		return this.updateLastTime;
	}

	public void setUpdateLastTime(Timestamp updateLastTime) {
		this.updateLastTime = updateLastTime;
	}

}