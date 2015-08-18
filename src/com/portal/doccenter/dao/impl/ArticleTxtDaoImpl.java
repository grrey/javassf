 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ArticleTxtDao;
 import com.portal.doccenter.entity.ArticleTxt;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ArticleTxtDaoImpl extends QueryDaoImpl<ArticleTxt, Integer>
   implements ArticleTxtDao
 {
   public int delDocByInputUser(Integer userId)
   {
     String hql = "delete from ArticleTxt bean where bean.id in (select a.id from Article a where a.user.id=?)";
 
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int delDocBySite(Integer siteId) {
     String hql = "delete from ArticleTxt bean where bean.id in (select a.id from Article a where a.site.id=?)";
 
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<ArticleTxt> getEntityClass()
   {
     return ArticleTxt.class;
   }
 }


 
 
 