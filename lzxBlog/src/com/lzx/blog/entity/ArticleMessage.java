package com.lzx.blog.entity;

import java.sql.Timestamp;


/**
 * ArticleMessage entity. @author MyEclipse Persistence Tools
 */

public class ArticleMessage implements java.io.Serializable {

	// Fields

	private Integer messageId;
	private Integer messageWriterId;
	private String messageWriterName;
	private String msgForArticleId;
	private String msgContent;
	private String msgState;

	
	private Timestamp msgUpdateTime;

	// Constructors

	/** default constructor */
	public ArticleMessage() {
	}

	/** minimal constructor */
	public ArticleMessage(Integer messageWriterId, String messageWriterName, String msgForArticleId, String msgContent,
			String msgState) {
		this.messageWriterId = messageWriterId;
		this.messageWriterName = messageWriterName;
		this.msgForArticleId = msgForArticleId;
		this.msgContent = msgContent;
		this.msgState = msgState;
	}

	/** full constructor */
	public ArticleMessage(Integer messageWriterId, String messageWriterName, String msgForArticleId, String msgContent,
			String msgState, Timestamp msgUpdateTime) {
		this.messageWriterId = messageWriterId;
		this.messageWriterName = messageWriterName;
		this.msgForArticleId = msgForArticleId;
		this.msgContent = msgContent;
		this.msgState = msgState;
		this.msgUpdateTime = msgUpdateTime;
	}

	// Property accessors

	public Integer getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getMessageWriterId() {
		return this.messageWriterId;
	}

	public void setMessageWriterId(Integer messageWriterId) {
		this.messageWriterId = messageWriterId;
	}

	public String getMessageWriterName() {
		return this.messageWriterName;
	}

	public void setMessageWriterName(String messageWriterName) {
		this.messageWriterName = messageWriterName;
	}

	public String getMsgForArticleId() {
		return this.msgForArticleId;
	}

	public void setMsgForArticleId(String msgForArticleId) {
		this.msgForArticleId = msgForArticleId;
	}

	public String getMsgContent() {
		return this.msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgState() {
		return this.msgState;
	}

	public void setMsgState(String msgState) {
		this.msgState = msgState;
	}

	public Timestamp getMsgUpdateTime() {
		return this.msgUpdateTime;
	}

	public void setMsgUpdateTime(Timestamp msgUpdateTime) {
		this.msgUpdateTime = msgUpdateTime;
	}

}