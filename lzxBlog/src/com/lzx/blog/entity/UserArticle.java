package com.lzx.blog.entity;

import java.sql.Timestamp;

/**
 * UserArticle entity. @author MyEclipse Persistence Tools
 */

public class UserArticle implements java.io.Serializable {

	// Fields

	private Integer articleId;
	private Integer articleWriterId;
	private String articleState;
	private Timestamp articleUpdateTime;
	private String articleTitle;
	private String articleContentText;
	private String articleContentId;

	// Constructors

	/** default constructor */
	public UserArticle() {
	}

	/** minimal constructor */
	public UserArticle(Integer articleWriterId, String articleState, String articleTitle, String articleContentId) {
		this.articleWriterId = articleWriterId;
		this.articleState = articleState;
		this.articleTitle = articleTitle;
		this.articleContentId = articleContentId;
	}

	/** full constructor */
	public UserArticle(Integer articleWriterId, String articleState, Timestamp articleUpdateTime, String articleTitle,
			String articleContentText, String articleContentId) {
		this.articleWriterId = articleWriterId;
		this.articleState = articleState;
		this.articleUpdateTime = articleUpdateTime;
		this.articleTitle = articleTitle;
		this.articleContentText = articleContentText;
		this.articleContentId = articleContentId;
	}

	// Property accessors

	public Integer getArticleId() {
		return this.articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getArticleWriterId() {
		return this.articleWriterId;
	}

	public void setArticleWriterId(Integer articleWriterId) {
		this.articleWriterId = articleWriterId;
	}

	public String getArticleState() {
		return this.articleState;
	}

	public void setArticleState(String articleState) {
		this.articleState = articleState;
	}

	public Timestamp getArticleUpdateTime() {
		return this.articleUpdateTime;
	}

	public void setArticleUpdateTime(Timestamp articleUpdateTime) {
		this.articleUpdateTime = articleUpdateTime;
	}

	public String getArticleTitle() {
		return this.articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleContentText() {
		return this.articleContentText;
	}

	public void setArticleContentText(String articleContentText) {
		this.articleContentText = articleContentText;
	}

	public String getArticleContentId() {
		return this.articleContentId;
	}

	public void setArticleContentId(String articleContentId) {
		this.articleContentId = articleContentId;
	}

}