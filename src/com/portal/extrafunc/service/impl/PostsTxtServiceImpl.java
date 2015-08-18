 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.PostsTxtDao;
 import com.portal.extrafunc.entity.Posts;
 import com.portal.extrafunc.entity.PostsTxt;
 import com.portal.extrafunc.service.PostsTxtService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class PostsTxtServiceImpl
   implements PostsTxtService
 {
   private PostsTxtDao dao;
 
   @Transactional(readOnly=true)
   public Page<PostsTxt> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public PostsTxt findById(Integer id) {
     PostsTxt entity = (PostsTxt)this.dao.findById(id);
     return entity;
   }
 
   public PostsTxt save(String content, Posts posts) {
     PostsTxt txt = new PostsTxt();
     txt.setContent(content);
     txt.setPosts(posts);
     this.dao.save(txt);
     posts.setTxt(txt);
     return txt;
   }
 
   public PostsTxt update(Integer txtId, String content) {
     PostsTxt txt = findById(txtId);
     txt.setContent(content);
     return txt;
   }
 
   public PostsTxt deleteById(Integer id) {
     PostsTxt bean = (PostsTxt)this.dao.deleteById(id);
     return bean;
   }
 
   public PostsTxt[] deleteByIds(Integer[] ids) {
     PostsTxt[] beans = new PostsTxt[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(PostsTxtDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 