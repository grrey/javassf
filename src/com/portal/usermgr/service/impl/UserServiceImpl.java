 package com.portal.usermgr.service.impl;
 
 import com.javassf.basic.security.encoder.PwdEncoder;
 import com.portal.sysmgr.event.DelUserEvent;
 import com.portal.usermgr.dao.UserDao;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.UserService;
 import java.sql.Timestamp;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationContext;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class UserServiceImpl
   implements UserService
 {
   private UserDao dao;
   private PwdEncoder pwdEncoder;
   private ApplicationContext applicationContext;
 
   @Transactional(readOnly=true)
   public User findById(Integer id)
   {
     User entity = (User)this.dao.findById(id);
     return entity;
   }
   @Transactional(readOnly=true)
   public User findByUsername(String username) {
     User entity = this.dao.findByUsername(username);
     return entity;
   }
   @Transactional(readOnly=true)
   public int getAllUserCount() {
     return this.dao.getAllUserCount();
   }
 
   public User save(User user) {
     user.init();
     user.setPassword(this.pwdEncoder.encodePassword(user.getPassword()));
     this.dao.save(user);
     return user;
   }
 
   public User update(User user) {
     User bean = (User)this.dao.update(user);
     return bean;
   }
 
   public User updatePass(Integer userId, String password) {
     User user = findById(userId);
     user.setPassword(this.pwdEncoder.encodePassword(password));
     return user;
   }
 
   public User updateFailTime(User user) {
     User bean = (User)this.dao.update(user);
     bean.setLastFailTime(new Timestamp(System.currentTimeMillis()));
     if (bean.getFailCount() != null)
       bean.setFailCount(Integer.valueOf(user.getFailCount().intValue() + 1));
     else {
       bean.setFailCount(Integer.valueOf(1));
     }
     return bean;
   }
 
   public User deleteById(Integer id) {
     User bean = (User)this.dao.deleteById(id);
     this.applicationContext.publishEvent(new DelUserEvent(bean));
     return bean;
   }
 
   public User[] deleteByIds(Integer[] ids) {
     User[] beans = new User[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(UserDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setPwdEncoder(PwdEncoder pwdEncoder) {
     this.pwdEncoder = pwdEncoder;
   }
   @Autowired
   public void setApplicationContext(ApplicationContext applicationContext) {
     this.applicationContext = applicationContext;
   }
 }


 
 
 