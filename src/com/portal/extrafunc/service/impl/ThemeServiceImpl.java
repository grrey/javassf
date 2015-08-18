 package com.portal.extrafunc.service.impl;
 
 import com.portal.extrafunc.action.cache.ForumCache;
 import com.portal.extrafunc.action.cache.ForumStatisCache;
 import com.portal.extrafunc.dao.ThemeDao;
 import com.portal.extrafunc.entity.Theme;
 import com.portal.extrafunc.service.ForumOperateService;
 import com.portal.extrafunc.service.ForumService;
 import com.portal.extrafunc.service.ThemeService;
 import com.portal.extrafunc.service.ThemeTxtService;
 import com.portal.sysmgr.event.DelThemeEvent;
 import com.portal.sysmgr.service.SiteService;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.UserService;
 import java.sql.Timestamp;
 import java.util.Date;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.ApplicationContext;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ThemeServiceImpl
   implements ThemeService
 {
   private ThemeDao dao;
   private UserService userService;
   private ForumService forumService;
   private SiteService siteService;
   private ThemeTxtService txtService;
   private ForumCache forumCache;
   private ForumStatisCache statisCache;
   private ForumOperateService operateService;
   private ApplicationContext applicationContext;
 
   @Transactional(readOnly=true)
   public Page<Theme> getPage(int pageNo, int pageSize)
   {
     return this.dao.getPage(pageNo, pageSize);
   }
 
   @Transactional(readOnly=true)
   public Page<Theme> getThemePageForTag(Integer forumId, Integer status, Integer createrId, Integer replyId, int orderBy, int pageNo, int pageSize)
   {
     return this.dao.getThemePageForTag(forumId, status, createrId, replyId, 
       orderBy, pageNo, pageSize);
   }
   @Transactional(readOnly=true)
   public List<Theme> getThemeAll() {
     return this.dao.getThemeAll();
   }
   @Transactional(readOnly=true)
   public Theme findById(Integer id) {
     Theme entity = (Theme)this.dao.findById(id);
     return entity;
   }
 
   public Theme save(Integer siteId, Integer userId, Integer forumId, String title, boolean img, boolean affix)
   {
     Theme theme = new Theme();
     theme.setCreater(this.userService.findById(userId));
     theme.setLastReplyer(this.userService.findById(userId));
     theme.setSite(this.siteService.findById(siteId));
     theme.setForum(this.forumService.findById(forumId));
     theme.setTitle(title);
     theme.setAffix(Boolean.valueOf(affix));
     theme.setImg(Boolean.valueOf(img));
     theme.init();
     this.dao.save(theme);
     this.txtService.save(theme);
     this.forumCache.updateForum(theme, Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0));
     this.statisCache.updateStatis(this.siteService.findById(siteId), Integer.valueOf(1), Integer.valueOf(1));
     return theme;
   }
 
   public Theme updateReply(Theme theme, User user, boolean affix) {
     if (!theme.getAffix().booleanValue()) {
       theme.setAffix(Boolean.valueOf(affix));
     }
     if (user.getAdmin() != null) {
       theme.setModerReply(Boolean.valueOf(true));
     }
     theme.setLastReplyer(user);
     theme.setLastReplyTime(new Timestamp(System.currentTimeMillis()));
     theme.setReplyCount(Integer.valueOf(theme.getReplyCount().intValue() + 1));
     this.txtService.update(theme.getId(), user.getId());
     this.forumCache.updateForum(theme, Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(0), Integer.valueOf(1));
     this.statisCache.updateStatis(theme.getSite(), Integer.valueOf(1), Integer.valueOf(1));
     return theme;
   }
 
   public Theme topTheme(Integer themeId, Integer userId, Integer topLevel, Date topTime, String reason)
   {
     Theme theme = findById(themeId);
     theme.setStatus(topLevel);
     theme.setTopTime(topTime);
     this.operateService.save(theme.getId(), "THEME", "置顶", reason, 
       theme.getSite(), this.userService.findById(userId));
     return theme;
   }
 
   public Theme essenaTheme(Integer themeId, Integer userId, Date essenaTime, String reason)
   {
     Theme theme = findById(themeId);
     theme.setEssena(Boolean.valueOf(true));
     theme.setEssenaTime(essenaTime);
     this.operateService.save(theme.getId(), "THEME", "精华", reason, 
       theme.getSite(), this.userService.findById(userId));
     return theme;
   }
 
   public Theme lightTheme(Integer themeId, Integer userId, String color, Boolean bold, Boolean italic, Date lightTime, String reason)
   {
     Theme theme = findById(themeId);
     theme.setBold(bold);
     theme.setColor(color);
     theme.setItalic(italic);
     theme.setLightTime(lightTime);
     this.operateService.save(theme.getId(), "THEME", "高亮", reason, 
       theme.getSite(), this.userService.findById(userId));
     return theme;
   }
 
   public Theme lockTheme(Integer themeId, Integer userId, Date lockTime, String reason)
   {
     Theme theme = findById(themeId);
     theme.setLock(Boolean.valueOf(true));
     theme.setLockTime(lockTime);
     this.operateService.save(theme.getId(), "THEME", "锁定", reason, 
       theme.getSite(), this.userService.findById(userId));
     return theme;
   }
 
   public Theme moveTheme(Integer themeId, Integer userId, Integer forumId, String reason)
   {
     Theme theme = findById(themeId);
     theme.setForum(this.forumService.findById(forumId));
     this.operateService.save(theme.getId(), "THEME", "移动", reason, 
       theme.getSite(), this.userService.findById(userId));
     return theme;
   }
 
   public void themeTopCheck() {
     List<Theme> list = this.dao.getThemeByTop();
     for (Theme theme : list)
       if (theme.getCheckTopTime()) {
         theme.setStatus(Integer.valueOf(0));
         theme.setTopTime(null);
       }
   }
 
   public void themeLightCheck()
   {
     List<Theme> list = this.dao.getThemeByLight();
     for (Theme theme : list)
       if (theme.getCheckLightTime()) {
         theme.setColor(null);
         theme.setItalic(Boolean.valueOf(false));
         theme.setLightTime(null);
       }
   }
 
   public void themeLockCheck()
   {
     List<Theme> list = this.dao.getThemeByLock();
     for (Theme theme : list)
       if (theme.getCheckLockTime()) {
         theme.setLock(Boolean.valueOf(false));
         theme.setLockTime(null);
       }
   }
 
   public Theme update(Theme bean)
   {
     bean = (Theme)this.dao.update(bean);
     return bean;
   }
 
   public Theme updateViewCount(Integer themeId, Integer viewCount) {
     Theme theme = findById(themeId);
     theme.setViewsCount(viewCount);
     return theme;
   }
 
   public int deleteByForumId(Integer forumId) {
     this.txtService.deleteByForumId(forumId);
     return this.dao.deleteByForumId(forumId);
   }
 
   public int deleteByUserId(Integer userId) {
     return this.dao.deleteByUserId(userId);
   }
 
   public int deleteBySiteId(Integer siteId) {
     this.txtService.deleteBySiteId(siteId);
     return this.dao.deleteBySiteId(siteId);
   }
 
   public int deleteByCategoryId(Integer categoryId) {
     return this.dao.deleteByCategoryId(categoryId);
   }
 
   public Theme deleteById(Integer id) {
     Theme bean = (Theme)this.dao.deleteById(id);
     this.applicationContext.publishEvent(new DelThemeEvent(bean));
     return bean;
   }
 
   public Theme[] deleteByIds(Integer[] ids) {
     Theme[] beans = new Theme[ids.length];
     int i = 0; for (int len = ids.length; i < len; i++) {
       beans[i] = deleteById(ids[i]);
     }
     return beans;
   }
 
   @Autowired
   public void setDao(ThemeDao dao)
   {
     this.dao = dao;
   }
   @Autowired
   public void setUserService(UserService userService) {
     this.userService = userService;
   }
   @Autowired
   public void setForumService(ForumService forumService) {
     this.forumService = forumService;
   }
   @Autowired
   public void setSiteService(SiteService siteService) {
     this.siteService = siteService;
   }
   @Autowired
   public void setTxtService(ThemeTxtService txtService) {
     this.txtService = txtService;
   }
   @Autowired
   public void setForumCache(ForumCache forumCache) {
     this.forumCache = forumCache;
   }
   @Autowired
   public void setStatisCache(ForumStatisCache statisCache) {
     this.statisCache = statisCache;
   }
   @Autowired
   public void setOperateService(ForumOperateService operateService) {
     this.operateService = operateService;
   }
   @Autowired
   public void setApplicationContext(ApplicationContext applicationContext) {
     this.applicationContext = applicationContext;
   }
 }


 
 
 