 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.MessageReceiveDao;
 import com.portal.usermgr.entity.MessageReceive;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class MessageReceiveDaoImpl extends QueryDaoImpl<MessageReceive, Integer>
   implements MessageReceiveDao
 {
   public Page<MessageReceive> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<MessageReceive> getEntityClass()
   {
     return MessageReceive.class;
   }
 }


 
 
 