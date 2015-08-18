 package com.portal.extrafunc.action.fnt;
 
 import com.portal.extrafunc.service.ThemeService;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class ThemeJobAct
 {
 
   @Autowired
   private ThemeService themeService;
 
   public void themeStatusCheck()
   {
     this.themeService.themeTopCheck();
     this.themeService.themeLightCheck();
     this.themeService.themeLockCheck();
   }
 }


 
 
 