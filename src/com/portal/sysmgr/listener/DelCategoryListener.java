 package com.portal.sysmgr.listener;
 
 import com.portal.extrafunc.service.CategoryService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelSiteEvent;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.event.SmartApplicationListener;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DelCategoryListener
   implements SmartApplicationListener
 {
   private CategoryService categoryService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     if ((baseEvent instanceof DelSiteEvent)) {
       DelSiteEvent event = (DelSiteEvent)baseEvent;
       Site site = event.getSite();
       this.categoryService.deleteBySiteId(site.getId());
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
   public void setCategoryService(CategoryService categoryService)
   {
     this.categoryService = categoryService;
   }
 }


 
 
 