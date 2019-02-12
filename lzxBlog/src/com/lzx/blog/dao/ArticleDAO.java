package com.lzx.blog.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.lzx.blog.entity.ArticleMessage;
import com.lzx.blog.entity.UserArticle;
import com.lzx.blog.entity.UserCollection;
import com.lzx.blog.entity.UserInfo;
import com.lzx.blog.entity.UserKey;



public class ArticleDAO extends HibernateDaoSupport{
	
	@Resource(name = "ALLDAO")
	private ALLDAO allDao;
	
	//把文章的信息写到数据库
	public Boolean doSaveArticle(Object userArticle,int userId){
		
		//更新最后登录时间
		UserInfo uInfo = new UserInfo();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String hql = "update UserInfo set updateLastTime=:updateLastTime where userId=:userId";
		uInfo.setUserId(userId);
		uInfo.setUpdateLastTime(time);	
		 allDao.doUpdate(hql, uInfo);
		//
		
		return allDao.doAdd(userArticle);
	}
	//把回复的信息写到数据库
	public boolean doSaveReply(Object articleMessage) {
		return allDao.doAdd(articleMessage);
	}
	//读取文章内容 根据文章内容id
	public List<?> doQueryArticleContent(String address) {
		String hql = "select u from UserArticle u where u.articleContentId='"+ address +"'";
		System.out.println("查询 文章内容 根据文章内容id");
		return allDao.doQuery(hql);
	}
	//读取回复内容 根据文章内容id
	public List<?> doQueryArticleMessage(String address) {
		String hql = "select am,ui.userLogo from ArticleMessage am,UserInfo ui where ui.userId=am.messageWriterId and am.msgForArticleId='"+ address +"' order by am.msgUpdateTime desc"; 		//升序排序 asc （默认升序）降序排序 desc
		System.out.println("查询 回复内容 根据文章内容id");
		return allDao.doQuery(hql);
	}
	//获取文章的全部内容 - 分表查询
	public List<?> doQueryArticleAll(int mark, int howMany) {
		String hql = "select ua,ui.userName from UserArticle ua,UserInfo ui where ua.articleWriterId=ui.userId order by ua.articleUpdateTime desc";
		System.out.println("查询 所有文章 根据时间 降序排列");
		return allDao.doQuery(hql, mark, howMany); 
	}
	//获取一个用户所有的文章
	public List<?> doQueryUserArticle(int page, int userID, int howMany) {
		String hql = "select ua,ui.userName from UserArticle ua,UserInfo ui where ua.articleWriterId=ui.userId and ua.articleWriterId="+userID+" order by ua.articleUpdateTime desc";
		System.out.println("查询 用户的所有文章 根据时间 降序排列");
		return allDao.doQuery(hql, page, howMany); 
	}
	//获取一个用户所有的收藏
	public List<?> doQueryUserCollection(int page, int userID, int howMany) {
		String hql = "select ua,ui.userName,uc from UserArticle ua,UserInfo ui,UserCollection uc where uc.articleContentId=ua.articleContentId and ua.articleWriterId=ui.userId and uc.collectionWriterId="+userID+" order by ua.articleUpdateTime desc";
		System.out.println("查询 用户的所有文章 根据时间 降序排列");
		return allDao.doQuery(hql, page, howMany); 
	}
	//收藏 查找用户收藏的文章
	public List<?> doQueryUserCollection(int userID) {
		String hql="select u from UserCollection u where u.collectionWriterId="+ userID +"";
		//查询收藏的文章
		return allDao.doQuery(hql);
	}
	//更新订阅状态
	public Boolean doChangeUserCollection(int id,int state,Timestamp time){
		UserCollection uc = new UserCollection();
		
		String hql = "update UserCollection uc set uc.collectionUpdateTime=:collectionUpdateTime , uc.collectionState=:collectionState where uc.id=:id";
		uc.setId(id);
		uc.setCollectionState(state + "");
		uc.setCollectionUpdateTime(time);
		return allDao.doUpdate(hql, uc);
	}
	//添加订阅记录
	public Boolean doAddUserCollection(Object ucollec){
		return allDao.doAdd(ucollec);
	}
}
