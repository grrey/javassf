 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.dao.AdvertDao;
 import com.portal.extrafunc.entity.Advert;
 import com.portal.extrafunc.service.AdvertService;
 import com.portal.extrafunc.service.AdvertSlotService;
 import com.portal.sysmgr.entity.Site;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class AdvertServiceImpl
   implements AdvertService
 {
   private AdvertDao dao;
   private AdvertSlotService slotService;
 
   @Transactional(readOnly=true)
   public Page<Advert> getPage(Integer siteId, Integer slotId, String sortname, String sortorder, int pageNo, int pageSize)
   {
     return this.dao.getPage(siteId, slotId, sortname, sortorder, pageNo, 
       pageSize);
   }
   @Transactional(readOnly=true)
   public List<Advert> getListByTag(Integer siteId, Integer slotId) {
     return this.dao.getListByTag(siteId, slotId);
   }
   @Transactional(readOnly=true)
   public Advert findById(Integer id) {
     Advert entity = (Advert)this.dao.findById(id);
     return entity;
   }
 
   public Advert save(Advert bean, Integer slotId, boolean advtype, Site site) {
     if (slotId != null) {
       bean.setSlot(this.slotService.findById(slotId));
     }
     if (advtype) {
       bean.setAdvType("js");
     }
     else if ((!StringUtils.isBlank(bean.getAttrUrl())) && 
       (bean.getAttrUrl().indexOf("swf") > -1))
       bean.setAdvType("flash");
     else {
       bean.setAdvType("img");
     }
 
     bean.setSite(site);
     bean.init();
     this.dao.save(bean);
     return bean;
   }
 
   public Advert update(Advert bean, Integer slotId, boolean advtype) {
     bean = (Advert)this.dao.update(bean);
     if (slotId != null) {
       bean.setSlot(this.slotService.findById(slotId));
     }
     if (advtype) {
       bean.setAdvType("js");
     }
     else if ((!StringUtils.isBlank(bean.getAttrUrl())) && 
       (bean.getAttrUrl().indexOf("swf") > -1))
       bean.setAdvType("flash");
     else {
       bean.setAdvType("img");
     }
 
     return bean;
   }
 
   public int deleteBySlotId(Integer slotId) {
     return this.dao.deleteBySlotId(slotId);
   }
 
   public int deleteBySiteId(Integer siteId) {
     return this.dao.deleteBySiteId(siteId);
   }
 
   public Advert deleteById(Integer id) {
     Advert bean = (Advert)this.dao.deleteById(id);
     return bean;
   }
 
   public Advert[] deleteByIds(Integer[] ids) {
     Advert[] beans = new Advert[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(AdvertDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setSlotService(AdvertSlotService slotService) {
     this.slotService = slotService;
   }
 }


 
 
 