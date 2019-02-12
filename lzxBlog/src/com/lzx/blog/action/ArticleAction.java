package com.lzx.blog.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.lzx.blog.service.ArticleService;
import com.lzx.blog.service.LoginService;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;



@Controller
public class ArticleAction extends ActionSupport {

	private String content;
	private String contentText;
	private String address;
	private String title;
	private String result;
	private int state;

	@Resource(name="articleService")
	private ArticleService service;
	
	//写文章
	public String postArticle(){	
		String[] returnStr = new String[3];
		returnStr = service.doWriteArticle(title,content, address,contentText);
		setResult(returnStr[0]);
		setAddress(returnStr[2]);
		return returnStr[1];
	}
	//写评论
	public String postReply(){	
		String[] returnStr = new String[3];
		returnStr = service.doWriteReply(content, address);
		setResult(returnStr[0]);
		setAddress(returnStr[2]);
		return returnStr[1];
	}
	//收藏
	public String postCollection(){
		String[] returnStr = new String[3];
		returnStr = service.doChangeCollection(state,address);
		setResult(returnStr[0]);
		return returnStr[1];
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContentText() {
		return contentText;
	}
	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
}
