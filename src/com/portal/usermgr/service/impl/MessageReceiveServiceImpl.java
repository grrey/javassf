 package com.portal.usermgr.service.impl;
 
 import com.portal.usermgr.dao.MessageReceiveDao;
 import com.portal.usermgr.entity.MessageReceive;
 import com.portal.usermgr.entity.SiteMessage;
 import com.portal.usermgr.service.MessageReceiveService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class MessageReceiveServiceImpl
   implements MessageReceiveService
 {
   private MessageReceiveDao dao;
 
   @Transactional(readOnly=true)
   public Page<MessageReceive> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public MessageReceive findById(Integer id) {
     MessageReceive entity = (MessageReceive)this.dao.findById(id);
     return entity;
   }
 
   public MessageReceive save(SiteMessage message, Integer[] receiveIds) {
     MessageReceive bean = new MessageReceive();
     String ids = ",";
     for (Integer receiveId : receiveIds) {
       ids = ids + receiveId + ",";
     }
     bean.setContent(ids);
     bean.setMessage(message);
     this.dao.save(bean);
     return bean;
   }
 
   public MessageReceive update(MessageReceive bean) {
     bean = (MessageReceive)this.dao.update(bean);
     return bean;
   }
 
   public MessageReceive deleteById(Integer id) {
     MessageReceive bean = (MessageReceive)this.dao.deleteById(id);
     return bean;
   }
 
   public MessageReceive[] deleteByIds(Integer[] ids) {
     MessageReceive[] beans = new MessageReceive[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(MessageReceiveDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 