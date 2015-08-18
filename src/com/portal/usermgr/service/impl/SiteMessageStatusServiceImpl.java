 package com.portal.usermgr.service.impl;
 
 import com.portal.usermgr.dao.SiteMessageStatusDao;
 import com.portal.usermgr.entity.SiteMessage;
 import com.portal.usermgr.entity.SiteMessageStatus;
 import com.portal.usermgr.service.SiteMessageStatusService;
 import com.portal.usermgr.service.UserService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class SiteMessageStatusServiceImpl
   implements SiteMessageStatusService
 {
   private SiteMessageStatusDao dao;
   private UserService userService;
 
   @Transactional(readOnly=true)
   public Page<SiteMessageStatus> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public SiteMessageStatus findByRecive(Integer receiveId, Integer messageId) {
     return this.dao.findByRecive(receiveId, messageId);
   }
   @Transactional(readOnly=true)
   public SiteMessageStatus findById(Integer id) {
     SiteMessageStatus entity = (SiteMessageStatus)this.dao.findById(id);
     return entity;
   }
 
   public void save(SiteMessage message, Integer[] receiveIds) {
     for (Integer receiveId : receiveIds) {
       SiteMessageStatus bean = new SiteMessageStatus();
       bean.setMessage(message);
       bean.setReceive(this.userService.findById(receiveId));
       this.dao.save(bean);
     }
   }
 
   public SiteMessageStatus update(SiteMessageStatus bean) {
     bean = (SiteMessageStatus)this.dao.update(bean);
     return bean;
   }
 
   public SiteMessageStatus deleteByReceive(Integer messageId, Integer receiveId)
   {
     SiteMessageStatus bean = this.dao.findByRecive(receiveId, messageId);
     if (bean != null) {
       if (bean.getStatus().equals(SiteMessageStatus.DEL))
         this.dao.deleteById(bean.getId());
       else {
         bean.setStatus(SiteMessageStatus.DEL);
       }
     }
     return bean;
   }
 
   public SiteMessageStatus deleteById(Integer id) {
     SiteMessageStatus bean = (SiteMessageStatus)this.dao.deleteById(id);
     return bean;
   }
 
   public SiteMessageStatus[] deleteByIds(Integer[] ids) {
     SiteMessageStatus[] beans = new SiteMessageStatus[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(SiteMessageStatusDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setUserService(UserService userService) {
     this.userService = userService;
   }
 }


 
 
 