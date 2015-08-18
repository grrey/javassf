 package com.portal.extrafunc.action.fnt;
 
 import com.portal.extrafunc.action.cache.ThemeStatisCache;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class ThemeStatisAct
 {
 
   @Autowired
   private ThemeStatisCache statisCache;
 
   public void statisToDb()
   {
     this.statisCache.statisToDB();
   }
 }


 
 
 