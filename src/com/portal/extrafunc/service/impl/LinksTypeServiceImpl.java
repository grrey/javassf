 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.LinksTypeDao;
 import com.portal.extrafunc.entity.LinksType;
 import com.portal.extrafunc.service.LinksTypeService;
 import com.portal.sysmgr.entity.Site;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class LinksTypeServiceImpl
   implements LinksTypeService
 {
   private LinksTypeDao dao;
 
   @Transactional(readOnly=true)
   public Page<LinksType> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public LinksType findById(Integer id) {
     LinksType entity = (LinksType)this.dao.findById(id);
     return entity;
   }
 
   @Transactional(readOnly=true)
   public List<LinksType> getList(Integer siteId, String sortname, String sortorder) {
     return this.dao.getList(siteId, sortname, sortorder);
   }
 
   public LinksType save(LinksType bean, Site site) {
     bean.setSite(site);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public LinksType update(LinksType bean) {
     bean = (LinksType)this.dao.update(bean);
     return bean;
   }
 
   public LinksType deleteById(Integer id) {
     LinksType bean = (LinksType)this.dao.deleteById(id);
     return bean;
   }
 
   public LinksType[] deleteByIds(Integer[] ids) {
     LinksType[] beans = new LinksType[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(LinksTypeDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 