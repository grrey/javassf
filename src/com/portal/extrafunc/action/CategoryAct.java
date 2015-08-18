 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.extrafunc.entity.Category;
 import com.portal.extrafunc.service.CategoryService;
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
 public class CategoryAct
 {
   private static final Logger log = LoggerFactory.getLogger(CategoryAct.class);
 
   @Autowired
   private CategoryService service;
 
   @Autowired
   private LogService logService;
 
   @RequiresPermissions({"admin:category:list"})
   @RequestMapping({"/category/v_list.do"})
   public String list() { return "extraFunc/category/list"; } 
   @RequiresPermissions({"admin:category:add"})
   @RequestMapping({"/category/v_add.do"})
   public String add() {
     return "extraFunc/category/add";
   }
   @RequiresPermissions({"admin:category:edit"})
   @RequestMapping({"/category/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("category", this.service.findById(id));
     return "extraFunc/category/edit"; } 
   @RequiresPermissions({"admin:category:save"})
   @RequestMapping({"/category/o_save.do"})
   public String save(Category bean, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, site);
     log.info("save Category id={}", bean.getId());
     this.logService.operating(request, "添加分类", "id=" + bean.getId());
     model.addAttribute("msg", "分类添加成功!");
     return list();
   }
   @RequiresPermissions({"admin:category:update"})
   @RequestMapping({"/category/o_update.do"})
   public String update(Category bean, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean);
     log.info("update Category id={}.", bean.getId());
     this.logService.operating(request, "修改分类", "id=" + bean.getId());
     model.addAttribute("msg", "分类修改成功!");
     return list();
   }
 
   @RequestMapping({"/category/jsonData.do"})
   public String dataPageByJosn(String sortname, String sortorder, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     List list = this.service
       .getList(site.getId(), sortname, sortorder);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/category/data";
   }
   @RequiresPermissions({"admin:category:delete"})
   @RequestMapping({"/category/o_ajax_delete.do"})
   public void deleteCategory(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Category[] beans = this.service.deleteByIds(ids);
     for (Category bean : beans) {
       log.info("delete Category id={}", bean.getId());
       this.logService.operating(request, "删除分类", "id=" + bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 