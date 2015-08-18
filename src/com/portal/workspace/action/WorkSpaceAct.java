 package com.portal.workspace.action;
 
 import com.javassf.basic.security.encoder.PwdEncoder;
 import com.portal.doccenter.service.ArticleService;
 import com.portal.doccenter.service.ChannelService;
 import com.portal.extrafunc.service.CommentService;
 import com.portal.extrafunc.service.MessageBoardService;
 import com.portal.extrafunc.service.PostsService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.MemberService;
 import com.portal.usermgr.service.UserService;
 import java.util.Properties;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class WorkSpaceAct
 {
 
   @Autowired
   private UserService userService;
 
   @Autowired
   private MemberService memberService;
 
   @Autowired
   private ArticleService articleService;
 
   @Autowired
   private ChannelService channelService;
 
   @Autowired
   private CommentService commentService;
 
   @Autowired
   private MessageBoardService messageService;
 
   @Autowired
   private PostsService postsService;
 
   @Autowired
   private PwdEncoder pwdEncoder;
 
   @RequiresPermissions({"admin:workspace:index"})//判断用户是否有该权限
   @RequestMapping({"/index.do"})
   public String index(HttpServletRequest request, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     model.addAttribute("site", site);
     model.addAttribute("user", user);
     return "index";//跳转到 /console/index.html 
   }
   @RequiresPermissions({"admin:workspace:right"})
   @RequestMapping({"/right.do"})
   public String right(HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     Properties props = System.getProperties();
     Runtime runtime = Runtime.getRuntime();
     long freeMemoery = runtime.freeMemory();
     long totalMemory = runtime.totalMemory();
     long usedMemory = totalMemory - freeMemoery;
     long maxMemory = runtime.maxMemory();
     long useableMemory = maxMemory - totalMemory + freeMemoery;
     int checkarti = this.articleService.getAllArtiCount(site.getId(), true);
     int allarti = this.articleService.getAllArtiCount(site.getId(), false);
     int allchannel = this.channelService.getAllChannelCount(site.getId());
     int alluser = this.userService.getAllUserCount();
     int allcomment = this.commentService.getAllCommentCount(site.getId()).intValue();
     int allmessage = this.messageService.getAllMessageCount(site.getId()).intValue();
     int allpost = this.postsService.getAllPostCount(site.getId()).intValue();
     int nocheckuser = this.memberService.getNoCheckMemberCount();
     int norepmessage = this.messageService.getNoRepMessageCount(site.getId()).intValue();
     model.addAttribute("props", props);
     model.addAttribute("freeMemoery", Long.valueOf(freeMemoery));
     model.addAttribute("totalMemory", Long.valueOf(totalMemory));
     model.addAttribute("usedMemory", Long.valueOf(usedMemory));
     model.addAttribute("maxMemory", Long.valueOf(maxMemory));
     model.addAttribute("useableMemory", Long.valueOf(useableMemory));
     model.addAttribute("site", site);
     model.addAttribute("user", user);
     model.addAttribute("allarti", Integer.valueOf(allarti));
     model.addAttribute("allchannel", Integer.valueOf(allchannel));
     model.addAttribute("alluser", Integer.valueOf(alluser));
     model.addAttribute("allcomment", Integer.valueOf(allcomment));
     model.addAttribute("allmessage", Integer.valueOf(allmessage));
     model.addAttribute("allpost", Integer.valueOf(allpost));
     model.addAttribute("checkarti", Integer.valueOf(checkarti));
     model.addAttribute("nocheckuser", Integer.valueOf(nocheckuser));
     model.addAttribute("norepmessage", Integer.valueOf(norepmessage));
     return "right"; }
 
   @RequiresPermissions({"admin:workspace:left"})
   @RequestMapping({"/left.do"})
   public String left(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/html;charset=UTF-8");
     return "frame/workspace_left";
   }
   @RequiresPermissions({"admin:workspace:info"})
   @RequestMapping({"/workSpace/v_info.do"})
   public String profileEdit(HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     model.addAttribute("user", user);
     model.addAttribute("siteId", site.getId());
     return "workSpace/personInfo"; }
 
   @RequiresPermissions({"admin:workspace:infoupdate"})
   @RequestMapping({"/workSpace/o_info_update.do"})
   public String profileUpdate(User user, HttpServletRequest request, ModelMap model) {
     this.userService.update(user);
     model.addAttribute("msg", "信息修改成功!");
     return profileEdit(request, model);
   }
   @RequiresPermissions({"admin:workspace:passedit"})
   @RequestMapping({"/workSpace/v_passwordEdit.do"})
   public String passwordEdit(HttpServletRequest request, ModelMap model) { User user = ContextTools.getUser(request);
     model.addAttribute("user", user);
     return "workSpace/editPass"; }
 
   @RequiresPermissions({"admin:workspace:passupdate"})
   @RequestMapping({"/workSpace/o_pass_update.do"})
   public String passwordUpdate(String password, String newPwd, HttpServletRequest request, ModelMap model) {
     User user = ContextTools.getUser(request);
     User u = this.userService.findById(user.getId());
     if (u.getPassword().equals(this.pwdEncoder.encodePassword(password))) {
       u.setPassword(this.pwdEncoder.encodePassword(newPwd));
       this.userService.update(u);
       model.addAttribute("msg", "密码修改成功!");
       model.addAttribute("result", "1");
     } else {
       model.addAttribute("msg", "原密码错误，修改失败!");
       model.addAttribute("result", "0");
     }
     return passwordEdit(request, model);
   }
 }


 
 
 