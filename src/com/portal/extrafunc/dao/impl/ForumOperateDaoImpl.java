 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.ForumOperateDao;
 import com.portal.extrafunc.entity.ForumOperate;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ForumOperateDaoImpl extends QueryDaoImpl<ForumOperate, Integer>
   implements ForumOperateDao
 {
   public Page<ForumOperate> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<ForumOperate> getEntityClass()
   {
     return ForumOperate.class;
   }
 }


 
 
 