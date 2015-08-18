 package com.portal.sysmgr.event;
 
 import com.portal.doccenter.entity.Channel;
 import org.springframework.context.ApplicationEvent;
 
 public class AddOrUpdateChannelEvent extends ApplicationEvent
 {
   public AddOrUpdateChannelEvent(Channel c)
   {
     super(c);
   }
 
   public Channel getChannel() {
     return (Channel)super.getSource();
   }
 }


 
 
 