 package com.portal.sysmgr.event;
 
 import com.portal.sysmgr.entity.Site;
 import org.springframework.context.ApplicationEvent;
 
 public class DelSiteEvent extends ApplicationEvent
 {
   public DelSiteEvent(Site site)
   { 
     super(site);
   }
 
   public Site getSite() {
     return (Site)super.getSource();
   }
 }


 
 
 