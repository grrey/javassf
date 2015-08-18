 package com.portal.extrafunc.action.fnt;
 
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.extrafunc.action.cache.PostsCheckCache;
 import com.portal.extrafunc.action.cache.ThemeStatisCache;
 import com.portal.extrafunc.entity.Forum;
 import com.portal.extrafunc.entity.Posts;
 import com.portal.extrafunc.entity.Theme;
 import com.portal.extrafunc.entity.UserForum;
 import com.portal.extrafunc.service.ForumService;
 import com.portal.extrafunc.service.PostsService;
 import com.portal.extrafunc.service.ThemeService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.entity.User;
 import java.util.Date;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.multipart.MultipartHttpServletRequest;
 
 @Controller
 public class ThemeAct
 {
   public static final String THEME_LIST = "tpl.themeList";
   public static final String THEME_DETAIL = "tpl.themeDetail";
   public static final String THEME_INPUT = "tpl.themeInput";
   public static final String REPLY_INPUT = "tpl.replyInput";
   public static final String QUOTE_INPUT = "tpl.quoteInput";
   public static final String NOT_IRRIGATION = "tpl.notIrrigation";
   public static final String EDIT_INPUT = "tpl.editInput";
 
   @Autowired
   private ThemeService themeService;
 
   @Autowired
   private PostsService postsService;
 
   @Autowired
   private ForumService forumService;
 
   @Autowired
   private ThemeStatisCache statisCache;
 
   @Autowired
   private PostsCheckCache postsCheckCache;
 
   @RequestMapping(value={"/themeList-{forumId:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String themeList(@PathVariable Integer forumId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     return themeListPage(forumId, Integer.valueOf(1), request, response, model);
   }
 
   @RequestMapping(value={"/themeList-{forumId:[0-9]+}_{page:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String themeListPage(@PathVariable Integer forumId, @PathVariable Integer page, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Forum forum = this.forumService.findById(forumId);
     if (forum == null) {
       return ViewTools.pageNotFound(response);
     }
     ViewTools.frontData(request, model, site);
     ViewTools.frontPageData(request, model, page);
     model.addAttribute("forum", forum);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/forum", "tpl.themeList");
   }
 
   @RequestMapping(value={"/themeDetail-{themeId:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String themeDetail(@PathVariable Integer themeId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     return themeDetailPage(themeId, Integer.valueOf(1), request, response, model);
   }
 
   @RequestMapping(value={"/themeDetail-{themeId:[0-9]+}_{page:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String themeDetailPage(@PathVariable Integer themeId, @PathVariable Integer page, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Theme theme = this.themeService.findById(themeId);
     if (theme == null) {
       return ViewTools.pageNotFound(response);
     }
     model.addAttribute("theme", this.themeService.findById(themeId));
     ViewTools.frontData(request, model, site);
     ViewTools.frontPageData(request, model, page);
     this.statisCache.updateStatis(themeId);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/forum", "tpl.themeDetail");
   }
 
   @RequestMapping(value={"/themeInput-{forumId:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String themeInput(@PathVariable Integer forumId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (user == null) {
       return ViewTools.showLogin(request, model, "必须登录才可以发帖!");
     }
     Forum forum = this.forumService.findById(forumId);
     if (forum == null) {
       return ViewTools.pageNotFound(response);
     }
     if ((user.getUserForum() != null) && 
       (!user.getUserForum().getStatus().equals(Integer.valueOf(0)))) {
       return ViewTools.showMessage(forum.getUrl(), request, model, 
         "对不起，您的账号已经被管理员屏蔽，无法发帖!", Integer.valueOf(0));
     }
     ViewTools.frontData(request, model, site);
     model.addAttribute("forum", forum);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/forum", "tpl.themeInput");
   }
 
   @RequestMapping(value={"/themeSave.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String themeSubmit(Integer forumId, String title, String content, @RequestParam(value="code", required=false) List<String> code, MultipartHttpServletRequest mrequest, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (user == null) {
       return ViewTools.showLogin(request, model, "必须登录才可以发帖!");
     }
     Forum forum = this.forumService.findById(forumId);
     if (forum == null) {
       return ViewTools.pageNotFound(response);
     }
     Date d = this.postsCheckCache.postsTime(user.getUsername());
     if (!checkPostsTime(d)) {
       model.addAttribute("interval", Long.valueOf(this.postsCheckCache.getInterval()));
       ViewTools.frontData(request, model, site);
       return ViewTools.getTplPath(request, site.getSolutionPath(), 
         "extrafunc/forum", "tpl.notIrrigation");
     }
     this.postsCheckCache.updateCheck(user.getUsername());
     String ip = ServicesUtils.getIpAddr(request);
     Posts posts = this.postsService.saveTheme(site.getId(), user.getId(), 
       forumId, title, content, ip, mrequest.getFiles("attachment"), 
       code);
     return "redirect:" + posts.getTheme().getUrl();
   }
 
   @RequestMapping(value={"/replyInput-{themeId:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String replyInput(@PathVariable Integer themeId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (user == null) {
       return ViewTools.showLogin(request, model, "必须登录才可以回复!");
     }
     Theme theme = this.themeService.findById(themeId);
     if (theme == null) {
       return ViewTools.pageNotFound(response);
     }
     if (theme.getLock().booleanValue()) {
       return ViewTools.showMessage(theme.getUrl(), request, model, 
         "对不起，该主题已经被锁定，无法回复!", Integer.valueOf(0));
     }
     if ((user.getUserForum() != null) && 
       (!user.getUserForum().getStatus().equals(Integer.valueOf(0)))) {
       return ViewTools.showMessage(theme.getUrl(), request, model, 
         "对不起，您的账号已经被管理员屏蔽，无法发帖!", Integer.valueOf(0));
     }
     ViewTools.frontData(request, model, site);
     model.addAttribute("theme", theme);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/forum", "tpl.replyInput");
   }
 
   @RequestMapping(value={"/replyInput-{themeId:[0-9]+}-{floor:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String replyFloorInput(@PathVariable Integer themeId, @PathVariable Integer floor, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (user == null) {
       return ViewTools.showLogin(request, model, "必须登录才可以回复!");
     }
     Theme theme = this.themeService.findById(themeId);
     if (theme == null) {
       return ViewTools.pageNotFound(response);
     }
     if (theme.getLock().booleanValue()) {
       return ViewTools.showMessage(theme.getUrl(), request, model, 
         "对不起，该主题已经被锁定，无法回复!", Integer.valueOf(0));
     }
     if ((user.getUserForum() != null) && 
       (!user.getUserForum().getStatus().equals(Integer.valueOf(0)))) {
       return ViewTools.showMessage(theme.getUrl(), request, model, 
         "对不起，您的账号已经被管理员屏蔽，无法发帖!", Integer.valueOf(0));
     }
     ViewTools.frontData(request, model, site);
     model.addAttribute("theme", theme);
     model.addAttribute("floor", floor);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/forum", "tpl.replyInput");
   }
 
   @RequestMapping(value={"/quoteInput-{postsId:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String quoteInput(@PathVariable Integer postsId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (user == null) {
       return ViewTools.showLogin(request, model, "必须登录才可以回复!");
     }
     Posts posts = this.postsService.findById(postsId);
     if (posts == null) {
       return ViewTools.pageNotFound(response);
     }
     if (posts.getTheme().getLock().booleanValue()) {
       return ViewTools.showMessage(posts.getTheme().getUrl(), request, 
         model, "对不起，该主题已经被锁定，无法回复!", Integer.valueOf(0));
     }
     if ((user.getUserForum() != null) && 
       (!user.getUserForum().getStatus().equals(Integer.valueOf(0)))) {
       return ViewTools.showMessage(posts.getTheme().getUrl(), request, 
         model, "对不起，您的账号已经被管理员屏蔽，无法发帖!", Integer.valueOf(0));
     }
     if ((user.getAdmin() != null) || 
       ((posts.getHidden().booleanValue()) && (posts.getCreater().equals(user))) || 
       (posts.getTheme().getReplyUser()
       .indexOf("," + user.getId() + ",") > -1))
       model.addAttribute("showhide", Boolean.valueOf(true));
     else {
       model.addAttribute("showhide", Boolean.valueOf(false));
     }
     ViewTools.frontData(request, model, site);
     model.addAttribute("posts", posts);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/forum", "tpl.quoteInput");
   }
 
   @RequestMapping(value={"/replySave.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String replySubmit(Integer themeId, Integer quoteId, String title, String content, @RequestParam(value="code", required=false) List<String> code, MultipartHttpServletRequest mrequest, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (user == null) {
       return ViewTools.showLogin(request, model, "必须登录才可以回复!");
     }
     Theme theme = this.themeService.findById(themeId);
     if (theme == null) {
       return ViewTools.pageNotFound(response);
     }
     if (theme.getLock().booleanValue()) {
       return ViewTools.showMessage(theme.getUrl(), request, model, 
         "对不起，该主题已经被锁定，无法回复!", Integer.valueOf(0));
     }
     Date d = this.postsCheckCache.postsTime(user.getUsername());
     if (!checkPostsTime(d)) {
       model.addAttribute("interval", Long.valueOf(this.postsCheckCache.getInterval()));
       ViewTools.frontData(request, model, site);
       return ViewTools.getTplPath(request, site.getSolutionPath(), 
         "extrafunc/forum", "tpl.notIrrigation");
     }
     this.postsCheckCache.updateCheck(user.getUsername());
     String ip = ServicesUtils.getIpAddr(request);
     this.postsService.savePosts(site.getId(), user.getId(), themeId, quoteId, 
       title, content, ip, mrequest.getFiles("attachment"), code);
     return "redirect:" + theme.getUrl();
   }
 
   @RequestMapping(value={"/editInput-{postId:[0-9]+}.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String editInput(@PathVariable Integer postId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (user == null) {
       return ViewTools.showLogin(request, model, "必须登录才可以发帖!");
     }
     Posts posts = this.postsService.findById(postId);
     if (posts == null) {
       return ViewTools.pageNotFound(response);
     }
     if ((user.getUserForum() != null) && 
       (!user.getUserForum().getStatus().equals(Integer.valueOf(0)))) {
       return ViewTools.showMessage(posts.getTheme().getUrl(), request, 
         model, "对不起，您的账号已经被管理员屏蔽，无法发帖!", Integer.valueOf(0));
     }
     if ((user.getAdmin() == null) && (!posts.getCreater().equals(user))) {
       return ViewTools.pageNotFound(response);
     }
     ViewTools.frontData(request, model, site);
     model.addAttribute("posts", posts);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "extrafunc/forum", "tpl.editInput");
   }
 
   @RequestMapping(value={"/editSave.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String editSubmit(Integer postId, String title, String content, @RequestParam(value="code", required=false) List<String> code, MultipartHttpServletRequest mrequest, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (user == null) {
       return ViewTools.showLogin(request, model, "必须登录才可以发帖!");
     }
     Posts posts = this.postsService.findById(postId);
     if (posts == null) {
       return ViewTools.pageNotFound(response);
     }
     if ((user.getAdmin() == null) && (!posts.getCreater().equals(user))) {
       return ViewTools.pageNotFound(response);
     }
     Date d = this.postsCheckCache.postsTime(user.getUsername());
     if (!checkPostsTime(d)) {
       model.addAttribute("interval", Long.valueOf(this.postsCheckCache.getInterval()));
       ViewTools.frontData(request, model, site);
       return ViewTools.getTplPath(request, site.getSolutionPath(), 
         "extrafunc/forum", "tpl.notIrrigation");
     }
     this.postsCheckCache.updateCheck(user.getUsername());
     String ip = ServicesUtils.getIpAddr(request);
     posts = this.postsService.updatePosts(postId, user.getId(), title, content, 
       ip, mrequest.getFiles("attachment"), code);
     return "redirect:" + posts.getTheme().getUrl();
   }
 
   private boolean checkPostsTime(Date d) {
     if (d == null) {
       return true;
     }
     long second = System.currentTimeMillis() - d.getTime();
     second /= 1000L;
 
     return second > this.postsCheckCache.getInterval();
   }
 }


 
 
 