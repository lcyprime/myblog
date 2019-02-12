package com.lzx.blog.action;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.lzx.blog.service.LoginService;
import com.opensymphony.xwork2.ActionSupport;


@Controller
public class LoginAction extends ActionSupport{

	private String usernum;
	private String password;
	private String result;
	
	@Resource(name="loginService")
	private LoginService service;
	
	public String login(){	
		String returnStr = "";
		returnStr = service.doLogin(usernum, password);
		this.result = service.getLoginTips();
		System.out.println(this.result);
		return returnStr;
	}
	public String cancellation(){
		ServletActionContext.getRequest().getSession().invalidate();
		setResult("ok");
		return "success";
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


}
