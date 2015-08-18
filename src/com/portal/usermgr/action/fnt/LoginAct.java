 package com.portal.usermgr.action.fnt;
 
 import com.javassf.basic.security.encoder.PwdEncoder;
 import com.javassf.basic.utils.DateUtils;
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.AdminService;
 import com.portal.usermgr.service.MemberService;
 import com.portal.usermgr.service.UserService;
 import java.util.Date;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang.StringUtils;
 import org.apache.shiro.SecurityUtils;
 import org.apache.shiro.authc.IncorrectCredentialsException;
 import org.apache.shiro.authc.LockedAccountException;
 import org.apache.shiro.authc.UnknownAccountException;
 import org.apache.shiro.authc.UsernamePasswordToken;
 import org.apache.shiro.subject.Subject;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class LoginAct
 {
 
   @Autowired
   private UserService userService;
 
   @Autowired
   private MemberService memberService;
 
   @Autowired
   private AdminService adminService;
 
   @Autowired
   private PwdEncoder pwdEncoder;
 
   @RequestMapping(value={"/login.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String input(HttpServletRequest request, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (user != null) {
       return "redirect:/";
     }
     ViewTools.frontData(request, model, site);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "user", "member.login");
   }
 
   @RequestMapping(value={"/login.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String submit(String username, String password, String nextUrl, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     Subject currentUser = SecurityUtils.getSubject();
     UsernamePasswordToken token = new UsernamePasswordToken(username, 
       this.pwdEncoder.encodePassword(password));
     token.setRememberMe(true);
     try {
       currentUser.login(token);
     } catch (UnknownAccountException localUnknownAccountException) {
     } catch (IncorrectCredentialsException localIncorrectCredentialsException) {
     } catch (LockedAccountException localLockedAccountException) {
     }
     User user = this.userService.findByUsername(username);
     String msg = checkLogin(site, user);
     if (!StringUtils.isBlank(msg)) {
       model.addAttribute("msg", msg);
       ViewTools.frontData(request, model, site);
       return ViewTools.getTplPath(request, site.getSolutionPath(), 
         "user", "member.login");
     }
     if (currentUser.isAuthenticated()) {
       if (!user.getStatus().equals(Byte.valueOf((byte) 0))) {
         return ViewTools.showMessage(nextUrl, request, model, 
           "该账号已经被禁止登录!", Integer.valueOf(0));
       }
       String ip = ServicesUtils.getIpAddr(request);
       if (user.getAdmin() == null)
         this.memberService.updateLoginInfo(user, ip);
       else {
         this.adminService.updateLoginInfo(user, ip);
       }
       if (!StringUtils.isBlank(nextUrl)) {
         return "redirect:" + ViewTools.showNextUrl(nextUrl, site);
       }
       return "redirect:/";
     }
     if (site.getNeedCheck()) {
       user = this.userService.updateFailTime(user);
       int i = site.getLoginCount().intValue() - user.getFailCount().intValue();
       model.addAttribute("msg", "用户名或密码错误,登录失败,您还有" + i + "次机会!");
       ViewTools.frontData(request, model, site);
       return ViewTools.getTplPath(request, site.getSolutionPath(), 
         "user", "member.login");
     }
     model.addAttribute("msg", "用户名或者密码错误");
     if (!StringUtils.isBlank(nextUrl)) {
       model.addAttribute("url", nextUrl);
     }
     ViewTools.frontData(request, model, site);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "user", "member.login");
   }
 
   @RequestMapping({"/logout.jsp"})
   public String logout(String nextUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Subject currentUser = SecurityUtils.getSubject();
     currentUser.logout();
     if (!StringUtils.isBlank(nextUrl)) {
       return "redirect:" + ViewTools.showNextUrl(nextUrl, site);
     }
     ViewTools.frontData(request, model, site);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "user", "member.login");
   }
 
   @RequestMapping({"/jslogin.jsp"})
   public String jslogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
     Site site = ContextTools.getSite(request);
     ViewTools.frontData(request, model, site);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return ViewTools.getTplPath(null, site.getSolutionPath(), 
       "user", "jslogin");
   }
 
   private String checkLogin(Site site, User user) {
     if ((user != null) && 
       (site.getNeedCheck()) && 
       (site.getLoginCount().intValue() <= user.getFailCount().intValue()) && 
       (user.getLastFailTime() != null) && 
       (user.getLastFailTime().after(DateUtils.getToday()))) {
       return "您登录失败次数超过" + site.getLoginCount() + 
         "次，今日禁止登录!";
     }
 
     return null;
   }
 }


 
 
 