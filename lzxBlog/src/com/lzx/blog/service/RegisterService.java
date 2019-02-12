package com.lzx.blog.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lzx.blog.dao.UserKeyDAO;
import com.lzx.blog.util.CheckFormat;

/**
 * @author 476832536@qq.com
 *
 */
@Service("registerService")
public class RegisterService {

	@Resource(name = "UserKeyDAO")
	private UserKeyDAO dao;

	private String loginTips;

	public String doRegister(String usernum, String password, String rpassword) {
		
		String tipsStr = CheckFormat.check(usernum, password, rpassword);	//	检查格式是否符合
		
		if (tipsStr == "ok" || tipsStr.equals("ok")) {
			List<?> list = dao.checkUserNumHave(usernum);	//判断用户账号 是否存在/是否已注册
			if (list != null && list.size() > 0) {
				setLoginTips("用户名已注册");
				return "success";
			}
			if(dao.doRegisterSave(usernum,password)){	//判断是否增加用户成功
				setLoginTips("ok");
				return "success";	
			};
		}
		setLoginTips(tipsStr);
		return "success";
	}

	public String getLoginTips() {
		return loginTips;
	}

	public void setLoginTips(String loginTips) {
		this.loginTips = loginTips;
	}

}
