 package com.portal.govcenter.service.impl;
 
 import com.portal.govcenter.dao.MailboxTypeDao;
 import com.portal.govcenter.entity.MailboxType;
 import com.portal.govcenter.service.MailboxTypeService;
 import com.portal.sysmgr.entity.Site;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class MailboxTypeServiceImpl
   implements MailboxTypeService
 {
   private MailboxTypeDao dao;
 
   @Transactional(readOnly=true)
   public Page<MailboxType> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public List<MailboxType> getList(Integer siteId) {
     return this.dao.getList(siteId);
   }
   @Transactional(readOnly=true)
   public MailboxType findById(Integer id) {
     MailboxType entity = (MailboxType)this.dao.findById(id);
     return entity;
   }
 
   public MailboxType save(MailboxType bean, Site site) {
     bean.setSite(site);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public MailboxType update(MailboxType bean) {
     bean = (MailboxType)this.dao.update(bean);
     return bean;
   }
 
   public MailboxType deleteById(Integer id) {
     MailboxType bean = (MailboxType)this.dao.deleteById(id);
     return bean;
   }
 
   public MailboxType[] deleteByIds(Integer[] ids) {
     MailboxType[] beans = new MailboxType[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(MailboxTypeDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 