 package com.portal.extrafunc.action.cache;
 
 import com.portal.extrafunc.entity.Forum;
 import com.portal.extrafunc.entity.Theme;
 import com.portal.extrafunc.service.ForumService;
 import java.util.List;
 import net.sf.ehcache.Ehcache;
 import net.sf.ehcache.Element;
 import org.springframework.beans.factory.DisposableBean;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Qualifier;
 
 public class ForumCacheImpl
   implements ForumCache, DisposableBean
 {
   private ForumService forumService;
   private Ehcache cache;
 
   public Forum updateForum(Theme theme, Integer themeTotal, Integer replyTotal, Integer themeToday, Integer replyToday)
   {
     Integer forumId = theme.getForum().getId();
     Element e = this.cache.get(forumId);
     Forum f;
     //Forum f;
     if (e != null)
       f = (Forum)e.getObjectValue();
     else {
       f = this.forumService.findById(forumId);
     }
     if (f == null) {
       return null;
     }
     f.setThemeTotal(Integer.valueOf(f.getThemeTotal().intValue() + themeTotal.intValue()));
     f.setReplyTotal(Integer.valueOf(f.getReplyTotal().intValue() + replyTotal.intValue()));
     f.setThemeToday(Integer.valueOf(f.getThemeToday().intValue() + themeToday.intValue()));
     f.setReplyToday(Integer.valueOf(f.getReplyToday().intValue() + replyToday.intValue()));
     f.setLastTheme(theme);
     f.setLastReplyer(theme.getLastReplyer());
     this.cache.put(new Element(forumId, f));
     return f;
   }
 
   public Forum getForum(Integer forumId) {
     Element e = this.cache.get(forumId);
     Forum f;
     //Forum f;
     if (e != null)
       f = (Forum)e.getObjectValue();
     else {
       f = this.forumService.findById(forumId);
     }
     if (f == null) {
       return null;
     }
     return f;
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
         Forum forum = (Forum)e.getObjectValue();
         this.forumService.updateForum(forum);
       }
     }
     this.cache.removeAll();
   }
 
   public void statisOneday() {
     List<Forum> flist = this.forumService.getList();
     for (Forum f : flist) {
       Element e = this.cache.get(f.getId());
       if (e != null) {
         Forum forum = (Forum)e.getObjectValue();
         this.forumService.updateForumOnday(forum);
       } else {
         this.forumService.updateForumOnday(f);
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
       Forum forum = (Forum)e.getObjectValue();
       this.forumService.updateForum(forum);
     }
     this.cache.removeAll();
   }
 
   @Autowired
   public void setForumService(ForumService forumService)
   {
     this.forumService = forumService;
   }
   @Autowired
   public void setCache(@Qualifier("forum") Ehcache cache) {
     this.cache = cache;
   }
 }


 
 
 