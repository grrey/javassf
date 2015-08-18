 package com.portal.extrafunc.action.fnt;
 
 import com.portal.extrafunc.entity.Forum;
 import com.portal.extrafunc.entity.Posts;
 import com.portal.extrafunc.entity.Theme;
 import com.portal.extrafunc.entity.UserForum;
 import com.portal.extrafunc.service.PostsService;
 import com.portal.extrafunc.service.ThemeService;
 import com.portal.extrafunc.service.UserForumService;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.entity.User;
 import java.util.Date;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class OperateAct
 {
 
   @Autowired
   private ThemeService themeService;
 
   @Autowired
   private PostsService postsService;
 
   @Autowired
   private UserForumService userForumService;
 
   @RequestMapping(value={"/topTheme.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String topTheme(Integer[] themeId, Integer topLevel, Date topTime, String reason, Integer pn, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     if (themeId != null) {
       User user = ContextTools.getUser(request);
       Theme theme = null;
       for (Integer tid : themeId) {
         theme = this.themeService.findById(tid);
         if (theme == null) {
           return ViewTools.pageNotFound(response);
         }
         if (user == null) {
           return ViewTools.showLogin(request, model, 
             "必须登录才可以进行置顶操作!");
         }
         if (user.getAdmin() == null) {
           return ViewTools.pageNotFound(response);
         }
         this.themeService.topTheme(tid, user.getId(), topLevel, topTime, 
           reason);
       }
       if ((pn != null) && (pn.intValue() > 1)) {
         return "redirect:" + theme.getForum().getUrl(pn);
       }
       return "redirect:" + theme.getForum().getUrl();
     }
     return ViewTools.pageNotFound(response);
   }
 
   @RequestMapping(value={"/essenaTheme.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String essenaTheme(Integer[] themeId, Date essenaTime, String reason, Integer pn, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     if (themeId != null) {
       User user = ContextTools.getUser(request);
       Theme theme = null;
       for (Integer tid : themeId) {
         theme = this.themeService.findById(tid);
         if (theme == null) {
           return ViewTools.pageNotFound(response);
         }
         if (user == null) {
           return ViewTools.showLogin(request, model, 
             "必须登录才可以进行精华操作!");
         }
         if (user.getAdmin() == null) {
           return ViewTools.pageNotFound(response);
         }
         this.themeService.essenaTheme(tid, user.getId(), essenaTime, reason);
       }
       if ((pn != null) && (pn.intValue() > 1)) {
         return "redirect:" + theme.getForum().getUrl(pn);
       }
       return "redirect:" + theme.getForum().getUrl();
     }
     return ViewTools.pageNotFound(response);
   }
 
   @RequestMapping(value={"/lightTheme.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String lightTheme(Integer[] themeId, String color, Boolean bold, Boolean italic, Date lightTime, String reason, Integer pn, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     if (themeId != null) {
       User user = ContextTools.getUser(request);
       Theme theme = null;
       for (Integer tid : themeId) {
         theme = this.themeService.findById(tid);
         if (theme == null) {
           return ViewTools.pageNotFound(response);
         }
         if (user == null) {
           return ViewTools.showLogin(request, model, 
             "必须登录才可以进行高亮操作!");
         }
         if (user.getAdmin() == null) {
           return ViewTools.pageNotFound(response);
         }
         this.themeService.lightTheme(tid, user.getId(), color, bold, italic, 
           lightTime, reason);
       }
       if ((pn != null) && (pn.intValue() > 1)) {
         return "redirect:" + theme.getForum().getUrl(pn);
       }
       return "redirect:" + theme.getForum().getUrl();
     }
     return ViewTools.pageNotFound(response);
   }
 
   @RequestMapping(value={"/lockTheme.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String lockTheme(Integer[] themeId, Date lockTime, String reason, Integer pn, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     if (themeId != null) {
       User user = ContextTools.getUser(request);
       Theme theme = null;
       for (Integer tid : themeId) {
         theme = this.themeService.findById(tid);
         if (theme == null) {
           return ViewTools.pageNotFound(response);
         }
         if (user == null) {
           return ViewTools.showLogin(request, model, 
             "必须登录才可以进行锁定操作!");
         }
         if (user.getAdmin() == null) {
           return ViewTools.pageNotFound(response);
         }
         this.themeService.lockTheme(tid, user.getId(), lockTime, reason);
       }
       if ((pn != null) && (pn.intValue() > 1)) {
         return "redirect:" + theme.getForum().getUrl(pn);
       }
       return "redirect:" + theme.getForum().getUrl();
     }
     return ViewTools.pageNotFound(response);
   }
 
   @RequestMapping(value={"/moveTheme.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String moveTheme(Integer[] themeId, Integer forumId, String reason, Integer pn, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     if (themeId != null) {
       User user = ContextTools.getUser(request);
       Theme theme = null;
       for (Integer tid : themeId) {
         theme = this.themeService.findById(tid);
         if (theme == null) {
           return ViewTools.pageNotFound(response);
         }
         if (user == null) {
           return ViewTools.showLogin(request, model, 
             "必须登录才可以进行移动操作!");
         }
         if (user.getAdmin() == null) {
           return ViewTools.pageNotFound(response);
         }
         this.themeService.moveTheme(tid, user.getId(), forumId, reason);
       }
       if ((pn != null) && (pn.intValue() > 1)) {
         return "redirect:" + theme.getForum().getUrl(pn);
       }
       return "redirect:" + theme.getForum().getUrl();
     }
     return ViewTools.pageNotFound(response);
   }
 
   @RequestMapping(value={"/shieldTheme.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String shieldTheme(Integer[] themeId, String reason, Integer pn, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     if (themeId != null) {
       User user = ContextTools.getUser(request);
       Theme theme = null;
       for (Integer tid : themeId) {
         theme = this.themeService.findById(tid);
         if (theme == null) {
           return ViewTools.pageNotFound(response);
         }
         if (user == null) {
           return ViewTools.showLogin(request, model, 
             "必须登录才可以进行屏蔽操作!");
         }
         if (user.getAdmin() == null) {
           return ViewTools.pageNotFound(response);
         }
         this.postsService.shieldTheme(tid, user.getId(), reason);
       }
       if ((pn != null) && (pn.intValue() > 1)) {
         return "redirect:" + theme.getForum().getUrl(pn);
       }
       return "redirect:" + theme.getForum().getUrl();
     }
     return ViewTools.pageNotFound(response);
   }
 
   @RequestMapping(value={"/shieldPosts.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String shieldPosts(Integer postsId, String reason, Integer pn, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Posts posts = this.postsService.findById(postsId);
     User user = ContextTools.getUser(request);
     if (posts == null) {
       return ViewTools.pageNotFound(response);
     }
     if (user == null) {
       return ViewTools.showLogin(request, model, "必须登录才可以进行屏蔽操作!");
     }
     if (user.getAdmin() == null) {
       return ViewTools.pageNotFound(response);
     }
     if (posts.getFloor().intValue() == 1)
       this.postsService.shieldTheme(posts.getTheme().getId(), user.getId(), 
         reason);
     else {
       this.postsService.shieldPosts(postsId, user.getId(), reason);
     }
     if ((pn != null) && (pn.intValue() > 1)) {
       return "redirect:" + posts.getTheme().getUrl(pn);
     }
     return "redirect:" + posts.getTheme().getUrl();
   }
 
   @RequestMapping(value={"/deleteTheme.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String deleteTheme(Integer[] themeId, String reason, Integer pn, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     if (themeId != null) {
       User user = ContextTools.getUser(request);
       Theme theme = null;
       for (Integer tid : themeId) {
         theme = this.themeService.findById(tid);
         if (theme == null) {
           return ViewTools.pageNotFound(response);
         }
         if (user == null) {
           return ViewTools.showLogin(request, model, 
             "必须登录才可以进行删除操作!");
         }
         if (user.getAdmin() == null) {
           return ViewTools.pageNotFound(response);
         }
         this.postsService.deleteTheme(tid, user.getId(), reason);
       }
       if ((pn != null) && (pn.intValue() > 1)) {
         return "redirect:" + theme.getForum().getUrl(pn);
       }
       return "redirect:" + theme.getForum().getUrl();
     }
     return ViewTools.pageNotFound(response);
   }
 
   @RequestMapping(value={"/shieldUser.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String shieldUser(Integer userId, Integer themeId, Date shieldTime, String reason, Integer pn, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     UserForum uf = this.userForumService.findById(userId);
     User user = ContextTools.getUser(request);
     if (uf == null) {
       return ViewTools.pageNotFound(response);
     }
     if (user == null) {
       return ViewTools.showLogin(request, model, "必须登录才可以进行屏蔽会员操作!");
     }
     if (user.getAdmin() == null) {
       return ViewTools.pageNotFound(response);
     }
     Theme theme = this.themeService.findById(themeId);
     if (theme == null) {
       return ViewTools.pageNotFound(response);
     }
     this.userForumService.shieldUserForum(userId, shieldTime);
     if ((pn != null) && (pn.intValue() > 1)) {
       return "redirect:" + theme.getUrl(pn);
     }
     return "redirect:" + theme.getUrl();
   }
 }


 
 
 