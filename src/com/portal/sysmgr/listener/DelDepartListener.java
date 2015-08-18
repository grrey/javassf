 package com.portal.sysmgr.listener;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelSiteEvent;
 import com.portal.usermgr.service.DepartService;
 import java.io.PrintStream;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.event.SmartApplicationListener;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DelDepartListener
   implements SmartApplicationListener
 {
   private DepartService departService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     System.out.println("-----------删除文章..部门");
     if ((baseEvent instanceof DelSiteEvent)) {
       DelSiteEvent event = (DelSiteEvent)baseEvent;
       Site site = event.getSite();
       this.departService.deleteBySiteId(site.getId());
     }
   }
 
   public int getOrder()
   {
     return 0;
   }
 
   public boolean supportsEventType(Class<? extends ApplicationEvent> arg0)
   {
     return false;
   }
 
   public boolean supportsSourceType(Class<?> arg0)
   {
     return false;
   }
 
   @Autowired
   public void setDepartService(DepartService departService)
   {
     this.departService = departService;
   }
 }


 
 
 