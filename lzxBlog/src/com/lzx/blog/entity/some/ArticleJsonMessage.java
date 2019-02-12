package com.lzx.blog.entity.some;

import java.sql.Timestamp;

public class ArticleJsonMessage {
	
	private int message_writer_id;	//回复人的id
	private String message_writer_name;	//通过id查名
	private String message_for_article_id;
	private String message_content;		//回复的内容
	private Timestamp time;
	
	
	public int getMessage_writer_id() {
		return message_writer_id;
	}
	public void setMessage_writer_id(int message_writer_id) {
		this.message_writer_id = message_writer_id;
	}
	public String getMessage_writer_name() {
		return message_writer_name;
	}
	public void setMessage_writer_name(String message_writer_name) {
		this.message_writer_name = message_writer_name;
	}
	public String getMessage_for_article_id() {
		return message_for_article_id;
	}
	public void setMessage_for_article_id(String message_for_article_id) {
		this.message_for_article_id = message_for_article_id;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	
}
