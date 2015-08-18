 package com.portal.doccenter.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.doccenter.entity.ArticleType;
 import com.portal.doccenter.service.ArticleTypeService;
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
 public class ArticleTypeAct
 {
   private static final Logger log = LoggerFactory.getLogger(ArticleTypeAct.class);
 
   @Autowired
   private LogService logService;
 
   @Autowired
   private ArticleTypeService service;
 
   @RequiresPermissions({"admin:doctype:list"})
   @RequestMapping({"/type/v_list.do"})
   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { return "docCenter/config/type/list"; } 
   @RequiresPermissions({"admin:doctype:add"})
   @RequestMapping({"/type/v_add.do"})
   public String add(ModelMap model) {
     return "docCenter/config/type/add";
   }
   @RequiresPermissions({"admin:doctype:edit"})
   @RequestMapping({"/type/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("type", this.service.findById(id));
     return "docCenter/config/type/edit"; }
 
   @RequiresPermissions({"admin:doctype:save"})
   @RequestMapping({"/type/o_save.do"})
   public String save(ArticleType bean, HttpServletRequest request, ModelMap model) {
     bean = this.service.save(bean);
     log.info("save Type id={}", bean.getId());
     this.logService.operating(request, "类型添加", "id=" + bean.getId() + ";name=" + 
       bean.getName());
     model.addAttribute("msg", "类型添加成功");
     return add(model);
   }
   @RequiresPermissions({"admin:doctype:update"})
   @RequestMapping({"/type/o_update.do"})
   public String update(ArticleType bean, Integer pageNo, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean);
     log.info("update Type id={}.", bean.getId());
     this.logService.operating(request, "类型修改", "id=" + bean.getId() + ";name=" + 
       bean.getName());
     model.addAttribute("msg", "类型修改成功");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/type/jsonData.do"})
   public String dataPageByJosn(String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     List list = this.service.getList(true, sortname, sortorder);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/config/type/dataJson";
   }
   @RequiresPermissions({"admin:doctype:delete"})
   @RequestMapping({"/type/o_ajax_delete.do"})
   public void deleteType(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     ArticleType[] beans = this.service.deleteByIds(ids);
     for (ArticleType bean : beans) {
       log.info("delete Type id={}", bean.getId());
       this.logService.operating(request, "删除类型", "id=" + bean.getId() + 
         ";name=" + bean.getName());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 