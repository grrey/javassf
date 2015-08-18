 package com.portal.sysmgr.listener;
 
 import com.portal.extrafunc.entity.Category;
 import com.portal.extrafunc.entity.Forum;
 import com.portal.extrafunc.entity.Posts;
 import com.portal.extrafunc.service.ForumService;
 import com.portal.extrafunc.service.PostsService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelCategoryEvent;
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
 public class DelForumListener
   implements SmartApplicationListener
 {
   private ForumService forumService;
   private PostsService postsService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     if ((baseEvent instanceof DelSiteEvent)) {
       DelSiteEvent event = (DelSiteEvent)baseEvent;
       Site site = event.getSite();
       this.forumService.deleteBySiteId(site.getId());
     }
     if ((baseEvent instanceof DelCategoryEvent)) {
       DelCategoryEvent event = (DelCategoryEvent)baseEvent;
       Category c = event.getCategory();
       this.forumService.deleteByCategoryId(c.getId());
     }
     if ((baseEvent instanceof DelUserEvent)) {
       DelUserEvent event = (DelUserEvent)baseEvent;
       User user = event.getUser();
       List<Forum> list = this.forumService.getList();
       for (Forum f : list) {
         Posts p = this.postsService.getLastPostsByUserAndForum(f.getId(), 
           user.getId());
         if (p != null) {
           f.setLastTheme(p.getTheme());
           f.setLastReplyer(p.getCreater());
         }
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
   public void setForumService(ForumService forumService)
   {
     this.forumService = forumService;
   }
   @Autowired
   public void setPostsService(PostsService postsService) {
     this.postsService = postsService;
   }
 }


 
 
 