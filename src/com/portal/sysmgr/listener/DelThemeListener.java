 package com.portal.sysmgr.listener;
 
 import com.portal.extrafunc.entity.Category;
 import com.portal.extrafunc.entity.Forum;
 import com.portal.extrafunc.entity.Posts;
 import com.portal.extrafunc.entity.Theme;
 import com.portal.extrafunc.service.PostsService;
 import com.portal.extrafunc.service.ThemeService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelCategoryEvent;
 import com.portal.sysmgr.event.DelForumEvent;
 import com.portal.sysmgr.event.DelSiteEvent;
 import com.portal.sysmgr.event.DelUserEvent;
 import com.portal.usermgr.entity.User;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.event.SmartApplicationListener;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DelThemeListener
   implements SmartApplicationListener
 {
   private ThemeService themeService;
   private PostsService postsService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     if ((baseEvent instanceof DelSiteEvent)) {
       DelSiteEvent event = (DelSiteEvent)baseEvent;
       Site site = event.getSite();
       this.themeService.deleteBySiteId(site.getId());
     }
     if ((baseEvent instanceof DelCategoryEvent)) {
       DelCategoryEvent event = (DelCategoryEvent)baseEvent;
       Category c = event.getCategory();
       this.themeService.deleteByCategoryId(c.getId());
     }
     if ((baseEvent instanceof DelForumEvent)) {
       DelForumEvent event = (DelForumEvent)baseEvent;
       Forum f = event.getForum();
       this.themeService.deleteByForumId(f.getId());
     }
     if ((baseEvent instanceof DelUserEvent)) {
       DelUserEvent event = (DelUserEvent)baseEvent;
       User user = event.getUser();
       this.themeService.deleteByUserId(user.getId());
       List<Theme> list = this.themeService.getThemeAll();
       for (Theme t : list) {
         Posts p = this.postsService.getLastPostsByUserAndTheme(t.getId(), 
           user.getId());
         if (p != null)
           t.setLastReplyer(p.getCreater());
       }
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
   public void setThemeService(ThemeService themeService)
   {
     this.themeService = themeService;
   }
   @Autowired
   public void setPostsService(PostsService postsService) {
     this.postsService = postsService;
   }
 }


 
 
 