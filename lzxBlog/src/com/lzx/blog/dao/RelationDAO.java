package com.lzx.blog.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.lzx.blog.entity.UserCollection;
import com.lzx.blog.entity.UserRelationship;

public class RelationDAO  extends HibernateDaoSupport{
	
	@Resource(name = "ALLDAO")
	private ALLDAO allDao;
		//doQueryUserCollection(userId);
	//关注 查找用户关注的文章
	public List<?> doQueryUserFollow(int userID) {
		String hql="select u from UserRelationship u where u.userId1="+ userID +"";
		//查询关注的文章
		return allDao.doQuery(hql);
	}
	//更新关注状态
	public Boolean doChangeUserFollow(int id,int state,Timestamp time){
		UserRelationship ur = new UserRelationship();
		String hql = "update UserRelationship ur set ur.followUpdateTime=:followUpdateTime , ur.userIsFollow=:userIsFollow where ur.id=:id";
		ur.setId(id);
		ur.setUserIsFollow(state + "");
		ur.setFollowUpdateTime(time);
		return allDao.doUpdate(hql, ur);
	}
	//添加关注记录
	public Boolean doAddUserFollow(Object ucollec){
		return allDao.doAdd(ucollec);
	}
	//关注 查找用户关注的文章作者信息
	public List<?> doQueryFollowUserInfo(int userId) {
		String hql="select ui from UserRelationship ur,UserInfo ui where ur.userId1="+ userId +" and ur.userId2=ui.userId";
		//查询关注的文章作者信息
		return allDao.doQuery(hql);
	}
}
