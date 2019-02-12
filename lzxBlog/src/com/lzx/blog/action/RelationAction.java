package com.lzx.blog.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.lzx.blog.service.RelationService;
import com.opensymphony.xwork2.ActionSupport;

public class RelationAction extends ActionSupport{
	
	private int writeID;
	private int state;
	private String result;
	private Map maps;
	
	@Resource(name="relationService")
	private RelationService service;
	
	public String relation_ChangeFollow(){
		String[] returnStr = new String[3];
		returnStr = service.doChangeFollow(state,writeID);
		setResult(returnStr[0]);
		return returnStr[1];
	}
	
	public String relation_LoadFollow(){
		Map m = new HashMap();
		m = service.doLoadFollow();
		setMaps(m);
		setResult(m.get("result").toString());
		return m.get("se").toString();
	}	
	
	public int getWriteID() {
		return writeID;
	}
	public void setWriteID(int writeID) {
		this.writeID = writeID;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Map getMaps() {
		return maps;
	}
	public void setMaps(Map maps) {
		this.maps = maps;
	}
	
}
