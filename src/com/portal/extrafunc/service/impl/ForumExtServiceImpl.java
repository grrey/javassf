 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.ForumExtDao;
 import com.portal.extrafunc.entity.Forum;
 import com.portal.extrafunc.entity.ForumExt;
 import com.portal.extrafunc.service.ForumExtService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ForumExtServiceImpl
   implements ForumExtService
 {
   private ForumExtDao dao;
 
   @Transactional(readOnly=true)
   public Page<ForumExt> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public ForumExt findById(Integer id) {
     ForumExt entity = (ForumExt)this.dao.findById(id);
     return entity;
   }
 
   public ForumExt save(ForumExt bean, Forum forum) {
     bean.setForum(forum);
     this.dao.save(bean);
     forum.setExt(bean);
     return bean;
   }
 
   public ForumExt update(ForumExt bean, Forum forum) {
     ForumExt ext = findById(forum.getId());
     if (ext != null)
       bean = (ForumExt)this.dao.update(bean);
     else {
       bean = save(bean, forum);
     }
     return bean;
   }
 
   public int deleteByCategoryId(Integer categoryId) {
     return this.dao.deleteByCategoryId(categoryId);
   }
 
   public int deleteBySiteId(Integer siteId) {
     return this.dao.deleteBySiteId(siteId);
   }
 
   public ForumExt deleteById(Integer id) {
     ForumExt bean = (ForumExt)this.dao.deleteById(id);
     return bean;
   }
 
   public ForumExt[] deleteByIds(Integer[] ids) {
     ForumExt[] beans = new ForumExt[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ForumExtDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 