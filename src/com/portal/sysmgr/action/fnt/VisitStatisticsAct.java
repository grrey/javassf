 package com.portal.sysmgr.action.fnt;
 
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.service.VisitStatisticsService;
 import com.portal.sysmgr.utils.ContextTools;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class VisitStatisticsAct
 {
 
   @Autowired
   private VisitStatisticsService statisticsService;
 
   @RequestMapping({"/visitViews.jsp"})
   public void visitViews(HttpServletRequest request, HttpServletResponse response)
   {
     Site site = ContextTools.getSite(request);
     String url = ServicesUtils.getQueryParam(request, "url");
     String ip = ServicesUtils.getIpAddr(request);
     String cookie = ContextTools.getIdentityCookie(request, response);
     this.statisticsService.save(site, url, ip, cookie);
   }
 }


 
 
 