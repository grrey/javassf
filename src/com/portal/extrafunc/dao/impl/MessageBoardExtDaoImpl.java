 package com.portal.extrafunc.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.extrafunc.dao.MessageBoardExtDao;
 import com.portal.extrafunc.entity.MessageBoardExt;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class MessageBoardExtDaoImpl extends QueryDaoImpl<MessageBoardExt, Integer>
   implements MessageBoardExtDao
 {
   public Page<MessageBoardExt> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<MessageBoardExt> getEntityClass()
   {
     return MessageBoardExt.class;
   }
 }


 
 
 