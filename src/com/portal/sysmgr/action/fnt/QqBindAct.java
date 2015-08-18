 package com.portal.sysmgr.action.fnt;
 
 import com.javassf.basic.security.encoder.PwdEncoder;
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.entity.Member;
 import com.portal.usermgr.entity.ThirdpartyBind;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.AdminService;
 import com.portal.usermgr.service.MemberService;
 import com.portal.usermgr.service.ThirdpartyBindService;
 import com.portal.usermgr.service.UserService;
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
 public class QqBindAct
 {
 
   @Autowired
   private ThirdpartyBindService bindService;
 
   @Autowired
   private UserService userService;
 
   @Autowired
   private MemberService memberService;
 
   @Autowired
   private AdminService adminService;
 
   @Autowired
   private PwdEncoder pwdEncoder;
 
   @RequestMapping({"/qqback.jsp"})
   public String backurl(HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     ViewTools.frontData(request, model, site);
     return ViewTools.getTplPath(null, site.getSolutionPath(), 
       "user", "qqback");
   }
 
   @RequestMapping({"/qqyz.jsp"})
   public String yz(String openid, String openkey, String nextUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     ThirdpartyBind bind = this.bindService.findByOpenId(openid,"qq");
     ViewTools.frontData(request, model, site);
     if (bind == null) {
       model.addAttribute("openid", openid);
       model.addAttribute("openkey", openkey);
       return ViewTools.getTplPath(null, site.getSolutionPath(), 
         "user", "qqbind");
     }
     User user = this.userService.findByUsername(bind.getUsername());
     if (user == null) {
       this.bindService.deleteById(bind.getId());
       return ViewTools.getTplPath(null, site.getSolutionPath(), 
         "user", "qqbind");
     }
     if (!user.getStatus().equals(Byte.valueOf((byte) 0))) {
       return ViewTools.showMessage(nextUrl, request, model, 
         "该账号已经被禁止登录!", Integer.valueOf(0));
     }
     bind.setOpenkey(openkey);
     Subject currentUser = SecurityUtils.getSubject();
     UsernamePasswordToken token = new UsernamePasswordToken(
       user.getUsername(), user.getPassword());
     token.setRememberMe(true);
     try {
       currentUser.login(token);
     } catch (UnknownAccountException localUnknownAccountException) {
     } catch (IncorrectCredentialsException localIncorrectCredentialsException) {
     } catch (LockedAccountException localLockedAccountException) {
     }
     if (currentUser.isAuthenticated()) {
       String ip = ServicesUtils.getIpAddr(request);
       if (user.getAdmin() == null)
         this.memberService.updateLoginInfo(user, ip);
       else {
         this.adminService.updateLoginInfo(user, ip);
       }
       this.bindService.update(bind);
     }
     if (!StringUtils.isBlank(nextUrl)) {
       return "redirect:" + ViewTools.showNextUrl(nextUrl, site);
     }
     return "redirect:/";
   }
 
   @RequestMapping({"/qqbind.jsp"})
   public String bind(User user, Member member, String openid, String openkey, Integer groupId, String nextUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     ViewTools.frontData(request, model, site);
     if (StringUtils.isBlank(user.getUsername())) {
       return ViewTools.pageNotFound(response);
     }
     if (StringUtils.isBlank(openid)) {
       return ViewTools.pageNotFound(response);
     }
     String ip = ServicesUtils.getIpAddr(request);
     User u = this.userService.findByUsername(user.getUsername());
     if (groupId != null) {
       if (u != null) {
         return ViewTools.showMessage(nextUrl, request, model, 
           "该会员已经存在，注册失败!", Integer.valueOf(0));
       }
       this.memberService.registerMember(user, member, ip, groupId);
     } else {
       if (u == null) {
         return ViewTools.showMessage(nextUrl, request, model, 
           "该会员不存在，绑定失败!", Integer.valueOf(0));
       }
       if (!u.getPassword().equals(
         this.pwdEncoder.encodePassword(user.getPassword()))) {
         return ViewTools.showMessage(nextUrl, request, model, 
           "密码错误，绑定失败!", Integer.valueOf(0));
       }
       this.bindService.save(user.getUsername(), openid, openkey, 
         "qq");
     }
     Subject currentUser = SecurityUtils.getSubject();
     UsernamePasswordToken token = new UsernamePasswordToken(
       user.getUsername(), this.pwdEncoder.encodePassword(user
       .getPassword()));
     token.setRememberMe(true);
     try {
       currentUser.login(token);
     } catch (UnknownAccountException localUnknownAccountException) {
     } catch (IncorrectCredentialsException localIncorrectCredentialsException) {
     } catch (LockedAccountException localLockedAccountException) {
     }
     if (currentUser.isAuthenticated()) {
       User userl = this.userService.findByUsername(user.getUsername());
       if (userl.getAdmin() == null)
         this.memberService.updateLoginInfo(userl, ip);
       else {
         this.adminService.updateLoginInfo(userl, ip);
       }
     }
     if (!StringUtils.isBlank(nextUrl)) {
       return "redirect:" + ViewTools.showNextUrl(nextUrl, site);
     }
     return "redirect:/";
   }
 }


 
 
 