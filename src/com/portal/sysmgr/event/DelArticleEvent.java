 package com.portal.sysmgr.event;
 
 import com.portal.doccenter.entity.Article;
 import org.springframework.context.ApplicationEvent;
 
 public class DelArticleEvent extends ApplicationEvent
 {
   private int type;
 
   public DelArticleEvent(Article a, Integer type)
   {
     super(a);
     this.type = type.intValue();
   }
 
   public Article getArticle() {
     return (Article)super.getSource();
   }
 
   public Integer getType() {
     return Integer.valueOf(this.type);
   }
 }


 
 
 