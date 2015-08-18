 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.AdvertSlotDao;
 import com.portal.extrafunc.entity.AdvertSlot;
 import com.portal.extrafunc.service.AdvertSlotService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelAdvertSlotEvent;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationContext;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class AdvertSlotServiceImpl
   implements AdvertSlotService
 {
   private AdvertSlotDao dao;
   private ApplicationContext applicationContext;
 
   @Transactional(readOnly=true)
   public Page<AdvertSlot> getPage(Integer siteId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     return this.dao.getPage(siteId, sortname, sortorder, pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public List<AdvertSlot> getList(Integer siteId) {
     return this.dao.getList(siteId);
   }
   @Transactional(readOnly=true)
   public AdvertSlot findById(Integer id) {
     AdvertSlot entity = (AdvertSlot)this.dao.findById(id);
     return entity;
   }
 
   public AdvertSlot save(AdvertSlot bean, Site site) {
     bean.setSite(site);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public AdvertSlot update(AdvertSlot bean) {
     bean.init();
     bean = (AdvertSlot)this.dao.update(bean);
     return bean;
   }
 
   public int deleteBySiteId(Integer siteId) {
     return this.dao.deleteBySiteId(siteId);
   }
 
   public AdvertSlot deleteById(Integer id) {
     AdvertSlot bean = (AdvertSlot)this.dao.deleteById(id);
     this.applicationContext.publishEvent(new DelAdvertSlotEvent(bean));
     return bean;
   }
 
   public AdvertSlot[] deleteByIds(Integer[] ids) {
     AdvertSlot[] beans = new AdvertSlot[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(AdvertSlotDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setApplicationContext(ApplicationContext applicationContext) {
     this.applicationContext = applicationContext;
   }
 }


 
 
 