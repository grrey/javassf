 package com.portal.datacenter.docdata.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.docdata.entity.Sensitivity;
 import com.portal.datacenter.docdata.service.SensitivityService;
 import com.portal.datacenter.operatedata.service.LogService;
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
 public class SensitivityAct
 {
   private static final Logger log = LoggerFactory.getLogger(SensitivityAct.class);
 
   @Autowired
   private LogService logService;
 
   @Autowired
   private SensitivityService service;
 
   @RequiresPermissions({"admin:sensitivity:list"})
   @RequestMapping({"/sensitivity/v_list.do"})
   public String list() { return "dataCenter/docData/sensitivity/list"; } 
   @RequiresPermissions({"admin:sensitivity:save"})
   @RequestMapping({"/sensitivity/o_save.do"})
   public String save(Sensitivity bean, ModelMap model) {
     bean = this.service.save(bean);
     model.addAttribute("msg", "敏感词添加成功!");
     return list();
   }
   @RequiresPermissions({"admin:sensitivity:update"})
   @RequestMapping({"/sensitivity/o_update.do"})
   public String update(Sensitivity bean, ModelMap model) { this.service.update(bean);
     model.addAttribute("msg", "敏感词修改成功!");
     return list();
   }
 
   @RequestMapping({"/sensitivity/jsonData.do"})
   public String dataPageByJosn(String sortname, String sortorder, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     List list = this.service.getList(false, sortname, sortorder);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "dataCenter/docData/sensitivity/listdata";
   }
   @RequiresPermissions({"admin:sensitivity:delete"})
   @RequestMapping({"/sensitivity/o_ajax_delete.do"})
   public void deleteAdvertiseSpace(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Sensitivity[] beans = this.service.deleteByIds(ids);
     for (Sensitivity bean : beans) {
       log.info("delete Sensitivity id={}", bean.getId());
       this.logService.operating(request, "删除敏感词", "id=" + bean.getId() + 
         ";name=" + bean.getSearch());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }

