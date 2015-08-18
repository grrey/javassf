 package com.portal.sysmgr.filter;
 
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.UserService;
 import java.io.IOException;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import org.apache.shiro.SecurityUtils;
 import org.apache.shiro.subject.Subject;
 import org.springframework.beans.factory.annotation.Autowired;
 
 public class AdminFilter
   implements Filter
 {
   private UserService userService;
 
   public void destroy()
   {
   }
 
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
     throws IOException, ServletException
   {
     Subject currentUser = SecurityUtils.getSubject();
     String username = (String)currentUser.getPrincipal();
     User user = this.userService.findByUsername(username);
     if ((user == null) || (user.getAdmin() == null)) {
       return;
     }
     chain.doFilter(request, response);
   }
 
   public void init(FilterConfig fConfig)
     throws ServletException
   {
   }
 
   @Autowired
   public void setUserService(UserService userService)
   {
     this.userService = userService;
   }
 }


 
 
 