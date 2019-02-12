package com.lzx.blog.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.lzx.blog.service.LoadUserInfoService;
import com.opensymphony.xwork2.ActionSupport;


@Controller
public class LoadUserInfoAction extends ActionSupport{

	private int writeID;
	private String address;
	private String result;
	private Map maps;
	
	@Resource(name="loadUserInfoService")
	private LoadUserInfoService service;
	
	public String userInfoDoLoadOfWrite(){
		Map m = new HashMap();
		m = service.doLoadWriteInfo(writeID,address);
		setMaps(m);
		setResult(m.get("result").toString());
		return m.get("se").toString();
	}
	
	public String userInfoDoLoadOfUser(){
		Map m = new HashMap();
		m = service.doLoadUserInfo(writeID);
		setMaps(m);
		setResult(m.get("result").toString());
		return m.get("se").toString();
	}
	
	public String userInfoDoSaveOfUser(){
		String strArray = result;
		String[] returnStr = new String[3];
		returnStr = service.doSaveUserInfo(strArray);
		setResult(returnStr[0]);
		return returnStr[1];
	}
	
	public int getWriteID() {
		return writeID;
	}
	public void setWriteID(int writeID) {
		this.writeID = writeID;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
