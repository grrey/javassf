 package com.portal.usermgr.action.fnt;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.sysmgr.utils.ViewTools;
 import com.portal.usermgr.entity.Member;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.MemberService;
 import com.portal.usermgr.service.UserService;
 import java.io.IOException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang.StringUtils;
 import org.apache.shiro.SecurityUtils;
 import org.apache.shiro.authc.IncorrectCredentialsException;
 import org.apache.shiro.authc.LockedAccountException;
 import org.apache.shiro.authc.UnknownAccountException;
 import org.apache.shiro.authc.UsernamePasswordToken;
 import org.apache.shiro.subject.Subject;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class RegisterAct
 {
   private static final Logger log = LoggerFactory.getLogger(RegisterAct.class);
   public static final String REGISTER = "member.register";
 
   @Autowired
   private MemberService memberService;
 
   @Autowired
   private UserService userService;
 
   @RequestMapping(value={"/reg.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String input(Integer groupId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     if (!site.getRegOpen().booleanValue()) {
       return ViewTools.pageNotFound(response);
     }
     ViewTools.frontData(request, model, site);
     return ViewTools.getTplPath(request, site.getSolutionPath(), 
       "user", "member.register");
   }
 
   @RequestMapping(value={"/reg.jsp"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String submit(User user, Member member, Integer groupId, String nextUrl, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException
   {
     Site site = ContextTools.getSite(request);
     if (!site.getRegOpen().booleanValue()) {
       return ViewTools.pageNotFound(response);
     }
     String msg = vldReg(user, site);
     if (!StringUtils.isBlank(msg)) {
       return ViewTools.showMessage(nextUrl, request, model, msg, Integer.valueOf(0));
     }
     User u = this.userService.findByUsername(user.getUsername());
     if (u != null) {
       return ViewTools.showMessage(nextUrl, request, model, 
         "该会员已经存在，注册失败!", Integer.valueOf(0));
     }
     String ip = ServicesUtils.getIpAddr(request);
     if (site.getRegCheck().booleanValue()) {
       member.setStatus(Byte.valueOf((byte) -2));
     }
     this.memberService.registerMember(user, member, ip, groupId);
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
       User userl = this.userService.findByUsername(user.getUsername());
       if (userl.getAdmin() == null) {
         this.memberService.updateLoginInfo(userl, ip);
       }
     }
     log.info("member register success. username={}", user.getUsername());
     return ViewTools.showMessage(nextUrl, request, model, "注册会员成功!", Integer.valueOf(1));
   }
 
   @RequestMapping({"/checkuser.jsp"})
   public void checkUser(String username, HttpServletRequest request, HttpServletResponse response) {
     User user = this.userService.findByUsername(username);
     if (user != null) {
       ResponseUtils.renderJson(response, "false");
       return;
     }
     ResponseUtils.renderJson(response, "true");
   }
 
   private String vldReg(User user, Site site) {
     if (!StringUtils.isBlank(user.getUsername())) {
       if ((site.getRegMin() != null) && 
         (user.getUsername().length() < site.getRegMin().intValue())) {
         return "用户名长度不能小于" + site.getRegMin() + ",注册失败!";
       }
       if ((site.getRegMax() != null) && 
         (user.getUsername().length() > site.getRegMax().intValue()))
         return "用户名长度不能大于" + site.getRegMax() + ",注册失败!";
     }
     else {
       return "用户名不能为空，注册失败!";
     }
     return null;
   }
 }


 
 
 