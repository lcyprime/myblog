package com.lzx.blog.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lzx.blog.dao.RelationDAO;
import com.lzx.blog.dao.SomeInfoDAO;
import com.lzx.blog.entity.UserCollection;
import com.lzx.blog.entity.UserInfo;
import com.lzx.blog.entity.UserRelationship;
import com.opensymphony.xwork2.ActionContext;


@Service("relationService")
public class RelationService {
	
	@Resource(name = "RelationDAO")
	private RelationDAO dao;
	
	@Resource(name = "SomeInfoDAO")
	private SomeInfoDAO someInfo;	

	//更改关注状态
	public String[] doChangeFollow(int state, int writeID) {
		String[] info = new String[3];
		info[0]="no"; info[1]="error";
		Map sess = ActionContext.getContext().getSession();
		UserInfo uInfo= (UserInfo)sess.get("userInfo");	
		
		if(uInfo == null){ info[0]="not"; return info;};
		
		int userId = uInfo.getUserId();	//关注人的id	
		
		UserRelationship userRelationship;
		List<?> list = dao.doQueryUserFollow(userId);
		
		Timestamp time = new Timestamp(System.currentTimeMillis());
		
		if (list != null && list.size() > 0){
			for(int i=0 ; i<list.size();i++){
				userRelationship = (UserRelationship) list.get(i);
				if(writeID == userRelationship.getUserId2()){
					if(dao.doChangeUserFollow(userRelationship.getId(), state, time)){
						info[0]="ok";info[1]="success";
						System.out.println("更新关注成功" + state);
						return info;
					};
				};
			};
		};	
		userRelationship = new UserRelationship();
		userRelationship.setUserId1(userId);
		userRelationship.setUserId2(writeID);
		userRelationship.setUserIsFollow(state + "");
		userRelationship.setFollowUpdateTime(time);
			if(dao.doAddUserFollow(userRelationship)){
				info[0]="ok";info[1]="success";
				System.out.println("添加关注成功" + state);
			};
		return info;
	}
	//读取是否已关注
	public Map<String, Object> doUserIsFollow(String address) {
	
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("result", "no"); //初始化
		m.put("se","error");
		
		Map sess = ActionContext.getContext().getSession();
		UserInfo uInfo= (UserInfo)sess.get("userInfo");
		m.put("state", 0);
		if(uInfo != null){
			int userId = uInfo.getUserId();
			String someKey = "collectionWriterId="+userId + " and articleContentId='"+address+"'";
			List list = someInfo.doQueryAllInfoBySomeKey("UserCollection","collectionState", someKey);
		//	System.out.println(list.size() + " - this list - " + list.get(0).toString());
			if(list !=null && list.size()>0){
				int uc =  Integer.parseInt(list.get(0).toString());
				System.out.println(uc);
				m.put("state", uc);
			}
		}
		
		m.put("se","success");	//全部成功后
		m.put("result", "ok");		
		return m;
	}
	public Map<String, Object> doLoadFollow() {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("result", "no"); //初始化
		m.put("se","error");
		
		Map sess = ActionContext.getContext().getSession();
		UserInfo uInfo= (UserInfo)sess.get("userInfo");
		if(uInfo != null){
			int userId = uInfo.getUserId();
			List<?> list = dao.doQueryFollowUserInfo(userId);
			if (list != null && list.size() > 0){
				m.put("followList", list);		//把整个查询结果以list<ArticleMessage>的形式保存在map里
				m.put("result", "ok");
				
			}
		}
		m.put("se","success");	//全部成功后
		return m;
	}
	
}
