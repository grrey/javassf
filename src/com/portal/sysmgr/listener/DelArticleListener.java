 package com.portal.sysmgr.listener;
 
 import com.portal.doccenter.service.ArticleService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelDepartEvent;
 import com.portal.sysmgr.event.DelRoleEvent;
 import com.portal.sysmgr.event.DelSiteEvent;
 import com.portal.sysmgr.event.DelUserEvent;
 import com.portal.usermgr.entity.Depart;
 import com.portal.usermgr.entity.Role;
 import com.portal.usermgr.entity.User;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.event.SmartApplicationListener;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DelArticleListener
   implements SmartApplicationListener
 {
   private ArticleService articleService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     if ((baseEvent instanceof DelSiteEvent)) {
       DelSiteEvent event = (DelSiteEvent)baseEvent;
       Site site = event.getSite();
       this.articleService.delDocBySite(site.getId());
     }
     if ((baseEvent instanceof DelUserEvent)) {
       DelUserEvent event = (DelUserEvent)baseEvent;
       User user = event.getUser();
       this.articleService.delDocByInputUser(user.getId());
       this.articleService.updateDocByCheckUser(user.getId());
     }
     if ((baseEvent instanceof DelRoleEvent)) {
       DelRoleEvent event = (DelRoleEvent)baseEvent;
       Role role = event.getRole();
       this.articleService.updateDocByRole(role.getId());
     }
     if ((baseEvent instanceof DelDepartEvent)) {
       DelDepartEvent event = (DelDepartEvent)baseEvent;
       Depart depart = event.getDepart();
       this.articleService.updateDocByInputDepart(depart.getId());
     }
   }
 
   public int getOrder()
   {
     return 2;
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
   public void setArticleService(ArticleService articleService)
   {
     this.articleService = articleService;
   }
 }


 
 
 