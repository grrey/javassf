 package com.portal.sysmgr.listener;
 
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.Channel;
 import com.portal.extrafunc.service.CommentService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.DelArticleEvent;
 import com.portal.sysmgr.event.DelSiteEvent;
 import com.portal.sysmgr.event.DelUserEvent;
 import com.portal.sysmgr.event.EmptyArticleEvent;
 import com.portal.usermgr.entity.User;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.event.SmartApplicationListener;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class DelCommentListener
   implements SmartApplicationListener
 {
   private CommentService commentService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     if ((baseEvent instanceof DelUserEvent)) {
       DelUserEvent event = (DelUserEvent)baseEvent;
       User user = event.getUser();
       this.commentService.deleteByUserId(user.getId());
     }
     if ((baseEvent instanceof DelArticleEvent)) {
       DelArticleEvent event = (DelArticleEvent)baseEvent;
       Integer type = event.getType();
       if (type.intValue() == 0) {
         Article a = event.getArticle();
         this.commentService.deleteByDocId(a.getId());
       }
     }
     if ((baseEvent instanceof DelSiteEvent)) {
       DelSiteEvent event = (DelSiteEvent)baseEvent;
       Site site = event.getSite();
       this.commentService.deleteBySiteId(site.getId());
     }
     if ((baseEvent instanceof EmptyArticleEvent)) {
       EmptyArticleEvent event = (EmptyArticleEvent)baseEvent;
       Channel c = event.getChannel();
       this.commentService.deleteByTreeNumber(c.getNumber());
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
     if (evenType == DelUserEvent.class) {
       return true;
     }
     if (evenType == DelArticleEvent.class) {
       return true;
     }
 
     return evenType == EmptyArticleEvent.class;
   }
 
   public boolean supportsSourceType(Class<?> sourceType)
   {
     if (sourceType == Site.class) {
       return true;
     }
     if (sourceType == User.class) {
       return true;
     }
     if (sourceType == Article.class) {
       return true;
     }
 
     return sourceType == Channel.class;
   }
 
   @Autowired
   public void setCommentService(CommentService commentService)
   {
     this.commentService = commentService;
   }
 }


 
 
 