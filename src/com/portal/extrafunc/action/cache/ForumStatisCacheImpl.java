 package com.portal.extrafunc.action.cache;
 
 import com.portal.extrafunc.entity.ForumStatis;
 import com.portal.extrafunc.service.ForumStatisService;
 import com.portal.sysmgr.entity.Site;
 import java.util.List;
 import net.sf.ehcache.Ehcache;
 import net.sf.ehcache.Element;
 import org.springframework.beans.factory.DisposableBean;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Qualifier;
 
 public class ForumStatisCacheImpl
   implements ForumStatisCache, DisposableBean
 {
   private ForumStatisService statisService;
   private Ehcache cache;
 
   public ForumStatis updateStatis(Site site, Integer postsToday, Integer postsTotal)
   {
     Element e = this.cache.get(site.getId());
     ForumStatis fstatis;
     //ForumStatis fstatis;
     if (e != null)
       fstatis = (ForumStatis)e.getObjectValue();
     else {
       fstatis = this.statisService.findById(site.getId());
     }
     if (fstatis == null) {
       fstatis = this.statisService.save(site);
     }
     fstatis.setPostsToday(Integer.valueOf(fstatis.getPostsToday().intValue() + postsToday.intValue()));
     fstatis.setPostsTotal(Integer.valueOf(fstatis.getPostsTotal().intValue() + postsTotal.intValue()));
     this.cache.put(new Element(site.getId(), fstatis));
     return fstatis;
   }
 
   public ForumStatis getStatis(Site site) {
     Element e = this.cache.get(site.getId());
     ForumStatis fstatis;
     //ForumStatis fstatis;
     if (e != null)
       fstatis = (ForumStatis)e.getObjectValue();
     else {
       fstatis = this.statisService.findById(site.getId());
     }
     if (fstatis == null) {
       fstatis = this.statisService.save(site);
     }
     return fstatis;
   }
 
   public void statisToDB()
   {
     List<Integer> keys = this.cache.getKeys();
     if (keys.size() <= 0) {
       return;
     }
     for (Integer id : keys) {
       Element e = this.cache.get(id);
       if (e != null) {
         ForumStatis fstatis = (ForumStatis)e.getObjectValue();
         this.statisService.update(id, fstatis.getPostsToday(), 
           fstatis.getPostsTotal());
       }
     }
   }
 
   public void statisOneday()
   {
     List<Integer> keys = this.cache.getKeys();
     if (keys.size() <= 0) {
       return;
     }
     for (Integer id : keys) {
       Element e = this.cache.get(id);
       if (e != null) {
         ForumStatis fstatis = (ForumStatis)e.getObjectValue();
         this.statisService.updateOnday(id, fstatis.getPostsToday(), 
           fstatis.getPostsTotal());
       }
     }
     this.cache.removeAll();
   }
 
   public void destroy()
     throws Exception
   {
     List<Integer> keys = this.cache.getKeys();
     if (keys.size() <= 0) {
       return;
     }
     for (Integer id : keys) {
       Element e = this.cache.get(id);
       ForumStatis fstatis = (ForumStatis)e.getObjectValue();
       this.statisService.update(id, fstatis.getPostsToday(), 
         fstatis.getPostsTotal());
     }
     this.cache.removeAll();
   }
 
   @Autowired
   public void setStatisService(ForumStatisService statisService)
   {
     this.statisService = statisService;
   }
   @Autowired
   public void setCache(@Qualifier("forumStatis") Ehcache cache) {
     this.cache = cache;
   }
 }


 
 
 