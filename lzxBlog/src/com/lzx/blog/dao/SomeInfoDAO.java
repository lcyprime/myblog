package com.lzx.blog.dao;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.lzx.blog.entity.UserRelationship;

public class SomeInfoDAO  extends HibernateDaoSupport{
	
	@Resource(name = "ALLDAO")
	private ALLDAO allDao;
	
	//根据byValue 查询出一条 info
	public String doQuerySomeInfo(String tableName, String info, String byKey, String theValue){
		String hql = "select "+info+" from "+tableName+" as t where "+byKey+"='"+ theValue +"'";
		System.out.println("查询 根据byValue 查询出 info");
		List<?> ls = allDao.doQuery(hql);
		if(ls == null || ls.size() <= 0){
			return null;
		}
		return ls.get(0).toString();		
	}
	//根据一个字段查出全部数据
	public List<?> doQueryAllInfoByOneKey(String tableName, String byKey, String theValue) {
		String hql = "select t from "+tableName+" as t where "+byKey+"='"+ theValue +"'";
		System.out.println("查询 根据byValue 查询出全部info");
		return allDao.doQuery(hql);
	}
	//根据多个字段查出一条数据
	public List<?> doQueryAllInfoBySomeKey(String tableName, String info, String bySomeKey) {
		String hql = "select "+info+" from "+tableName+" as t where "+bySomeKey+"";
		System.out.println("查询 根据bySomeKey 查询出全部info");
		return allDao.doQuery(hql);
	}
	
	//更新关注状态
	public Boolean doChangeUserInfo(String[] ui,String setIs){
		String hql = "update UserInfo set "+ setIs +" where userId=?";
		System.out.println(hql);
		return allDao.doUpdate(hql, ui);
	}
}
