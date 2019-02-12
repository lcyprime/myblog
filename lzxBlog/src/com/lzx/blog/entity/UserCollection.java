package com.lzx.blog.entity;

import java.sql.Timestamp;

/**
 * UserCollection entity. @author MyEclipse Persistence Tools
 */

public class UserCollection implements java.io.Serializable {

	// Fields

	private Integer id;
	private String articleContentId;
	private Integer collectionWriterId;
	private String collectionState;
	private Timestamp collectionUpdateTime;

	// Constructors

	/** default constructor */
	public UserCollection() {
	}

	/** minimal constructor */
	public UserCollection(String articleContentId, Integer collectionWriterId, String collectionState) {
		this.articleContentId = articleContentId;
		this.collectionWriterId = collectionWriterId;
		this.collectionState = collectionState;
	}

	/** full constructor */
	public UserCollection(String articleContentId, Integer collectionWriterId, String collectionState,
			Timestamp collectionUpdateTime) {
		this.articleContentId = articleContentId;
		this.collectionWriterId = collectionWriterId;
		this.collectionState = collectionState;
		this.collectionUpdateTime = collectionUpdateTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getArticleContentId() {
		return this.articleContentId;
	}

	public void setArticleContentId(String articleContentId) {
		this.articleContentId = articleContentId;
	}

	public Integer getCollectionWriterId() {
		return this.collectionWriterId;
	}

	public void setCollectionWriterId(Integer collectionWriterId) {
		this.collectionWriterId = collectionWriterId;
	}

	public String getCollectionState() {
		return this.collectionState;
	}

	public void setCollectionState(String collectionState) {
		this.collectionState = collectionState;
	}

	public Timestamp getCollectionUpdateTime() {
		return this.collectionUpdateTime;
	}

	public void setCollectionUpdateTime(Timestamp collectionUpdateTime) {
		this.collectionUpdateTime = collectionUpdateTime;
	}

}