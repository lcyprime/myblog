package com.lzx.blog.entity;

import java.sql.Timestamp;

/**
 * UserInfoBgimg entity. @author MyEclipse Persistence Tools
 */

public class UserInfoBgimg implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private String bgimg;
	private Timestamp changeTime;

	// Constructors

	/** default constructor */
	public UserInfoBgimg() {
	}

	/** minimal constructor */
	public UserInfoBgimg(Integer userId) {
		this.userId = userId;
	}

	/** full constructor */
	public UserInfoBgimg(Integer userId, String bgimg, Timestamp changeTime) {
		this.userId = userId;
		this.bgimg = bgimg;
		this.changeTime = changeTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getBgimg() {
		return this.bgimg;
	}

	public void setBgimg(String bgimg) {
		this.bgimg = bgimg;
	}

	public Timestamp getChangeTime() {
		return this.changeTime;
	}

	public void setChangeTime(Timestamp changeTime) {
		this.changeTime = changeTime;
	}

}