 package com.portal.extrafunc.action.fnt;
 
 import com.portal.extrafunc.action.cache.ForumCache;
 import com.portal.extrafunc.action.cache.ForumStatisCache;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class ForumStatisAct
 {
 
   @Autowired
   private ForumStatisCache statisCache;
 
   @Autowired
   private ForumCache forumCache;
 
   public void statisToDb()
   {
     this.statisCache.statisToDB();
   }
 
   public void statisOneday() {
     this.statisCache.statisOneday();
   }
 
   public void forumToDb() {
     this.forumCache.statisToDB();
   }
 
   public void forumOneday() {
     this.forumCache.statisOneday();
   }
 }


 
 
 