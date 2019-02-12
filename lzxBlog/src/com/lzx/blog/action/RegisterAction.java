package com.lzx.blog.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.lzx.blog.service.RegisterService;
import com.opensymphony.xwork2.ActionSupport;


@Controller
public class RegisterAction extends ActionSupport{

	private String usernum;
	private String password;
	private String rpassword;
	private String result;
	
	@Resource(name="registerService")
	private RegisterService service;
	
	public String execute(){	
		String returnStr = "";
		
		returnStr = service.doRegister(usernum, password, rpassword);
		this.result = service.getLoginTips();
		System.out.println(this.result);
		return returnStr;
	}
	

	
	public String getUsernum() {
		return usernum;
	}

	public void setUsernum(String usernum) {
		this.usernum = usernum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRpassword() {
		return rpassword;
	}

	public void setRpassword(String rpassword) {
		this.rpassword = rpassword;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


}
