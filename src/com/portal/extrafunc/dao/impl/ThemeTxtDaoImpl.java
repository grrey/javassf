 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.ThemeTxtDao;
 import com.portal.extrafunc.entity.ThemeTxt;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ThemeTxtDaoImpl extends QueryDaoImpl<ThemeTxt, Integer>
   implements ThemeTxtDao
 {
   public Page<ThemeTxt> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public int deleteByForumId(Integer forumId) {
     String hql = "delete from ThemeTxt bean where bean.id in (select t.id from Theme t inner join t.forum f where f.id=?)";
 
     return executeQuery(hql, new Object[] { forumId });
   }
 
   public int deleteByUserId(Integer userId) {
     String hql = "delete from ThemeTxt bean where bean.id in (select t.id from Theme t inner join t.creater c where c.id=?)";
 
     return executeQuery(hql, new Object[] { userId });
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from Theme bean where bean.site.id=? (select t.id from Theme t inner join t.site s where s.id=?)";
 
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<ThemeTxt> getEntityClass()
   {
     return ThemeTxt.class;
   }
 }


 
 
 