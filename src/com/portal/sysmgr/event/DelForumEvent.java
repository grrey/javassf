 package com.portal.sysmgr.event;
 
 import com.portal.extrafunc.entity.Forum;
 import org.springframework.context.ApplicationEvent;
 
 public class DelForumEvent extends ApplicationEvent
 {
   public DelForumEvent(Forum forum)
   {
     super(forum);
   }
 
   public Forum getForum() {
     return (Forum)super.getSource();
   }
 }


 
 
 