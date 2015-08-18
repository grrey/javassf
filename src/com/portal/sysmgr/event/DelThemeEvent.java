 package com.portal.sysmgr.event;
 
 import com.portal.extrafunc.entity.Theme;
 import org.springframework.context.ApplicationEvent;
 
 public class DelThemeEvent extends ApplicationEvent
 {
   public DelThemeEvent(Theme theme)
   {
     super(theme);
   }
 
   public Theme getTheme() {
     return (Theme)super.getSource();
   }
 }


 
 
 