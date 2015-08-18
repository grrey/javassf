 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.PostsExtDao;
 import com.portal.extrafunc.entity.PostsExt;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class PostsExtDaoImpl extends QueryDaoImpl<PostsExt, Integer>
   implements PostsExtDao
 {
   public Page<PostsExt> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<PostsExt> getEntityClass()
   {
     return PostsExt.class;
   }
 }


 
 
 