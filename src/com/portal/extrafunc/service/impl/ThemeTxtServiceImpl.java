 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.ThemeTxtDao;
 import com.portal.extrafunc.entity.Theme;
 import com.portal.extrafunc.entity.ThemeTxt;
 import com.portal.extrafunc.service.ThemeTxtService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ThemeTxtServiceImpl
   implements ThemeTxtService
 {
   private ThemeTxtDao dao;
 
   @Transactional(readOnly=true)
   public Page<ThemeTxt> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public ThemeTxt findById(Integer id) {
     ThemeTxt entity = (ThemeTxt)this.dao.findById(id);
     return entity;
   }
 
   public ThemeTxt save(Theme theme) {
     ThemeTxt txt = new ThemeTxt();
     txt.setContent(",");
     txt.setTheme(theme);
     this.dao.save(txt);
     theme.setTxt(txt);
     return txt;
   }
 
   public ThemeTxt update(Integer txtId, Integer userId) {
     ThemeTxt txt = findById(txtId);
     if (txt.getContent().indexOf("," + userId + ",") == -1) {
       txt.setContent(txt.getContent() + userId + ",");
     }
     return txt;
   }
 
   public int deleteByForumId(Integer forumId) {
     return this.dao.deleteByForumId(forumId);
   }
 
   public int deleteByUserId(Integer userId) {
     return this.dao.deleteByUserId(userId);
   }
 
   public int deleteBySiteId(Integer siteId) {
     return this.dao.deleteBySiteId(siteId);
   }
 
   public ThemeTxt deleteById(Integer id) {
     ThemeTxt bean = (ThemeTxt)this.dao.deleteById(id);
     return bean;
   }
 
   public ThemeTxt[] deleteByIds(Integer[] ids) {
     ThemeTxt[] beans = new ThemeTxt[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ThemeTxtDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 