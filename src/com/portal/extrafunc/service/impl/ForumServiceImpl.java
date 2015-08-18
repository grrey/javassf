 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.ForumDao;
 import com.portal.extrafunc.entity.Forum;
 import com.portal.extrafunc.entity.ForumExt;
 import com.portal.extrafunc.service.CategoryService;
 import com.portal.extrafunc.service.ForumExtService;
 import com.portal.extrafunc.service.ForumService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelForumEvent;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationContext;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ForumServiceImpl
   implements ForumService
 {
   private ForumDao dao;
   private CategoryService categoryService;
   private ForumExtService extService;
   private ApplicationContext applicationContext;
 
   @Transactional(readOnly=true)
   public Page<Forum> getPage(Integer siteId, Integer categoryId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     return this.dao.getPage(siteId, categoryId, sortname, sortorder, pageNo, 
       pageSize);
   }
   @Transactional(readOnly=true)
   public List<Forum> getList(Integer categoryId) {
     return this.dao.getList(categoryId);
   }
 
   public List<Forum> getList() {
     return this.dao.getList();
   }
   @Transactional(readOnly=true)
   public Forum findById(Integer id) {
     Forum entity = (Forum)this.dao.findById(id);
     return entity;
   }
 
   public Forum save(Forum bean, ForumExt ext, Site site, Integer categoryId) {
     bean.setSite(site);
     bean.setCategory(this.categoryService.findById(categoryId));
     bean.init();
     this.dao.save(bean);
     this.extService.save(ext, bean);
     return bean;
   }
 
   public Forum updateForum(Forum forum) {
     Forum f = findById(forum.getId());
     f.setThemeTotal(forum.getThemeTotal());
     f.setReplyTotal(forum.getReplyTotal());
     f.setThemeToday(forum.getThemeToday());
     f.setReplyToday(forum.getReplyToday());
     f.setLastTheme(forum.getLastTheme());
     f.setLastReplyer(forum.getLastReplyer());
     return f;
   }
 
   public Forum updateForumOnday(Forum forum) {
     Forum f = findById(forum.getId());
     f.setThemeTotal(forum.getThemeTotal());
     f.setReplyTotal(forum.getReplyTotal());
     f.setThemeToday(Integer.valueOf(0));
     f.setReplyToday(Integer.valueOf(0));
     f.setLastTheme(forum.getLastTheme());
     f.setLastReplyer(forum.getLastReplyer());
     return f;
   }
 
   public Forum update(Forum bean, ForumExt ext, Integer categoryId) {
     bean = (Forum)this.dao.update(bean);
     bean.setCategory(this.categoryService.findById(categoryId));
     this.extService.update(ext, bean);
     return bean;
   }
 
   public int deleteByCategoryId(Integer categoryId) {
     this.extService.deleteByCategoryId(categoryId);
     return this.dao.deleteByCategoryId(categoryId);
   }
 
   public int deleteBySiteId(Integer siteId) {
     this.extService.deleteBySiteId(siteId);
     return this.dao.deleteBySiteId(siteId);
   }
 
   public Forum deleteById(Integer id) {
     Forum bean = (Forum)this.dao.deleteById(id);
     this.applicationContext.publishEvent(new DelForumEvent(bean));
     return bean;
   }
 
   public Forum[] deleteByIds(Integer[] ids) {
     Forum[] beans = new Forum[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ForumDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setCategoryService(CategoryService categoryService) {
     this.categoryService = categoryService;
   }
   @Autowired
   public void setExtService(ForumExtService extService) {
     this.extService = extService;
   }
   @Autowired
   public void setApplicationContext(ApplicationContext applicationContext) {
     this.applicationContext = applicationContext;
   }
 }


 
 
 