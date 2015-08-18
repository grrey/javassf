 package com.portal.sysmgr.event;
 
 import com.portal.usermgr.entity.Role;
 import org.springframework.context.ApplicationEvent;
 
 public class DelRoleEvent extends ApplicationEvent
 {
   public DelRoleEvent(Role role)
   {
     super(role);
   }
 
   public Role getRole() {
     return (Role)super.getSource();
   }
 }


 
 
 