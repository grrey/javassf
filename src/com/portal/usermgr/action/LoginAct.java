 package com.portal.usermgr.action;
 
 import com.javassf.basic.security.encoder.PwdEncoder;
 import com.javassf.basic.utils.DateUtils;
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.AdminService;
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
 import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
 @Controller
 public class LoginAct
 {
   public static final String COOKIE_ERROR_REMAINING = "_error_remaining";
 
   @Autowired
   private PwdEncoder pwdEncoder;
 
   @Autowired
   private UserService userService;
 
   @Autowired
   private AdminService adminService;
 
   @RequestMapping(value={"/login.do"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String input(HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     return "login";
   }
 
   @RequestMapping(value={"/login.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String submit(String username, String password, String captcha, String nextUrl, String message, HttpServletRequest request, HttpServletResponse response, ModelMap model, RedirectAttributes ra)
   {
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
     Site site = ContextTools.getSite(request);
     User user = this.userService.findByUsername(username);
     String msg = checkLogin(site, user);
     if (!StringUtils.isBlank(msg)) {
       ra.addFlashAttribute("msg", msg);
       return "redirect:login.do";
     }
     if ((currentUser.isAuthenticated()) && (user.getAdmin() != null)) {
       if (user.getAdmin().getRole(site.getId()) == null) {
         currentUser.logout();
         ra.addFlashAttribute("msg", "该用户没有权限，禁止登陆!");
         return "redirect:login.do";
       }
       if (user.getAdmin().getDepart(site.getId()) == null) {
         currentUser.logout();
         ra.addFlashAttribute("msg", "该用户没有分配部门，禁止登陆,请联系管理员!");
         return "redirect:login.do";
       }
       if (!user.getStatus().equals(Byte.valueOf((byte) 0))) {
         return ViewTools.showMessage(nextUrl, request, model, 
           "该账号已经被禁止登录!", Integer.valueOf(0));
       }
       String ip = ServicesUtils.getIpAddr(request);
       this.adminService.updateLoginInfo(user, ip);
       if (!StringUtils.isBlank(nextUrl)) {
         return "redirect:" + nextUrl;
       }
       return "redirect:index.do";
     }
     if ((site.getNeedCheck()) && 
       (!currentUser.isAuthenticated())) {
       user = this.userService.updateFailTime(user);
       int i = site.getLoginCount().intValue() - user.getFailCount().intValue();
       ra.addFlashAttribute("msg", "用户名或密码错误,登录失败,您还有" + i + 
         "次机会!");
       return "redirect:login.do";
     }
 
     ra.addFlashAttribute("msg", "用户名或密码错误!");
     return "redirect:login.do";
   }
 
   @RequestMapping({"/logout.do"})
   public String logout(HttpServletRequest request, HttpServletResponse response)
   {
     Subject currentUser = SecurityUtils.getSubject();
     currentUser.logout();
     return "login";
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


 
 
 