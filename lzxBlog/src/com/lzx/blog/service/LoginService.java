package com.lzx.blog.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.lzx.blog.dao.UserKeyDAO;
import com.lzx.blog.entity.UserInfo;
import com.lzx.blog.entity.UserKey;
import com.lzx.blog.util.CheckFormat;
import com.opensymphony.xwork2.ActionContext;
import com.sun.corba.se.spi.servicecontext.ServiceContext;

/**
 * @author 476832536@qq.com
 *
 */
@Service("loginService")
public class LoginService {

	@Resource(name = "UserKeyDAO")
	private UserKeyDAO dao;

	private String loginTips;


	public String doLogin(String usernum, String password) {
		
		String tipsStr = CheckFormat.check(usernum, password);	//	检查格式是否符合
		
		if (tipsStr=="ok" || tipsStr.equals("ok")) {
			
			List<?> list = dao.getAllExceptMe(usernum);	//通过用户名获取信息
			
			if (list != null && list.size() > 0) {
				
				UserKey user = (UserKey) list.get(0);
				String pwd = user.getUserPassword();
				String uNum = user.getUserNum();
				String myRole = user.getRole();

				if (pwd.equals(password) && dao.LastLoginTimeUpdate(usernum)) {
					
				Map sess = ActionContext.getContext().getSession();		//验证通过后把user信息放到session中	
				UserInfo uInfo= (UserInfo)dao.getUserInfoByUsernum(usernum).get(0);
				
	/*				ActionContext context = ActionContext.getContext();
					context.put("userInfo",uNum);*/
				//	sess.get(uNum);				
		//			context.put("", "");
					sess.put("userInfo",uInfo);
				//	sess.put(uNum, uInfo);
					setLoginTips(tipsStr);
					return "success";
				}
			}
			setLoginTips("用户名或密码错误");
			return "error";
		}else{
			setLoginTips(tipsStr);
			return "error";
		}
	}

	public String getLoginTips() {
		return loginTips;
	}

	public void setLoginTips(String loginTips) {
		this.loginTips = loginTips;
	}

}
