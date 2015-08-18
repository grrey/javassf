 package com.portal.sysmgr.listener;
 
 import com.portal.extrafunc.service.AdvertSlotService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelSiteEvent;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.event.SmartApplicationListener;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DelAdvertSlotListener
   implements SmartApplicationListener
 {
   private AdvertSlotService slotService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     if ((baseEvent instanceof DelSiteEvent)) {
       DelSiteEvent event = (DelSiteEvent)baseEvent;
       Site site = event.getSite();
       this.slotService.deleteBySiteId(site.getId());
     }
   }
 
   public int getOrder()
   {
     return 2;
   }
 
   public boolean supportsEventType(Class<? extends ApplicationEvent> evenType)
   {
     return evenType == DelSiteEvent.class;
   }
 
   public boolean supportsSourceType(Class<?> sourceType)
   {
     return sourceType == Site.class;
   }
 
   @Autowired
   public void setSlotService(AdvertSlotService slotService)
   {
     this.slotService = slotService;
   }
 }


 
 
 