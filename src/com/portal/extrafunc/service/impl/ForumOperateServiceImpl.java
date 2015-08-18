 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.ForumOperateDao;
 import com.portal.extrafunc.entity.ForumOperate;
 import com.portal.extrafunc.service.ForumOperateService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.usermgr.entity.User;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ForumOperateServiceImpl
   implements ForumOperateService
 {
   private ForumOperateDao dao;
 
   @Transactional(readOnly=true)
   public Page<ForumOperate> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public ForumOperate findById(Integer id) {
     ForumOperate entity = (ForumOperate)this.dao.findById(id);
     return entity;
   }
 
   public ForumOperate save(Integer targetId, String targetType, String name, String reason, Site site, User user)
   {
     ForumOperate bean = new ForumOperate();
     bean.setAdmin(user);
     bean.setSite(site);
     bean.setName(name);
     bean.setTarget(targetId);
     bean.setTargetType(targetType);
     bean.setReason(reason);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public ForumOperate update(ForumOperate bean) {
     bean = (ForumOperate)this.dao.update(bean);
     return bean;
   }
 
   public ForumOperate deleteById(Integer id) {
     ForumOperate bean = (ForumOperate)this.dao.deleteById(id);
     return bean;
   }
 
   public ForumOperate[] deleteByIds(Integer[] ids) {
     ForumOperate[] beans = new ForumOperate[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ForumOperateDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 