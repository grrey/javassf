 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.LinksDao;
 import com.portal.extrafunc.entity.Links;
 import com.portal.extrafunc.service.LinksService;
 import com.portal.extrafunc.service.LinksTypeService;
 import com.portal.sysmgr.entity.Site;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class LinksServiceImpl
   implements LinksService
 {
   private LinksDao dao;
   private LinksTypeService typeService;
 
   @Transactional(readOnly=true)
   public Page<Links> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public Links findById(Integer id) {
     Links entity = (Links)this.dao.findById(id);
     return entity;
   }
 
   @Transactional(readOnly=true)
   public Page<Links> getPage(Integer siteId, Integer typeId, String sortname, String sortorder, int pageNo, int pageSize) {
     return this.dao.getPage(siteId, typeId, sortname, sortorder, pageNo, 
       pageSize);
   }
 
   @Transactional(readOnly=true)
   public List<Links> getListByTag(Integer siteId, Integer typeId, Integer count) {
     return this.dao.getListByTag(siteId, typeId, count);
   }
 
   public Links save(Links bean, Integer typeId, Site site) {
     if (typeId != null) {
       bean.setType(this.typeService.findById(typeId));
     }
     bean.setSite(site);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public Links update(Links bean, Integer typeId) {
     bean = (Links)this.dao.update(bean);
     if (typeId != null) {
       bean.setType(this.typeService.findById(typeId));
     }
     return bean;
   }
 
   public Links deleteById(Integer id) {
     Links bean = (Links)this.dao.deleteById(id);
     return bean;
   }
 
   public Links[] deleteByIds(Integer[] ids) {
     Links[] beans = new Links[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(LinksDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setTypeService(LinksTypeService typeService) {
     this.typeService = typeService;
   }
 }


 
 
 