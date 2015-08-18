 package com.portal.sysmgr.event;
 
 import com.portal.doccenter.entity.Article;
 import org.springframework.context.ApplicationEvent;
 
 public class AddArticleEvent extends ApplicationEvent
 {
   public AddArticleEvent(Article a)
   {
     super(a);
   }
 
   public Article getArticle() {
     return (Article)super.getSource();
   }
 }


 
 
 