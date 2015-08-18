 package com.portal.sysmgr.listener;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelSiteEvent;
 import com.portal.usermgr.service.RoleService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.ApplicationListener;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DelRoleListener
   implements ApplicationListener
 {
 
   @Autowired
   private RoleService roleService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     if ((baseEvent instanceof DelSiteEvent)) {
       DelSiteEvent event = (DelSiteEvent)baseEvent;
       Site site = event.getSite();
       this.roleService.deleteBySiteId(site.getId());
     }
   }
 }


 
 
 