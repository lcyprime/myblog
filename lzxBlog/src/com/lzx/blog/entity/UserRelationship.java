package com.lzx.blog.entity;

import java.sql.Timestamp;

/**
 * UserRelationship entity. @author MyEclipse Persistence Tools
 */

public class UserRelationship implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId1;
	private Integer userId2;
	private String userIsFriend;
	private Timestamp friendUpdateTime;
	private String userIsFollow;
	private Timestamp followUpdateTime;

	// Constructors

	/** default constructor */
	public UserRelationship() {
	}

	/** minimal constructor */
	public UserRelationship(Integer userId1, Integer userId2) {
		this.userId1 = userId1;
		this.userId2 = userId2;
	}

	/** full constructor */
	public UserRelationship(Integer userId1, Integer userId2, String userIsFriend, Timestamp friendUpdateTime,
			String userIsFollow, Timestamp followUpdateTime) {
		this.userId1 = userId1;
		this.userId2 = userId2;
		this.userIsFriend = userIsFriend;
		this.friendUpdateTime = friendUpdateTime;
		this.userIsFollow = userIsFollow;
		this.followUpdateTime = followUpdateTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId1() {
		return this.userId1;
	}

	public void setUserId1(Integer userId1) {
		this.userId1 = userId1;
	}

	public Integer getUserId2() {
		return this.userId2;
	}

	public void setUserId2(Integer userId2) {
		this.userId2 = userId2;
	}

	public String getUserIsFriend() {
		return this.userIsFriend;
	}

	public void setUserIsFriend(String userIsFriend) {
		this.userIsFriend = userIsFriend;
	}

	public Timestamp getFriendUpdateTime() {
		return this.friendUpdateTime;
	}

	public void setFriendUpdateTime(Timestamp friendUpdateTime) {
		this.friendUpdateTime = friendUpdateTime;
	}

	public String getUserIsFollow() {
		return this.userIsFollow;
	}

	public void setUserIsFollow(String userIsFollow) {
		this.userIsFollow = userIsFollow;
	}

	public Timestamp getFollowUpdateTime() {
		return this.followUpdateTime;
	}

	public void setFollowUpdateTime(Timestamp followUpdateTime) {
		this.followUpdateTime = followUpdateTime;
	}

}