 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ArticleExtDao;
 import com.portal.doccenter.entity.ArticleExt;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ArticleExtDaoImpl extends QueryDaoImpl<ArticleExt, Integer>
   implements ArticleExtDao
 {
   public int delDocByInputUser(Integer userId)
   {
     String hql = "delete from ArticleExt bean where bean.id in (select a.id from Article a where a.user.id=?)";
 
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int delDocBySite(Integer siteId) {
     String hql = "delete from ArticleExt bean where bean.id in (select a.id from Article a where a.site.id=?)";
 
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<ArticleExt> getEntityClass()
   {
     return ArticleExt.class;
   }
 }


 
 
 