 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.MessageTypeDao;
 import com.portal.extrafunc.entity.MessageType;
 import com.portal.extrafunc.service.MessageTypeService;
 import com.portal.sysmgr.entity.Site;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class MessageTypeServiceImpl
   implements MessageTypeService
 {
   private MessageTypeDao dao;
 
   @Transactional(readOnly=true)
   public Page<MessageType> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public List<MessageType> getList(Integer siteId, String sortname, String sortorder) {
     return this.dao.getList(siteId, sortname, sortorder);
   }
   @Transactional(readOnly=true)
   public MessageType getUniqueType(Integer siteId) {
     return this.dao.getUniqueType(siteId);
   }
   @Transactional(readOnly=true)
   public MessageType findById(Integer id) {
     MessageType entity = (MessageType)this.dao.findById(id);
     return entity;
   }
 
   public MessageType save(MessageType bean, Site site) {
     bean.setSite(site);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public MessageType update(MessageType bean) {
     bean = (MessageType)this.dao.update(bean);
     return bean;
   }
 
   public MessageType deleteById(Integer id) {
     MessageType bean = (MessageType)this.dao.deleteById(id);
     return bean;
   }
 
   public MessageType[] deleteByIds(Integer[] ids) {
     MessageType[] beans = new MessageType[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(MessageTypeDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 