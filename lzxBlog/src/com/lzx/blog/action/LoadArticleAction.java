package com.lzx.blog.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import com.lzx.blog.service.LoadArticleService;
import com.opensymphony.xwork2.ActionSupport;

public class LoadArticleAction  extends ActionSupport {
	
	private String address;
	private String result;
	private Map maps;
	
	private int page;
	private int mark;
	private int howMany; 
	
	@Resource(name="loadArticleService")
	private LoadArticleService service;
	//加载文章内容
	public String loadArticle(){
		Map m = new HashMap();
		m = service.doLoadArticle(address);
		
		setMaps(m);
		setResult(m.get("result").toString());
		return m.get("se").toString();
	}
	//获取全部的文章
	public String loadArticle_list(){
		Map m = new HashMap();
		m = service.doLoadArticleList(mark,howMany);	//mark读取  howMany一次读取的数量
		
		setMaps(m);
		setResult(m.get("result").toString());
		return m.get("se").toString();
	}
	//获取特定用户的文章
	public String loadUserArticle_list(){
		Map m = new HashMap();
		m = service.doLoadUserArticleList(page,mark,howMany);	//此时 mark是userId,howMany一次读取的数量,page当前页码 起始为0
	
		setMaps(m);
		setResult(m.get("result").toString());
		return m.get("se").toString();
	}
	
	//获取特定用户的收藏
	public String loadUserCollection_list(){
		Map m = new HashMap();
		m = service.doLoadUserCollectionList(page,mark,howMany);	//此时 mark是userId,howMany一次读取的数量,page当前页码 起始为0
	
		setMaps(m);
		setResult(m.get("result").toString());
		return m.get("se").toString();
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
	public Map getMaps() {
		return maps;
	}
	public void setMaps(Map maps) {
		this.maps = maps;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public int getHowMany() {
		return howMany;
	}
	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}
	
	
}
