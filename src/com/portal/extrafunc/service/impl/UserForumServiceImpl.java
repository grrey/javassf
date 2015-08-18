 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.UserForumDao;
 import com.portal.extrafunc.entity.UserForum;
 import com.portal.extrafunc.service.UserForumService;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.UserService;
 import java.util.Date;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class UserForumServiceImpl
   implements UserForumService
 {
   private UserForumDao dao;
   private UserService userService;
 
   @Transactional(readOnly=true)
   public Page<UserForum> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public UserForum findById(Integer id) {
     UserForum entity = (UserForum)this.dao.findById(id);
     return entity;
   }
 
   public UserForum save(User user) {
     UserForum bean = new UserForum();
     bean.setUser(user);
     bean.init();
     this.dao.save(bean);
     user.setUserForum(bean);
     return bean;
   }
 
   public UserForum update(Integer userId, Integer themeCount, Integer replyCount, Integer essenaCount, Integer point)
   {
     UserForum bean = findById(userId);
     if (bean != null) {
       bean.setThemeCount(Integer.valueOf(bean.getThemeCount().intValue() + themeCount.intValue()));
       bean.setEssenaCount(Integer.valueOf(bean.getEssenaCount().intValue() + essenaCount.intValue()));
       bean.setReplyCount(Integer.valueOf(bean.getReplyCount().intValue() + replyCount.intValue()));
       bean.setPoint(Integer.valueOf(bean.getPoint().intValue() + point.intValue()));
     } else {
       bean = new UserForum();
       User user = this.userService.findById(userId);
       bean.setThemeCount(themeCount);
       bean.setEssenaCount(essenaCount);
       bean.setReplyCount(replyCount);
       bean.setPoint(point);
       bean.setUser(user);
       bean.init();
       this.dao.save(bean);
       user.setUserForum(bean);
     }
     return bean;
   }
 
   public UserForum shieldUserForum(Integer userId, Date shieldTime) {
     UserForum bean = findById(userId);
     bean.setStatus(Integer.valueOf(-2));
     bean.setStatusTime(shieldTime);
     return bean;
   }
 
   public UserForum update(UserForum bean) {
     bean = (UserForum)this.dao.update(bean);
     return bean;
   }
 
   public UserForum deleteById(Integer id) {
     UserForum bean = (UserForum)this.dao.deleteById(id);
     return bean;
   }
 
   public UserForum[] deleteByIds(Integer[] ids) {
     UserForum[] beans = new UserForum[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(UserForumDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setUserService(UserService userService) {
     this.userService = userService;
   }
 }


 
 
 