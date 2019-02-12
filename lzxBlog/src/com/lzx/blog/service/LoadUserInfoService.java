package com.lzx.blog.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lzx.blog.dao.SomeInfoDAO;
import com.lzx.blog.entity.UserInfo;
import com.lzx.blog.entity.UserInfoBgimg;
import com.lzx.blog.entity.UserRelationship;
import com.lzx.blog.entity.some.ArticleJson;
import com.lzx.blog.util.CreateFileUtil;
import com.lzx.blog.util.MyRandom;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service("loadUserInfoService")
public class LoadUserInfoService {
	
	
	@Resource(name = "SomeInfoDAO")
	private SomeInfoDAO someInfo;

	public Map<String, Object> doLoadWriteInfo(int writeID ,String address) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("result", "no");	//初始化
		m.put("se","error");
		m.put("isMe",0);
		
		Map sess = ActionContext.getContext().getSession();
		UserInfo uInfo= (UserInfo)sess.get("userInfo");
		//判断是不是自己
		if(uInfo != null){
			int userId = uInfo.getUserId();
			if(userId == writeID){
				m.put("isMe",1);	//是的话就 1
				System.out.println(" is me --------");
			}
			System.out.println(writeID + " the "+ writeID);
		};		
		System.out.println(writeID);
		
		if(writeID<=0){return m;};
		
		List<?> list = someInfo.doQueryAllInfoByOneKey("UserInfo","userId",writeID + "");		//(String tableName, String byKey, String theValue)		
		if (list == null || list.size() <= 0) {
			String some = someInfo.doQuerySomeInfo("UserArticle", "articleWriterId", "articleContentId", address);
			list = someInfo.doQueryAllInfoByOneKey("UserInfo","userId",some);
			if (list == null || list.size() <= 0) {return m;};
		};	
		UserInfo user = (UserInfo) list.get(0);
		
		///
		//读取是否已关注
		m.put("state", 0);
		if(uInfo != null){
			int userId = uInfo.getUserId();
			String someKey = "userId1="+userId + " and userId2="+writeID+"";
			System.out.println(someKey);
			List relation = someInfo.doQueryAllInfoBySomeKey("UserRelationship","userIsFollow", someKey);
			System.out.println(relation.size() + " - this list - ");
		if(relation !=null && relation.size()>0){
				int uc =  Integer.parseInt(relation.get(0).toString());
				
				System.out.println(uc);
				m.put("state", uc);
			}
		}
		m.put("userInfo",user);
		m.put("result", "ok");	//初始化
		m.put("se","success");	//全部成功后
		return m;
	}
	
	public Map<String, Object> doLoadUserInfo(int writeID) {
		
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("result", "no");	//初始化
		m.put("se","error");
		m.put("isMe",0);
		
		Map sess = ActionContext.getContext().getSession();
		UserInfo uInfo= (UserInfo)sess.get("userInfo");
		//判断是不是自己
		if(uInfo != null){	
			int userId = uInfo.getUserId();
			if(writeID<=0){		//判断？后面有没有值
				writeID = userId;
			};
			if(userId == writeID){
				m.put("isMe",1);	//是的话就 1
				
			}
			System.out.println(" is me --------");
		}else if(writeID<=0){
			return m;
		};
		
		List<?> ulist = someInfo.doQueryAllInfoByOneKey("UserInfo","userId",writeID + "");		//(String tableName, String byKey, String theValue)	
		if (ulist == null || ulist.size() <= 0) {return m;};	//找不到该用户
		
		UserInfo user = (UserInfo) ulist.get(0);
		int uId = user.getUserId();
		
		//获取背景图片
		List<?> ubglist = someInfo.doQueryAllInfoByOneKey("UserInfoBgimg","userId",uId + "");		//(String tableName, String byKey, String theValue)	
		if (ubglist != null && ubglist.size() > 0) {
			UserInfoBgimg userInfoBgimg = (UserInfoBgimg) ubglist.get(0);	
			String userBGImg = userInfoBgimg.getBgimg();	//获取背景图片文件名
			Timestamp bgTime = userInfoBgimg.getChangeTime();	//获取第一次上传的时间
			if(userBGImg != null && bgTime != null){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");	//格式化时间  用于找文件的目录
				String uTime= formatter.format(bgTime);
				String content = CreateFileUtil.readBGimgJSONFile(uTime, userBGImg);	//读取json文件
				if(content != null && !content.equals("")){
	//				JSONObject  jsonObj = JSONObject.fromObject(content);		//把字符串转为json对象的格式
	//				JSONObject.toBean(jsonObj,String.class);
					JSONArray json = JSONArray.fromObject(content);
					List<?> jsonls = JSONArray.toList(json, new JsonConfig());
					m.put("userBGImg",jsonls);
				}
			}
		};
		///
		m.put("userInfo",user);
		m.put("result", "ok");	//初始化
		m.put("se","success");	//全部成功后
		return m;
	}
	
	//更改用户信息
	public String[] doSaveUserInfo(String userInfo) {
		String[] info = new String[3];
		info[0]="no"; info[1]="error";
		Map sess = ActionContext.getContext().getSession();
		UserInfo uInfo= (UserInfo)sess.get("userInfo");	
		if(uInfo == null){return info;};
		JSONArray json=JSONArray.fromObject(userInfo);
		int jsize = json.size();
		JSONObject jsonOne = null;
		String[] ls = new String[jsize+1];
		String setIs = "";
		int userId = uInfo.getUserId();	//修改人的id	
		Timestamp time = new Timestamp(System.currentTimeMillis()); // √
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
		String d2= formatter2.format(time) +  MyRandom.getItemName(4);	
		
		for(int i=0;i<jsize;i++){
			jsonOne = json.getJSONObject(i); 
			String key = (String)jsonOne.get("Key");
			String value = (String)jsonOne.get("Value");
			System.out.println(key);
			if(key.equals("userLogo")){
				String imgType = "";
				String regex = "^data:image/(jpg|gif|png|jpeg|bmp);base64,";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(value);
				if(matcher.find()){
					String val= value.replace(matcher.group(0),"");	//获取整段匹配内容并从value中去除这段
					imgType = matcher.group(1);	//获取图片类型
					String file = d2 + "." + imgType;
					boolean flag =  CreateFileUtil.generateImage(val, userId + "", file);	//生成json文件
					if(!flag){return info;};
					value = "/lzxBlog/page/img/user_logo/custom/" + userId + "/" + file;
					System.out.println(value);
				};
				
			}
			if(i==0){
				setIs = setIs + key + "=?";
			}else{
				setIs = setIs + " , " + key + "=?";
			}
			
			ls[i] = value;
		}
		
		ls[jsize] = userId + "";
		
		if(someInfo.doChangeUserInfo(ls, setIs)){
			info[0]="ok"; info[1]="success";
			System.out.println("修改成功");
			List<?> ulist = someInfo.doQueryAllInfoByOneKey("UserInfo","userId",userId + "");		//(String tableName, String byKey, String theValue)	
			UserInfo user = (UserInfo) ulist.get(0);
			sess.put("userInfo",user);
		};
		return info;
	}

}
