 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.extrafunc.entity.Category;
 import com.portal.extrafunc.entity.Forum;
 import com.portal.extrafunc.entity.ForumExt;
 import com.portal.extrafunc.service.CategoryService;
 import com.portal.extrafunc.service.ForumService;
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
 public class ForumAct
 {
   private static final Logger log = LoggerFactory.getLogger(ForumAct.class);
 
   @Autowired
   private ForumService service;
 
   @Autowired
   private CategoryService categoryService;
 
   @RequiresPermissions({"admin:forum:list"})
   @RequestMapping({"/forum/v_list.do"})
   public String list(Integer categoryId, HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     List categoryList = this.categoryService.getList(site.getId(), 
       null, null);
     model.addAttribute("categoryList", categoryList);
     model.addAttribute("categoryId", categoryId);
     return "extraFunc/forum/list"; }
 
   @RequiresPermissions({"admin:forum:save"})
   @RequestMapping({"/forum/o_save.do"})
   public String save(Forum bean, ForumExt ext, Integer categoryId, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, ext, site, categoryId);
     log.info("save Forum id={}", bean.getId());
     model.addAttribute("msg", "板块添加成功!");
     return list(null, request, model);
   }
   @RequiresPermissions({"admin:forum:update"})
   @RequestMapping({"/forum/o_update.do"})
   public String update(Forum bean, ForumExt ext, Integer categoryId, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean, ext, categoryId);
     log.info("update Forum id={}.", bean.getId());
     model.addAttribute("msg", "板块修改成功!");
     return list(null, request, model);
   }
 
   @RequestMapping({"/forum/jsonData.do"})
   public String dataPageByJosn(Integer categoryId, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page p = this.service.getPage(site.getId(), categoryId, sortname, 
       sortorder, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/forum/data";
   }
   @RequiresPermissions({"admin:forum:delete"})
   @RequestMapping({"/forum/o_ajax_delete.do"})
   public void deleteForum(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Forum[] beans = this.service.deleteByIds(ids);
     for (Forum bean : beans) {
       log.info("delete Forum id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:forum:findbyid"})
   @RequestMapping({"/forum/o_ajax_find.do"})
   public void findForum(Integer id, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Forum bean = this.service.findById(id);
     json.put("name", bean.getName());
     json.put("keywords", bean.getKeywords());
     json.put("description", bean.getDescription());
     json.put("rule", bean.getRule());
     json.put("categoryId", bean.getCategory().getId());
     json.put("tplContent", bean.getTplContent());
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 