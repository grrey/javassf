 package com.portal.sysmgr.event;
 
 import com.portal.doccenter.entity.Channel;
 import org.springframework.context.ApplicationEvent;
 
 public class EmptyArticleEvent extends ApplicationEvent
 {
   public EmptyArticleEvent(Channel c)
   {
     super(c);
   }
 
   public Channel getChannel() {
     return (Channel)super.getSource();
   }
 }


 
 
 