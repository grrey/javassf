 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.extrafunc.entity.Links;
 import com.portal.extrafunc.service.LinksService;
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
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 @RequestMapping({"/links"})
 public class LinksAct
 {
   private static final Logger log = LoggerFactory.getLogger(LinksAct.class);
 
   @Autowired
   private LinksService service;
 
   @Autowired
   private LinksTypeService typeService;
 
   @RequiresPermissions({"admin:links:list"})
   @RequestMapping({"/v_list.do"})
   public String list() { return "extraFunc/links/list"; } 
   @RequiresPermissions({"admin:links:add"})
   @RequestMapping({"/v_add.do"})
   public String add(HttpServletRequest request, ModelMap model) {
     Integer siteId = ContextTools.getSiteId(request);
     List typeList = this.typeService.getList(siteId, null, null);
     model.addAttribute("typeList", typeList);
     return "extraFunc/links/add";
   }
   @RequiresPermissions({"admin:links:edit"})
   @RequestMapping({"/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { Integer siteId = ContextTools.getSiteId(request);
     List typeList = this.typeService.getList(siteId, null, null);
     model.addAttribute("typeList", typeList);
     model.addAttribute("links", this.service.findById(id));
     return "extraFunc/links/edit"; }
 
   @RequiresPermissions({"admin:links:edit"})
   @RequestMapping({"/o_save.do"})
   public String save(Links bean, Integer typeId, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, typeId, site);
     log.info("save Links id={}", bean.getId());
     model.addAttribute("msg", "友情链接添加成功!");
     return add(request, model);
   }
   @RequiresPermissions({"admin:links:update"})
   @RequestMapping({"/o_update.do"})
   public String update(Links bean, Integer typeId, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean, typeId);
     log.info("update Links id={}.", bean.getId());
     model.addAttribute("msg", "友情链接修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/jsonData.do"})
   public String dataPageByJosn(Integer typeId, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Integer siteId = ContextTools.getSiteId(request);
     Page p = this.service.getPage(siteId, typeId, sortname, sortorder, 
       page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/links/data";
   }
   @RequiresPermissions({"admin:links:delete"})
   @RequestMapping({"/o_ajax_delete.do"})
   public void deleteLinks(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Links[] beans = this.service.deleteByIds(ids);
     for (Links bean : beans) {
       log.info("delete Links id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 