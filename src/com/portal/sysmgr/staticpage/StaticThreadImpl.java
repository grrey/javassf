 package com.portal.sysmgr.staticpage;
 
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.Channel;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.event.AddArticleEvent;
 import com.portal.sysmgr.event.AddOrUpdateChannelEvent;
 import com.portal.sysmgr.event.DelArticleEvent;
 import com.portal.sysmgr.event.DelChannelEvent;
 import com.portal.sysmgr.event.EmptyArticleEvent;
 import com.portal.sysmgr.event.UpdateArticleEvent;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.event.SmartApplicationListener;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class StaticThreadImpl
   implements SmartApplicationListener
 {
   private StaticPageService staticPageService;
 
   public void onApplicationEvent(ApplicationEvent baseEvent)
   {
     if ((baseEvent instanceof AddArticleEvent)) {
       Article a = ((AddArticleEvent)baseEvent).getArticle();
       a.getSite().initTime();
       this.staticPageService.staticArticle(a);
       this.staticPageService.staticIndex(a.getSite());
     }
     if ((baseEvent instanceof UpdateArticleEvent)) {
       UpdateArticleEvent event = (UpdateArticleEvent)baseEvent;
       Article a = event.getArticle();
       a.getSite().initTime();
       this.staticPageService.staticArticle(a);
       this.staticPageService.staticIndex(a.getSite());
     }
     if ((baseEvent instanceof DelArticleEvent)) {
       DelArticleEvent event = (DelArticleEvent)baseEvent;
       Article a = event.getArticle();
       a.getSite().initTime();
       this.staticPageService.deleteStaticArticle(a);
       this.staticPageService.staticIndex(a.getSite());
     }
     if ((baseEvent instanceof EmptyArticleEvent)) {
       EmptyArticleEvent event = (EmptyArticleEvent)baseEvent;
       Channel c = event.getChannel();
       c.getSite().initTime();
       this.staticPageService.deleteAllStaticArticle(c);
       this.staticPageService.staticIndex(c.getSite());
     }
     if ((baseEvent instanceof AddOrUpdateChannelEvent)) {
       AddOrUpdateChannelEvent event = (AddOrUpdateChannelEvent)baseEvent;
       Channel c = event.getChannel();
       c.getSite().initTime();
       this.staticPageService.staticChannel(c);
       this.staticPageService.staticIndex(c.getSite());
     }
     if ((baseEvent instanceof DelChannelEvent)) {
       DelChannelEvent event = (DelChannelEvent)baseEvent;
       Channel c = event.getChannel();
       c.getSite().initTime();
       this.staticPageService.deleteStaticChannel(c);
       this.staticPageService.staticIndex(c.getSite());
     }
   }
 
   public int getOrder()
   {
     return 0;
   }
 
   public boolean supportsEventType(Class<? extends ApplicationEvent> evenType)
   {
     if (evenType == AddArticleEvent.class) {
       return true;
     }
     if (evenType == UpdateArticleEvent.class) {
       return true;
     }
     if (evenType == DelArticleEvent.class) {
       return true;
     }
     if (evenType == EmptyArticleEvent.class) {
       return true;
     }
     if (evenType == AddOrUpdateChannelEvent.class) {
       return true;
     }
 
     return evenType == DelChannelEvent.class;
   }
 
   public boolean supportsSourceType(Class<?> sourceType)
   {
     if (sourceType == Article.class) {
       return true;
     }
 
     return sourceType == Channel.class;
   }
 
   @Autowired
   public void setStaticPageService(StaticPageService staticPageService)
   {
     this.staticPageService = staticPageService;
   }
 }


 
 
 