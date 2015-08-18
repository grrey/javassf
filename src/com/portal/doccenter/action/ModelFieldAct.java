 package com.portal.doccenter.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.doccenter.entity.Model;
 import com.portal.doccenter.entity.ModelField;
 import com.portal.doccenter.service.ModelFieldService;
 import com.portal.doccenter.service.ModelService;
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
 public class ModelFieldAct
 {
   private static final Logger log = LoggerFactory.getLogger(ModelFieldAct.class);
 
   @Autowired
   private ModelService modelService;
 
   @Autowired
   private ModelFieldService service;
 
   @RequiresPermissions({"admin:field:list"})
   @RequestMapping({"/field/v_list.do"})
   public String list(Integer modelId, HttpServletRequest request, ModelMap model) { model.addAttribute("modelId", modelId);
     return "docCenter/config/model/field/list"; } 
   @RequiresPermissions({"admin:field:add"})
   @RequestMapping({"/field/v_add.do"})
   public String add(Integer modelId, ModelMap model) {
     Model m = this.modelService.findById(modelId);
     model.addAttribute("model", m);
     model.addAttribute("modelId", modelId);
     return "docCenter/config/model/field/add";
   }
   @RequiresPermissions({"admin:field:edit"})
   @RequestMapping({"/field/v_edit.do"})
   public String edit(Integer id, ModelMap model) { ModelField field = this.service.findById(id);
     model.addAttribute("field", field);
     return "docCenter/config/model/field/edit"; }
 
   @RequiresPermissions({"admin:field:save"})
   @RequestMapping({"/field/o_save.do"})
   public String save(ModelField bean, Integer modelId, HttpServletRequest request, ModelMap model) {
     bean = this.service.save(bean, modelId);
     log.info("update ModelItem id={}.", bean.getId());
     model.addAttribute("modelId", bean.getModel().getId());
     model.addAttribute("msg", "字段添加成功!");
     return add(modelId, model);
   }
   @RequiresPermissions({"admin:field:update"})
   @RequestMapping({"/field/o_update.do"})
   public String update(ModelField bean, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean);
     log.info("update ModelItem id={}.", bean.getId());
     model.addAttribute("modelId", bean.getModel().getId());
     model.addAttribute("msg", "字段修改成功!");
     return edit(bean.getId(), model);
   }
 
   @RequestMapping({"/field/jsonData.do"})
   public String dataPageByJosn(Integer modelId, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     List list = this.service.getList(modelId, true, sortname, 
       sortorder);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/config/model/field/dataJson";
   }
   @RequiresPermissions({"admin:field:delete"})
   @RequestMapping({"/field/o_ajax_delete.do"})
   public void deleteItem(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     ModelField[] beans = this.service.deleteByIds(ids);
     for (ModelField bean : beans) {
       log.info("delete Model id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:field:priority"})
   @RequestMapping({"/field/o_priority.do"})
   public void itemPriority(Integer id, Integer priority, HttpServletRequest request, HttpServletResponse response) throws JSONException
   {
     JSONObject json = new JSONObject();
     this.service.updatePriority(id, priority);
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:field:single"})
   @RequestMapping({"/field/o_single.do"})
   public void itemSingle(Integer id, HttpServletRequest request, HttpServletResponse response) {
     ModelField bean = this.service.findById(id);
     if (bean.getSingle().booleanValue()) {
       bean.setSingle(Boolean.valueOf(false));
       ResponseUtils.renderJson(response, "false");
     } else {
       bean.setSingle(Boolean.valueOf(true));
       ResponseUtils.renderJson(response, "true");
     }
     this.service.update(bean);
   }
 
   @RequiresPermissions({"admin:field:show"})
   @RequestMapping({"/field/o_show.do"})
   public void itemShow(Integer id, HttpServletRequest request, HttpServletResponse response) {
     ModelField bean = this.service.findById(id);
     if (bean.getShow().booleanValue()) {
       bean.setShow(Boolean.valueOf(false));
       ResponseUtils.renderJson(response, "false");
     } else {
       bean.setShow(Boolean.valueOf(true));
       ResponseUtils.renderJson(response, "true");
     }
     this.service.update(bean);
   }
 }


 
 
 