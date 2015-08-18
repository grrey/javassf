 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.UserForumDao;
 import com.portal.extrafunc.entity.UserForum;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class UserForumDaoImpl extends QueryDaoImpl<UserForum, Integer>
   implements UserForumDao
 {
   public Page<UserForum> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<UserForum> getEntityClass()
   {
     return UserForum.class;
   }
 }


 
 
 