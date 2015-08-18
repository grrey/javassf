 package com.portal.sysmgr.service.impl;
 
 import com.portal.sysmgr.dao.SiteConfigDao;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.entity.SiteConfig;
 import com.portal.sysmgr.service.SiteConfigService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class SiteConfigServiceImpl
   implements SiteConfigService
 {
   private SiteConfigDao dao;
 
   @Transactional(readOnly=true)
   public Page<SiteConfig> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public SiteConfig findById(Integer id) {
     SiteConfig entity = (SiteConfig)this.dao.findById(id);
     return entity;
   }
 
   public SiteConfig save(SiteConfig bean) {
     this.dao.save(bean);
     return bean;
   }
 
   public SiteConfig update(SiteConfig bean, Site site) {
     if (bean.getId() != null) {
       SiteConfig sc = findById(bean.getId());
       bean = (SiteConfig)this.dao.update(bean);
       if (bean.getRegMin() == null) {
         sc.setRegMin(null);
       }
       if (bean.getRegMax() == null) {
         sc.setRegMax(null);
       }
       if (bean.getLoginCount() == null)
         sc.setLoginCount(null);
     }
     else {
       bean.setSite(site);
       bean.init();
       save(bean);
       site.setConfig(bean);
     }
     return bean;
   }
 
   public SiteConfig deleteById(Integer id) {
     SiteConfig bean = (SiteConfig)this.dao.deleteById(id);
     return bean;
   }
 
   public SiteConfig[] deleteByIds(Integer[] ids) {
     SiteConfig[] beans = new SiteConfig[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(SiteConfigDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 