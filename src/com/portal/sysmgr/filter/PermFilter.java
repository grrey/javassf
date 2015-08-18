 package com.portal.sysmgr.filter;
 
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import org.apache.shiro.authc.AuthenticationToken;
 import org.apache.shiro.subject.Subject;
 import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
 /**
  * 基于表单认证的过滤器
  */
 public class PermFilter extends FormAuthenticationFilter
 {
   protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
   {
     boolean isAllowed = super.isAccessAllowed(request, response, 
       mappedValue);
     if ((isAllowed) && (isLoginRequest(request, response))) {
       try {
         issueSuccessRedirect(request, response);
       } catch (Exception localException) {
       }
       return false;
     }
     return isAllowed;
   }
 
   protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
     throws Exception
   {
     return super.onLoginSuccess(token, subject, request, response);
   }
 }


 
 
 