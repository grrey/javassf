 package com.portal.usermgr.service.impl;
 
 import com.portal.usermgr.dao.SiteMessageDao;
 import com.portal.usermgr.entity.SiteMessage;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.MessageReceiveService;
 import com.portal.usermgr.service.SiteMessageService;
 import com.portal.usermgr.service.SiteMessageStatusService;
 import com.portal.usermgr.service.UserService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class SiteMessageServiceImpl
   implements SiteMessageService
 {
   private SiteMessageDao dao;
   private UserService userService;
   private MessageReceiveService receiveService;
   private SiteMessageStatusService statusService;
 
   @Transactional(readOnly=true)
   public Page<SiteMessage> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public Page<SiteMessage> getPageByTag(Integer sendId, Integer status, int pageNo, int pageSize) {
     return this.dao.getPageByTag(sendId, status, pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public SiteMessage findById(Integer id) {
     SiteMessage entity = (SiteMessage)this.dao.findById(id);
     return entity;
   }
 
   public SiteMessage save(SiteMessage bean, Integer sendId, Integer[] receiveIds)
   {
     bean.setSend(this.userService.findById(sendId));
     bean.init();
     this.dao.save(bean);
     if ((receiveIds != null) && (receiveIds.length > 3))
       this.receiveService.save(bean, receiveIds);
     else {
       this.statusService.save(bean, receiveIds);
     }
     return bean;
   }
 
   public SiteMessage update(SiteMessage bean) {
     bean = (SiteMessage)this.dao.update(bean);
     return bean;
   }
 
   public SiteMessage deleteById(Integer id, User user) {
     SiteMessage message = (SiteMessage)this.dao.findById(id);
     if (message.getSend().equals(user)) {
       if (message.getStatus().equals(SiteMessage.NORMAL))
         message.setStatus(SiteMessage.RECYCLE);
       else
         message.setStatus(SiteMessage.DEL);
     }
     else {
       this.statusService.deleteByReceive(id, user.getId());
     }
     return message;
   }
 
   public SiteMessage deleteById(Integer id) {
     SiteMessage bean = (SiteMessage)this.dao.deleteById(id);
     return bean;
   }
 
   public SiteMessage[] deleteByIds(Integer[] ids) {
     SiteMessage[] beans = new SiteMessage[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(SiteMessageDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setUserService(UserService userService) {
     this.userService = userService;
   }
   @Autowired
   public void setReceiveService(MessageReceiveService receiveService) {
     this.receiveService = receiveService;
   }
   @Autowired
   public void setStatusService(SiteMessageStatusService statusService) {
     this.statusService = statusService;
   }
 }


 
 
 