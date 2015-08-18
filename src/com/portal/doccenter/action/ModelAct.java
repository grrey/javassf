 package com.portal.doccenter.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.doccenter.entity.Model;
 import com.portal.doccenter.entity.ModelField;
 import com.portal.doccenter.service.ModelFieldService;
 import com.portal.doccenter.service.ModelService;
 import com.portal.sysmgr.service.TplService;
 import java.util.ArrayList;
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
 public class ModelAct
 {
   private static final Logger log = LoggerFactory.getLogger(ModelAct.class);
 
   @Autowired
   private LogService logService;
 
   @Autowired
   private ModelService service;
 
   @Autowired
   private TplService tplService;
 
   @Autowired
   private ModelFieldService fieldService;
 
   @RequiresPermissions({"admin:model:list"})
   @RequestMapping({"/model/v_list.do"})
   public String list(HttpServletRequest request, ModelMap model) { return "docCenter/config/model/list"; } 
   @RequiresPermissions({"admin:model:add"})
   @RequestMapping({"/model/v_add.do"})
   public String add(ModelMap model) {
     List iconList = this.tplService.getFileChild(
       "/skin/sys", "/img/icon");
     model.addAttribute("iconList", iconList);
     return "docCenter/config/model/add";
   }
   @RequiresPermissions({"admin:model:edit"})
   @RequestMapping({"/model/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { List iconList = this.tplService.getFileChild(
       "/skin/sys", "/img/icon");
     model.addAttribute("iconList", iconList);
     model.addAttribute("model", this.service.findById(id));
     return "docCenter/config/model/edit"; } 
   @RequiresPermissions({"admin:model:save"})
   @RequestMapping({"/model/o_save.do"})
   public String save(Model bean, HttpServletRequest request, ModelMap model) {
     bean = this.service.save(bean);
     List fieldList = getFields(bean);
     this.fieldService.saveList(fieldList);
     log.info("save Model id={}", bean.getId());
     this.logService.operating(request, "添加模型", "id=" + bean.getId() + ";name=" + 
       bean.getName());
     model.addAttribute("msg", "模型添加成功!");
     return add(model);
   }
   @RequiresPermissions({"admin:model:update"})
   @RequestMapping({"/model/o_update.do"})
   public String update(Model bean, HttpServletRequest request, ModelMap model) { bean = this.service.update(bean);
     log.info("update Model id={}.", bean.getId());
     this.logService.operating(request, "修改模型", "id=" + bean.getId() + ";name=" + 
       bean.getName());
     model.addAttribute("msg", "模型修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/model/jsonData.do"})
   public String dataPageByJosn(String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     List list = this.service.getList(true, sortname, sortorder);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/config/model/dataJson";
   }
   @RequiresPermissions({"admin:model:delete"})
   @RequestMapping({"/model/o_ajax_delete.do"})
   public void deleteModel(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Model[] beans = this.service.deleteByIds(ids);
     for (Model bean : beans) {
       log.info("delete Model id={}", bean.getId());
       this.logService.operating(request, "删除模型", "id=" + bean.getId() + 
         ";name=" + bean.getName());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:model:priority"})
   @RequestMapping({"/model/o_priority.do"})
   public String priority(Integer[] wids, Integer[] priority, Boolean[] disabled, Integer defId, HttpServletRequest request, ModelMap model)
   {
     this.service.updatePriority(wids, priority, disabled, defId);
     model.addAttribute("msg", "序列修改成功!");
     return list(request, model);
   }
 
   private List<ModelField> getFields(Model model) {
     List list = new ArrayList();
 
     int i = 0; for (int len = Model.DEF_NAMES.length; i < len; i++) {
       if (!StringUtils.isBlank(Model.DEF_NAMES[i])) {
         ModelField field = new ModelField();
         field.setEconomy(Boolean.valueOf(true));
         field.setModel(model);
         field.setName(Model.DEF_NAMES[i]);
         field.setLabel(Model.DEF_LABELS[i]);
         field.setDataType(Model.DEF_DATA_TYPES[i]);
         field.setSingle(Boolean.valueOf(true));
         field.setShow(Boolean.valueOf(true));
         field.setRequired(Model.DEF_REQUIREDS[i]);
         field.setPriority(Integer.valueOf(i + 1));
         list.add(field);
       }
     }
     return list;
   }
 }


 
 
 