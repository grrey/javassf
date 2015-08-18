 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.ForumExtDao;
 import com.portal.extrafunc.entity.ForumExt;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ForumExtDaoImpl extends QueryDaoImpl<ForumExt, Integer>
   implements ForumExtDao
 {
   public Page<ForumExt> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public int deleteByCategoryId(Integer categoryId) {
     String hql = "delete from ForumExt bean where bean.id in (select f.id from Forum f inner join f.category c where c.id=?)";
 
     return executeQuery(hql, new Object[] { categoryId });
   }
 
   public int deleteBySiteId(Integer siteId) {
     String hql = "delete from ForumExt bean where bean.id in (select f.id from Forum f inner join f.site s where s.id=?)";
 
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<ForumExt> getEntityClass()
   {
     return ForumExt.class;
   }
 }


 
 
 