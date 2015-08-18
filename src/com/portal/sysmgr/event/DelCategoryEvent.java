 package com.portal.sysmgr.event;
 
 import com.portal.extrafunc.entity.Category;
 import org.springframework.context.ApplicationEvent;
 
 public class DelCategoryEvent extends ApplicationEvent
 {
   public DelCategoryEvent(Category c)
   {
     super(c);
   }
 
   public Category getCategory() {
     return (Category)super.getSource();
   }
 }


 
 
 