 package com.portal.sysmgr.listener;
 
 import com.portal.extrafunc.entity.AdvertSlot;
 import com.portal.extrafunc.service.AdvertService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelAdvertSlotEvent;
 import com.portal.sysmgr.event.DelSiteEvent;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.event.SmartApplicationListener;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DelAdvertListener
   implements SmartApplicationListener
 {
   private AdvertService advertService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     if ((baseEvent instanceof DelSiteEvent)) {
       DelSiteEvent event = (DelSiteEvent)baseEvent;
       Site site = event.getSite();
       this.advertService.deleteBySiteId(site.getId());
     }
     if ((baseEvent instanceof DelAdvertSlotEvent)) {
       DelAdvertSlotEvent event = (DelAdvertSlotEvent)baseEvent;
       AdvertSlot slot = event.getAdvertSlot();
       this.advertService.deleteBySlotId(slot.getId());
     }
   }
 
   public int getOrder()
   {
     return 1;
   }
 
   public boolean supportsEventType(Class<? extends ApplicationEvent> evenType)
   {
     if (evenType == DelSiteEvent.class) {
       return true;
     }
 
     return evenType == DelAdvertSlotEvent.class;
   }
 
   public boolean supportsSourceType(Class<?> sourceType)
   {
     if (sourceType == Site.class) {
       return true;
     }
 
     return sourceType == AdvertSlot.class;
   }
 
   @Autowired
   public void setAdvertService(AdvertService advertService)
   {
     this.advertService = advertService;
   }
 }


 
 
 