 package com.portal.govcenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.govcenter.dao.MailboxExtDao;
 import com.portal.govcenter.entity.MailboxExt;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class MailboxExtDaoImpl extends QueryDaoImpl<MailboxExt, Integer>
   implements MailboxExtDao
 {
   protected Class<MailboxExt> getEntityClass()
   {
     return MailboxExt.class;
   }
 }


 
 
 