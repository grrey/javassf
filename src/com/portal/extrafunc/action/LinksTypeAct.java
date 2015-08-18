 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.extrafunc.entity.LinksType;
 import com.portal.extrafunc.service.LinksTypeService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
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
 @RequestMapping({"/linksType"})
 public class LinksTypeAct
 {
   private static final Logger log = LoggerFactory.getLogger(LinksTypeAct.class);
 
   @Autowired
   private LinksTypeService service;
 
   @RequiresPermissions({"admin:linksType:list"})
   @RequestMapping({"/v_list.do"})
   public String list() { return "extraFunc/links/type/list"; } 
   @RequiresPermissions({"admin:linksType:add"})
   @RequestMapping({"/v_add.do"})
   public String add(ModelMap model) {
     return "extraFunc/links/type/add";
   }
   @RequiresPermissions({"admin:linksType:edit"})
   @RequestMapping({"/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("linksType", this.service.findById(id));
     return "extraFunc/links/type/edit"; }
 
   @RequiresPermissions({"admin:linksType:edit"})
   @RequestMapping({"/o_save.do"})
   public String save(LinksType bean, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, site);
     log.info("save LinksType id={}", bean.getId());
     return list();
   }
   @RequiresPermissions({"admin:linksType:update"})
   @RequestMapping({"/o_update.do"})
   public String update(LinksType bean, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean);
     log.info("update LinksType id={}.", bean.getId());
     return list();
   }
 
   @RequestMapping({"/jsonData.do"})
   public String dataPageByJosn(String sortname, String sortorder, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Integer siteId = ContextTools.getSiteId(request);
     List list = this.service.getList(siteId, sortname, sortorder);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/links/type/data";
   }
   @RequiresPermissions({"admin:linksType:delete"})
   @RequestMapping({"/o_ajax_delete.do"})
   public void deleteLinksType(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     LinksType[] beans = this.service.deleteByIds(ids);
     for (LinksType bean : beans) {
       log.info("delete LinksType id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 