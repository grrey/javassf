 package com.portal.sysmgr.event;
 
 import com.portal.extrafunc.entity.AdvertSlot;
 import org.springframework.context.ApplicationEvent;
 
 public class DelAdvertSlotEvent extends ApplicationEvent
 {
   public DelAdvertSlotEvent(AdvertSlot slot)
   {
     super(slot);
   }
 
   public AdvertSlot getAdvertSlot() {
     return (AdvertSlot)super.getSource();
   }
 }


 
 
 