 package com.portal.sysmgr.action;
 
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.entity.ThirdpartyConfig;
 import com.portal.sysmgr.service.ThirdpartyConfigService;
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
 @RequestMapping({"/partyConfig"})
 public class ThirdpartyConfigAct
 {
   private static final Logger log = LoggerFactory.getLogger(ThirdpartyConfigAct.class);
 
   @Autowired
   private ThirdpartyConfigService service;
 
   @RequiresPermissions({"admin:partyConfig:edit"})
   @RequestMapping({"/v_edit.do"})
   public String edit(HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     model.addAttribute("config", this.service.findById(site.getId()));
     return "sysMgr/partyConfig/edit"; }
 
   @RequiresPermissions({"admin:partyConfig:update"})
   @RequestMapping({"/o_update.do"})
   public String update(ThirdpartyConfig bean, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.update(bean, site);
     model.addAttribute("msg", "保存成功");
     log.info("update ThirdpartyConfig id={}.", bean.getId());
     return edit(request, model);
   }
 }


 
 
 