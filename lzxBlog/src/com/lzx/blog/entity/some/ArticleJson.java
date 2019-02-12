package com.lzx.blog.entity.some;

import java.util.ArrayList;

public class ArticleJson {
	
	private int article_writer_id;	//文章作者id
	private String article_title;	//文章标题
	private String article_content_id;  //文章的 json名
	private String article_content;		//文章内容
	
	public int getArticle_writer_id() {
		return article_writer_id;
	}
	public void setArticle_writer_id(int article_writer_id) {
		this.article_writer_id = article_writer_id;
	}
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public String getArticle_content_id() {
		return article_content_id;
	}
	public void setArticle_content_id(String article_content_id) {
		this.article_content_id = article_content_id;
	}
	public String getArticle_content() {
		return article_content;
	}
	public void setArticle_content(String article_content) {
		this.article_content = article_content;
	}


}
