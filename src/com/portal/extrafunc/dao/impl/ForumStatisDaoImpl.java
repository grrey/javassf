 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.ForumStatisDao;
 import com.portal.extrafunc.entity.ForumStatis;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ForumStatisDaoImpl extends QueryDaoImpl<ForumStatis, Integer>
   implements ForumStatisDao
 {
   public Page<ForumStatis> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<ForumStatis> getEntityClass()
   {
     return ForumStatis.class;
   }
 }


 
 
 