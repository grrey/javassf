 package com.portal.sysmgr.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.service.SiteService;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.User;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang.StringUtils;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
 import org.json.JSONException;
 import org.json.JSONObject;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class SiteAct
 {
   private static final Logger log = LoggerFactory.getLogger(SiteAct.class);
 
   @Autowired
   private SiteService service;
 
   @RequiresPermissions({"admin:sites:list"})
   @RequestMapping({"/sites/v_list.do"})
   public String list(HttpServletRequest request) { return "sysMgr/siteConf/sites/list"; } 
   @RequiresPermissions({"admin:sites:add"})
   @RequestMapping({"/sites/v_add.do"})
   public String add(ModelMap model) {
     return "sysMgr/siteConf/sites/add";
   }
   @RequiresPermissions({"admin:sites:edit"})
   @RequestMapping({"/sites/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("site", this.service.findById(id));
     return "sysMgr/siteConf/sites/edit"; }
 
   @RequiresPermissions({"admin:sites:save"})
   @RequestMapping({"/sites/o_save.do"})
   public String save(Site bean, Integer[] channelIds, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     bean = this.service.save(bean, user, site.getId(), channelIds);
     log.info("save Site id={}", bean.getId());
     return "redirect:v_list.do";
   }
   @RequiresPermissions({"admin:sites:update"})
   @RequestMapping({"/sites/o_update.do"})
   public String update(Site bean, HttpServletRequest request, ModelMap model) { bean = this.service.update(bean);
     model.addAttribute("msg", "站点修改成功");
     log.info("update Site success. id={}", bean.getId());
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/sites/jsonData.do"})
   public String dataPageByJosn(String name, Integer typeId, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     List list = this.service.getList();
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "sysMgr/siteConf/sites/data";
   }
   @RequiresPermissions({"admin:sites:delete"})
   @RequestMapping({"/sites/o_ajax_delete.do"})
   public void delete(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Site[] beans = this.service.deleteByIds(ids);
     for (Site bean : beans) {
       log.info("delete Site id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
   @RequiresPermissions({"admin:sites:sys_edit"})
   @RequestMapping({"/site/v_sys_edit.do"})
   public String sysEdit(HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     model.addAttribute("site", site);
     return "sysMgr/siteConf/sites/sys_edit";
   }
   @RequiresPermissions({"admin:sites:sys_update"})
   @RequestMapping({"/site/o_sys_update.do"})
   public String sysUpdate(Site bean, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean);
     model.addAttribute("msg", "站点修改成功");
     log.info("update Site success. id={}", bean.getId());
     return sysEdit(request, model);
   }
 
   @RequestMapping({"/sites/v_checkDomain.do"})
   public void checkDomainJson(String domain, HttpServletResponse response)
   {
     String pass;
     //String pass;
     if (StringUtils.isBlank(domain))
       pass = "false";
     else {
       pass = this.service.findByDomain(domain, false) == null ? "true" : 
         "false";
     }
     ResponseUtils.renderJson(response, pass);
   }
 }


 
 
 