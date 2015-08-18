 package com.portal.extrafunc.action.fnt;
 
 import com.portal.extrafunc.action.cache.PostsCheckCache;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class PostsCheckAct
 {
 
   @Autowired
   private PostsCheckCache checkCache;
 
   public void refreshCheck()
   {
     this.checkCache.refreshCheck();
   }
 }


 
 
 