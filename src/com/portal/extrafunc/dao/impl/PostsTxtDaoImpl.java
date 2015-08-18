 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.PostsTxtDao;
 import com.portal.extrafunc.entity.PostsTxt;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class PostsTxtDaoImpl extends QueryDaoImpl<PostsTxt, Integer>
   implements PostsTxtDao
 {
   public Page<PostsTxt> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<PostsTxt> getEntityClass()
   {
     return PostsTxt.class;
   }
 }


 
 
 