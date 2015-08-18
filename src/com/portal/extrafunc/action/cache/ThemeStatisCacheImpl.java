 package com.portal.extrafunc.action.cache;
 
 import com.portal.extrafunc.entity.Theme;
 import com.portal.extrafunc.service.ThemeService;
 import java.util.List;
 import net.sf.ehcache.Ehcache;
 import net.sf.ehcache.Element;
 import org.springframework.beans.factory.DisposableBean;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Qualifier;
 
 public class ThemeStatisCacheImpl
   implements ThemeStatisCache, DisposableBean
 {
   private ThemeService themeService;
   private Ehcache cache;
 
   public Integer updateStatis(Integer themeId)
   {
     Theme theme = this.themeService.findById(themeId);
     if (theme == null) {
       return null;
     }
     Element e = this.cache.get(theme.getId());
     Integer viewCount;
     //Integer viewCount;
     if (e != null)
       viewCount = Integer.valueOf(((Integer)e.getValue()).intValue() + 1);
     else {
       viewCount = Integer.valueOf(theme.getViewsCount().intValue() + 1);
     }
     this.cache.put(new Element(theme.getId(), viewCount));
     return viewCount;
   }
 
   public Integer getStatis(Integer themeId) {
     Theme theme = this.themeService.findById(themeId);
     if (theme == null) {
       return null;
     }
     Element e = this.cache.get(theme.getId());
     Integer viewCount;
     //Integer viewCount;
     if (e != null)
       viewCount = (Integer)e.getValue();
     else {
       viewCount = theme.getViewsCount();
     }
     return viewCount;
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
         Integer viewCount = (Integer)e.getValue();
         this.themeService.updateViewCount(id, viewCount);
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
       Integer viewCount = (Integer)e.getValue();
       this.themeService.updateViewCount(id, viewCount);
     }
     this.cache.removeAll();
   }
 
   @Autowired
   public void setCache(@Qualifier("themeViewCount") Ehcache cache)
   {
     this.cache = cache;
   }
   @Autowired
   public void setThemeService(ThemeService themeService) {
     this.themeService = themeService;
   }
 }


 
 
 