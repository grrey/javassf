 package com.portal.sysmgr.service.impl;
 
 import com.portal.sysmgr.dao.ThirdpartyConfigDao;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.entity.ThirdpartyConfig;
 import com.portal.sysmgr.service.ThirdpartyConfigService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ThirdpartyConfigServiceImpl
   implements ThirdpartyConfigService
 {
   private ThirdpartyConfigDao dao;
 
   @Transactional(readOnly=true)
   public Page<ThirdpartyConfig> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public ThirdpartyConfig findById(Integer id) {
     ThirdpartyConfig entity = (ThirdpartyConfig)this.dao.findById(id);
     return entity;
   }
 
   public ThirdpartyConfig save(ThirdpartyConfig bean) {
     this.dao.save(bean);
     return bean;
   }
 
   public ThirdpartyConfig update(ThirdpartyConfig bean, Site site) {
     if (bean.getId() != null) {
       bean = (ThirdpartyConfig)this.dao.update(bean);
     } else {
       bean.setSite(site);
       save(bean);
     }
     return bean;
   }
 
   public ThirdpartyConfig deleteById(Integer id) {
     ThirdpartyConfig bean = (ThirdpartyConfig)this.dao.deleteById(id);
     return bean;
   }
 
   public ThirdpartyConfig[] deleteByIds(Integer[] ids) {
     ThirdpartyConfig[] beans = new ThirdpartyConfig[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ThirdpartyConfigDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 