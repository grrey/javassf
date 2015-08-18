 package com.portal.usermgr.service.impl;
 
 import com.portal.usermgr.dao.UserBindDao;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.entity.UserBind;
 import com.portal.usermgr.service.UserBindService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class UserBindServiceImpl
   implements UserBindService
 {
   private UserBindDao dao;
 
   @Transactional(readOnly=true)
   public Page<UserBind> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public UserBind getBindByUser(Integer userId, Integer status) {
     return this.dao.getBindByUser(userId, status);
   }
   @Transactional(readOnly=true)
   public UserBind findById(Integer id) {
     UserBind entity = (UserBind)this.dao.findById(id);
     return entity;
   }
 
   public UserBind save(User user, String username, String pass, Integer status) {
     UserBind bean = this.dao.getBindByUser(user.getId(), status);
     if (bean == null) {
       bean = new UserBind();
       bean.setUser(user);
       bean.setUsername(username);
       bean.setPassword(pass);
       bean.setStatus(status);
       this.dao.save(bean);
     } else {
       bean.setUsername(username);
       bean.setPassword(pass);
     }
     return bean;
   }
 
   public UserBind update(UserBind bean) {
     bean = (UserBind)this.dao.update(bean);
     return bean;
   }
 
   public UserBind deleteById(Integer id) {
     UserBind bean = (UserBind)this.dao.deleteById(id);
     return bean;
   }
 
   public UserBind[] deleteByIds(Integer[] ids) {
     UserBind[] beans = new UserBind[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(UserBindDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 