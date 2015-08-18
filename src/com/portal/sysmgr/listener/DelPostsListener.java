 package com.portal.sysmgr.listener;
 
 import com.portal.extrafunc.entity.Category;
 import com.portal.extrafunc.entity.Forum;
 import com.portal.extrafunc.entity.Theme;
 import com.portal.extrafunc.service.PostsService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelCategoryEvent;
 import com.portal.sysmgr.event.DelForumEvent;
 import com.portal.sysmgr.event.DelSiteEvent;
 import com.portal.sysmgr.event.DelThemeEvent;
 import com.portal.sysmgr.event.DelUserEvent;
 import com.portal.usermgr.entity.User;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.event.SmartApplicationListener;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DelPostsListener
   implements SmartApplicationListener
 {
   private PostsService postsService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     if ((baseEvent instanceof DelSiteEvent)) {
       DelSiteEvent event = (DelSiteEvent)baseEvent;
       Site site = event.getSite();
       this.postsService.deletePosts(site.getId(), null, null, null, null);
     }
     if ((baseEvent instanceof DelCategoryEvent)) {
       DelCategoryEvent event = (DelCategoryEvent)baseEvent;
       Category c = event.getCategory();
       this.postsService.deletePosts(null, c.getId(), null, null, null);
     }
     if ((baseEvent instanceof DelForumEvent)) {
       DelForumEvent event = (DelForumEvent)baseEvent;
       Forum f = event.getForum();
       this.postsService.deletePosts(null, null, f.getId(), null, null);
     }
     if ((baseEvent instanceof DelThemeEvent)) {
       DelThemeEvent event = (DelThemeEvent)baseEvent;
       Theme t = event.getTheme();
       this.postsService.deletePosts(null, null, null, t.getId(), null);
     }
     if ((baseEvent instanceof DelUserEvent)) {
       DelUserEvent event = (DelUserEvent)baseEvent;
       User user = event.getUser();
       this.postsService.deletePosts(null, null, null, null, user.getId());
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
   public void setPostsService(PostsService postsService)
   {
     this.postsService = postsService;
   }
 }


 
 
 