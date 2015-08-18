 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.ForumStatisDao;
 import com.portal.extrafunc.entity.ForumStatis;
 import com.portal.extrafunc.service.ForumStatisService;
 import com.portal.sysmgr.entity.Site;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ForumStatisServiceImpl
   implements ForumStatisService
 {
   private ForumStatisDao dao;
 
   @Transactional(readOnly=true)
   public Page<ForumStatis> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public ForumStatis findById(Integer id) {
     ForumStatis entity = (ForumStatis)this.dao.findById(id);
     return entity;
   }
 
   public ForumStatis save(Site site) {
     ForumStatis bean = new ForumStatis();
     bean.setPostsToday(Integer.valueOf(0));
     bean.setPostsYestoday(Integer.valueOf(0));
     bean.setPostsTotal(Integer.valueOf(0));
     bean.setHighestDay(Integer.valueOf(0));
     bean.setSite(site);
     this.dao.save(bean);
     site.setForumStatis(bean);
     return bean;
   }
 
   public ForumStatis update(Integer siteId, Integer postsToday, Integer postsTotal)
   {
     ForumStatis bean = findById(siteId);
     bean.setPostsToday(postsToday);
     bean.setPostsTotal(postsTotal);
     return bean;
   }
 
   public ForumStatis updateOnday(Integer siteId, Integer postsToday, Integer postsTotal)
   {
     ForumStatis bean = findById(siteId);
     if (postsToday.intValue() > bean.getHighestDay().intValue()) {
       bean.setHighestDay(postsToday);
     }
     bean.setPostsToday(Integer.valueOf(0));
     bean.setPostsYestoday(postsToday);
     bean.setPostsTotal(postsTotal);
     return bean;
   }
 
   public ForumStatis update(ForumStatis bean) {
     bean = (ForumStatis)this.dao.update(bean);
     return bean;
   }
 
   public ForumStatis deleteById(Integer id) {
     ForumStatis bean = (ForumStatis)this.dao.deleteById(id);
     return bean;
   }
 
   public ForumStatis[] deleteByIds(Integer[] ids) {
     ForumStatis[] beans = new ForumStatis[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ForumStatisDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 