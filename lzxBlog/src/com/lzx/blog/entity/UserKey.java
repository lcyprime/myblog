package com.lzx.blog.entity;

/**
 * UserKey entity. @author MyEclipse Persistence Tools
 */

public class UserKey implements java.io.Serializable {

	// Fields

	private Integer userId;
	private String userNum;
	private String userPassword;
	private String role;

	// Constructors

	/** default constructor */
	public UserKey() {
	}

	/** full constructor */
	public UserKey(String userNum, String userPassword) {
		this.userNum = userNum;
		this.userPassword = userPassword;
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

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}