 package com.portal.sysmgr.event;
 
 import com.portal.usermgr.entity.User;
 import org.springframework.context.ApplicationEvent;
 
 public class DelUserEvent extends ApplicationEvent
 {
   public DelUserEvent(User user)
   {
     super(user);
   }
 
   public User getUser() {
     return (User)super.getSource();
   }
 }


 
 
 