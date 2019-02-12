package com.lzx.blog.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class ALLDAO extends HibernateDaoSupport {

	private Session sess = null;
	private Transaction tx = null;
	
	//执行查询的hql语句
	public List<?> doQuery(String hql) {
		try {
			sess = this.getHibernateTemplate().getSessionFactory().openSession();
			Query query = sess.createQuery(hql);
			List<?> usersList = query.list();
			return usersList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null && sess.isOpen()) {
				sess.close();
				System.out.println("DoQuery_close - 普通查询");
			}
		}
		return null;
	}
	//执行分组查询的hql语句
	public List<?> doQuery(String hql, int firstResult, int maxCount) {
		try {
			sess = this.getHibernateTemplate().getSessionFactory().openSession();
			Query query = sess.createQuery(hql);
			query.setFirstResult(firstResult);    //设置从第几个开始获取
			query.setMaxResults(maxCount);  //设置获取的数量
			List<?> usersList = query.list();
			return usersList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null && sess.isOpen()) {
				sess.close();
				System.out.println("DoQuery_close - 分组查询");
			}
		}
		return null;
	}
	//执行插入数据
	public Boolean doAdd(Object object) {
		try {
			sess = this.getHibernateTemplate().getSessionFactory().openSession();
			tx = sess.beginTransaction();		
			sess.save(object);
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {		//最后都会执行
			if (sess != null && sess.isOpen()) {
				sess.close();
				System.out.println("DoAdd_close");
			}
		}
		return false;
	}
	//执行更新数据
	public Boolean doUpdate(String hql,Object object) {
		try {
			sess = this.getHibernateTemplate().getSessionFactory().openSession();
			tx = sess.beginTransaction();		
			Query query = sess.createQuery(hql);
			query.setProperties(object);
			query.executeUpdate();
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {		//最后都会执行
			if (sess != null && sess.isOpen()) {
				sess.close();
				System.out.println("DoUpdate_close");
			}
		}
		return false;
	}
	
	//执行更新数据(问号位置)
	public Boolean doUpdate(String hql,String[] ls) {
		if(ls.length==0 || ls==null){return false;};
		try {
			sess = this.getHibernateTemplate().getSessionFactory().openSession();
			tx = sess.beginTransaction();		
			Query query = sess.createQuery(hql);
			for(int i=0;i<ls.length;i++){
				String s = new String(ls[i].getBytes("utf-8"),"utf-8");
				query.setString(i, s);
			}
			query.executeUpdate();
			tx.commit();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {		//最后都会执行
			if (sess != null && sess.isOpen()) {
				sess.close();
				System.out.println("DoUpdate_close");
			}
		}
		return false;
	}
}
