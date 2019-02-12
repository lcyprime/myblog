package com.lzx.blog.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lzx.blog.dao.ArticleDAO;
import com.lzx.blog.dao.SomeInfoDAO;
import com.lzx.blog.entity.ArticleMessage;
import com.lzx.blog.entity.UserArticle;
import com.lzx.blog.entity.UserCollection;
import com.lzx.blog.entity.UserInfo;
import com.lzx.blog.entity.some.ArticleJson;
import com.lzx.blog.util.CreateFileUtil;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import net.sf.json.JsonConfig;

@Service("loadArticleService")
public class LoadArticleService {
	
	@Resource(name = "ArticleDAO")
	private ArticleDAO dao;
	
	@Resource(name = "SomeInfoDAO")
	private SomeInfoDAO someInfo;
	
	//读取文章内容和回复内容	
	public Map<String, Object> doLoadArticle(String address) {
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//读取文章
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("result", "no"); //初始化
		m.put("se","error");
		List<?> listContent = dao.doQueryArticleContent(address);	//通过文章地址获取信息
		
		if (listContent == null || listContent.size() <= 0){ return m;}
		
		UserArticle userArticle = (UserArticle) listContent.get(0);	//存放文章的信息

		String artState = userArticle.getArticleState();
		String artTitle = userArticle.getArticleTitle();
		Timestamp updateTime =  userArticle.getArticleUpdateTime();
		int writerId = userArticle.getArticleWriterId();	
		
		if(!artState.equals("1")){ return m;}
		
		String writerName = someInfo.doQuerySomeInfo("UserInfo", "userName", "userId", writerId + "");	//根据用户真id 查找UserInfo表里的 用户名userName
		if(writerName == null || writerName.equals("")){ return m;}
		
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");	//格式化时间  用于找文件的目录
		String uTime= formatter2.format(updateTime);
		
		String content = CreateFileUtil.readJSONFile(uTime, address);	//读取json文件
		if(content == null || content.equals("")){ return m;}
		
		JSONObject  jsonObj = JSONObject.fromObject(content);		//把字符串转为json对象的格式
		
		ArticleJson articleJson = (ArticleJson)JSONObject.toBean(jsonObj,ArticleJson.class);		//把json对象转化为bean对象
		int writerIdJson =  articleJson.getArticle_writer_id();	
		if(writerIdJson != writerId){ return m;}
		
		String articleContent = articleJson.getArticle_content();		//获取文章的内容
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//读取回复
		List<?> listMessage = dao.doQueryArticleMessage(address);	//通过文章地址获取信息
		if (listMessage != null || listMessage.size() > 0){
			m.put("listMessage", listMessage);		//把整个查询结果以list<ArticleMessage>的形式保存在map里
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//读取总收藏数
		String some = someInfo.doQuerySomeInfo("UserCollection", "count(t)", "collectionState='1' and articleContentId", address);
		m.put("collectionNum",some);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//读取是否已收藏
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
		
		m.put("writerName", writerName);	//作者用户名
		m.put("artTitle", artTitle);	//文章标题
		m.put("articleContent", articleContent);	//文章内容
		m.put("updateTime", ""+updateTime);		//文章更新时间
		m.put("address",address);		//文章地址
		
		m.put("se","success");	//全部成功后
		m.put("result", "ok");		
		return m;
	}

	//读取文章列表 show_more
	public Map<String, Object> doLoadArticleList(int mark, int howMany) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("result", "no");	//初始化
		m.put("se","error");
		
		List<?> articleList = dao.doQueryArticleAll(mark,howMany);	//通过文章地址获取信息
		if (articleList != null && articleList.size() > 0){
			m.put("articleList", articleList);		//把整个查询结果以list<ArticleMessage>的形式保存在map里
			m.put("result", "ok");
			System.out.println("查询得到 " + mark + " - - " + howMany);
		}
		
		m.put("se","success");	//全部成功后
		return m;
	}
	// 按用户名读取 他/她 的文章列表
	public Map doLoadUserArticleList(int page, int userID, int howMany) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("result", "no");	//初始化
		m.put("se","error");
		
		String some = someInfo.doQuerySomeInfo("UserArticle", "count(t)", "articleWriterId", userID + "");
		int countArticle =  Integer.parseInt(some);
		System.out.println(some);
		if(countArticle == 0){
			m.put("info","not");
			return m;
		}
		List<?> articleList = dao.doQueryUserArticle(page,userID,howMany);	//通过文章地址获取信息
		if (articleList != null && articleList.size() > 0){
			String userName = someInfo.doQuerySomeInfo("UserInfo", "userName", "userId", userID + "");
			m.put("articleList", articleList);		//把整个查询结果以list<ArticleMessage>的形式保存在map里
			m.put("userName", userName);	
			m.put("result", "ok");
		}
		
		System.out.println("ookk"  + articleList.size());
		m.put("info",countArticle);
		m.put("se","success");	//全部成功后
		return m;
	}
	// 按用户名读取 他/她 的收藏列表
	public Map doLoadUserCollectionList(int page, int userID, int howMany) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("result", "no");	//初始化
		m.put("se","error");
		
		String some = someInfo.doQuerySomeInfo("UserArticle", "count(t)", "articleWriterId", userID + "");
		int countArticle =  Integer.parseInt(some);
		System.out.println(some);
		if(countArticle == 0){
			m.put("info","not");
			return m;
		}
		List<?> articleList = dao.doQueryUserCollection(page,userID,howMany);	//通过文章地址获取信息
		if (articleList != null && articleList.size() > 0){
			String userName = someInfo.doQuerySomeInfo("UserInfo", "userName", "userId", userID + "");
			m.put("userName", userName);
			m.put("articleList", articleList);		//把整个查询结果以list<ArticleMessage>的形式保存在map里
			m.put("result", "ok");
		}
		
		System.out.println("ookk"  + articleList.size());
		m.put("info",countArticle);
		m.put("se","success");	//全部成功后
		return m;
	}

	
}
