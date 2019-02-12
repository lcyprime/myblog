package com.lzx.blog.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.lzx.blog.dao.ALLDAO;
import com.lzx.blog.entity.UserInfo;
import com.lzx.blog.entity.UserKey;


public class UserKeyDAO extends HibernateDaoSupport{
	
//	private static final Logger log = LoggerFactory.getLogger(ALLDAO.class);
	
	@Resource(name = "ALLDAO")
	private ALLDAO allDao;
	
	//根据usernum获取UserKey表的全部信息
	public List<?> getAllExceptMe(String usernum){		
		String hql = "select u from UserKey u where u.userNum='"+ usernum +"'";	
		System.out.println("查询 根据usernum获取UserKey表的全部信息");
		return allDao.doQuery(hql);
	}
	
	//根据usernum获取UserKey表的用户账号
	public List<?> checkUserNumHave(String usernum){
		String hql = "select u.userNum from UserKey u where u.userNum='"+ usernum +"'";
		System.out.println("查询 根据usernum获取UserKey表的用户账号");
		return allDao.doQuery(hql);
	}
	
	//根据usernum获取UserInfo表的全部信息
		public List<?> getUserInfoByUsernum(String usernum){
			String hql = "select u from UserInfo u where u.userNum='"+ usernum +"'";	
			System.out.println("查询 根据usernum获取UserInfo表的全部信息");
			return allDao.doQuery(hql);
	}
	
	//注册 - 写入用户账号·用户密码·用户类型
	public Boolean doRegisterSave(String usernum, String password){
		UserKey ukey = new UserKey();
		ukey.setUserNum(usernum);
		ukey.setUserPassword(password);
		ukey.setRole("user");
		return allDao.doAdd(ukey);
	}
		//更新最后登录时间
	public Boolean LastLoginTimeUpdate(String usernum){
		UserInfo uInfo = new UserInfo();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String hql = "update UserInfo uif set uif.loginLastTime=:loginLastTime where uif.userNum=:userNum";
		uInfo.setUserNum(usernum);
		uInfo.setLoginLastTime(time);	
		return allDao.doUpdate(hql, uInfo);
	}
}
