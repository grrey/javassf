 package com.portal.sysmgr.action;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.entity.SiteConfig;
 import com.portal.sysmgr.service.SiteConfigService;
 import com.portal.sysmgr.utils.ContextTools;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 @RequestMapping({"/config"})
 public class SiteConfigAct
 {
   private static final Logger log = LoggerFactory.getLogger(SiteConfigAct.class);
 
   @Autowired
   private SiteConfigService service;
 
   @RequiresPermissions({"admin:config:edit"})
   @RequestMapping({"/v_edit.do"})
   public String edit(HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     model.addAttribute("config", this.service.findById(site.getId()));
     return "sysMgr/siteConf/config/edit"; }
 
   @RequiresPermissions({"admin:config:update"})
   @RequestMapping({"/o_update.do"})
   public String update(SiteConfig bean, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.update(bean, site);
     model.addAttribute("msg", "保存成功");
     log.info("update SiteConfig id={}.", bean.getId());
     return edit(request, model);
   }
 }


 
 
 