 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.PostsExtDao;
 import com.portal.extrafunc.entity.Posts;
 import com.portal.extrafunc.entity.PostsExt;
 import com.portal.extrafunc.service.PostsExtService;
 import com.portal.usermgr.service.UserService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class PostsExtServiceImpl
   implements PostsExtService
 {
   private PostsExtDao dao;
   private UserService userService;
 
   @Transactional(readOnly=true)
   public Page<PostsExt> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public PostsExt findById(Integer id) {
     PostsExt entity = (PostsExt)this.dao.findById(id);
     return entity;
   }
 
   public PostsExt save(String ip, Posts posts) {
     PostsExt ext = new PostsExt();
     ext.setCreateIp(ip);
     ext.setPosts(posts);
     ext.init();
     this.dao.save(ext);
     posts.setExt(ext);
     return ext;
   }
 
   public PostsExt update(Integer extId, Integer userId, String ip) {
     PostsExt ext = findById(extId);
     ext.setEditer(this.userService.findById(userId));
     ext.setCreateIp(ip);
     ext.setEditCount(Integer.valueOf(ext.getEditCount().intValue() + 1));
     return ext;
   }
 
   public PostsExt deleteById(Integer id) {
     PostsExt bean = (PostsExt)this.dao.deleteById(id);
     return bean;
   }
 
   public PostsExt[] deleteByIds(Integer[] ids) {
     PostsExt[] beans = new PostsExt[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(PostsExtDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setUserService(UserService userService) {
     this.userService = userService;
   }
 }


 
 
 