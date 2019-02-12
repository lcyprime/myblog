package com.lzx.blog.service;

import java.io.File;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.lzx.blog.dao.ArticleDAO;
import com.lzx.blog.entity.ArticleMessage;
import com.lzx.blog.entity.UserArticle;
import com.lzx.blog.entity.UserCollection;
import com.lzx.blog.entity.UserInfo;
import com.lzx.blog.entity.some.ArticleJson;
import com.lzx.blog.util.CreateFileUtil;
import com.lzx.blog.util.MyRandom;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service("articleService")
public class ArticleService {
	
	@Resource(name = "ArticleDAO")
	private ArticleDAO dao;
	//发表文章
	public String[] doWriteArticle(String title,String content, String address, String contentText) {
		String[] info = new String[3];
		info[0]="no"; info[1]="error";
		
		Map sess = ActionContext.getContext().getSession();
		UserInfo uInfo= (UserInfo)sess.get("userInfo");
			
		if(uInfo == null){return info;};
			
		int userId = uInfo.getUserId();
		ArticleJson artJson = new ArticleJson();
		UserArticle userArticle = new UserArticle();
		
		Timestamp time = new Timestamp(System.currentTimeMillis()); // √
		SimpleDateFormat formatter1 = new SimpleDateFormat("HHmmssyyyyMMdd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
		String d1= formatter1.format(time);	
		String d2= formatter2.format(time);	
		String article_content_id =  MyRandom.getItemID(4) + d1 + MyRandom.getItemName(4);
	
		//打包数据进json
		artJson.setArticle_writer_id(userId); 	// 作者id/当前用户id
		artJson.setArticle_title(title);		// 文章题目
		artJson.setArticle_content(content);	// 文章内容
		artJson.setArticle_content_id(article_content_id);	//文章id
		
		//打包数据进数据库
		userArticle.setArticleWriterId(userId);
		userArticle.setArticleTitle(title);
		userArticle.setArticleUpdateTime(time);
		userArticle.setArticleContentId(article_content_id);
		userArticle.setArticleContentText(contentText);
		userArticle.setArticleState("1");
		
		JSONObject jo = JSONObject.fromObject(artJson);	 	//转成json格式
		System.out.println(jo.toString());
		boolean flag =  CreateFileUtil.createJsonFile(jo.toString(), article_content_id, d2);	//生成json文件
		//把jsonArray变成二维数组
		System.out.println("这里的地址 - " + address);	
		
		if(!flag){return info;};
		
		info[2]=article_content_id;
		
		String[][] strs2s = null;
		JSONArray json = JSONArray.fromObject(address);
		System.out.println("这里的json - " + json);
		List<?> list = JSONArray.toList(json, new JsonConfig());	//先变成list集合
		System.out.println("这里的大小 - " + list.size());
		
		if(list.size()>0){	
			strs2s = new String[list.size()][3];	//根据第一维数组的大小定长度，二维长度时固定的
			for(int i=0; i < list.size(); i++){	//遍历第一维
				JSONArray str = JSONArray.fromObject(list.get(i));
				List<?> ls = JSONArray.toList(str, new JsonConfig()); //先变成list集合
				for(int j=0; j<ls.size(); j++){		//遍历第二维
					strs2s[i][j] = ls.get(j).toString();
				}
			}
			boolean copyflag = true;
			for(int k=0; k < strs2s.length; k++){		//复制文件
				String fileAddress = strs2s[k][1];
				String fileName = strs2s[k][2];
				File source=new File(ServletActionContext.getServletContext().getRealPath("upload_temp")); 	// 文件源
				File dest=new File(ServletActionContext.getServletContext().getRealPath("upload"));		//复制至
				copyflag = CreateFileUtil.copyFileUsingFileChannels(source, dest, fileAddress,fileName);
				if(!copyflag){ break;}
			}
			if(!copyflag){return info;};
		}
		if(!dao.doSaveArticle(userArticle,userId)){return info;};		//写入数据库
		info[0]="ok"; info[1]="success";return info;
	}
	//发表回复
	public String[] doWriteReply(String content, String address) {
		String[] info = new String[3];
		info[0]="no"; info[1]="error";
		Map sess = ActionContext.getContext().getSession();
		UserInfo uInfo= (UserInfo)sess.get("userInfo");
		
		if(uInfo == null){return info;};
		
		Timestamp time = new Timestamp(System.currentTimeMillis()); // √	
		int userId = uInfo.getUserId();	//回复人的id
		String userName = uInfo.getUserName();
		
		ArticleMessage articleMessage = new ArticleMessage();
		articleMessage.setMessageWriterId(userId);
		articleMessage.setMessageWriterName(userName);
		articleMessage.setMsgForArticleId(address);
		articleMessage.setMsgContent(content);
		articleMessage.setMsgState("1");
		articleMessage.setMsgUpdateTime(time);
		
		if(!dao.doSaveReply(articleMessage)){return info;};		//写入数据库
		
		info[2]=address;
		info[0]="ok"; info[1]="success";return info;
	}
	//添加/取消 收藏
	public String[] doChangeCollection(int state,String address) {
		
		String[] info = new String[3];
		info[0]="no"; info[1]="error";
		Map sess = ActionContext.getContext().getSession();
		UserInfo uInfo= (UserInfo)sess.get("userInfo");	
		
		if(uInfo == null){return info;};
		
		int userId = uInfo.getUserId();	//订阅人的id	
		
		UserCollection userCollection;
		List<?> list = dao.doQueryUserCollection(userId);
		
		Timestamp time = new Timestamp(System.currentTimeMillis());
		
		if (list != null && list.size() > 0){
			for(int i=0 ; i<list.size();i++){
				userCollection = (UserCollection) list.get(i);
				if(address == userCollection.getArticleContentId() || address.equals(userCollection.getArticleContentId())){
					if(dao.doChangeUserCollection(userCollection.getId(), state, time)){
						info[0]="ok";info[1]="success";
						System.out.println("更新订阅成功" + state);
						return info;
					};
				};
			};
		};	
			userCollection = new UserCollection();
			userCollection.setCollectionState(state + "");
			userCollection.setCollectionUpdateTime(time);
			userCollection.setCollectionWriterId(userId);
			userCollection.setArticleContentId(address);
			if(dao.doAddUserCollection(userCollection)){
				info[0]="ok";info[1]="success";
				System.out.println("添加订阅成功" + state);
			};
		return info;
	}

	
}
