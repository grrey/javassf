 package com.portal.sysmgr.event;
 
 import com.portal.usermgr.entity.Depart;
 import org.springframework.context.ApplicationEvent;
 
 public class DelDepartEvent extends ApplicationEvent
 {
   public DelDepartEvent(Depart depart)
   {
     super(depart);
   }
 
   public Depart getDepart() {
     return (Depart)super.getSource();
   }
 }


 
 
 