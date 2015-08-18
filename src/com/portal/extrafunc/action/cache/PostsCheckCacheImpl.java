 package com.portal.extrafunc.action.cache;
 
 import java.util.Date;
 import java.util.List;
 import net.sf.ehcache.Ehcache;
 import net.sf.ehcache.Element;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Qualifier;
 
 public class PostsCheckCacheImpl
   implements PostsCheckCache
 {
   private long interval;
   private Ehcache cache;
 
   public void updateCheck(String username)
   {
     Date d = new Date();
     this.cache.put(new Element(username, d));
   }
 
   public Date postsTime(String username) {
     Element e = this.cache.get(username);
     if (e != null) {
       Date d = (Date)e.getValue();
       return d;
     }
     return null;
   }
 
   public void refreshCheck()
   {
     List<String> keys = this.cache.getKeys();
     if (keys.size() <= 0) {
       return;
     }
     for (String key : keys) {
       Element e = this.cache.get(key);
       if (e != null) {
         Date d = (Date)e.getValue();
         long second = System.currentTimeMillis() - d.getTime();
         second /= 1000L;
         if (second > this.interval)
           this.cache.remove(key);
       }
     }
   }
 
   public long getInterval()
   {
     return this.interval;
   }
 
   public void setInterval(long interval) {
     this.interval = interval;
   }
   
   @Autowired
   public void setCache(@Qualifier("postsCheck") Ehcache cache) {
     this.cache = cache;
   }
 }


 
 
 