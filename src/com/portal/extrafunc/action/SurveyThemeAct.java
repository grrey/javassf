 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.extrafunc.entity.SurveyTheme;
 import com.portal.extrafunc.service.SurveyThemeService;
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
 public class SurveyThemeAct
 {
   private static final Logger log = LoggerFactory.getLogger(SurveyThemeAct.class);
 
   @Autowired
   private SurveyThemeService service;
 
   @RequiresPermissions({"admin:surveytheme:list"})
   @RequestMapping({"/surveytheme/v_list.do"})
   public String list(Integer naireId, ModelMap model) { model.addAttribute("naireId", naireId);
     return "extraFunc/questionnaire/surveytheme/list"; } 
   @RequiresPermissions({"admin:surveytheme:add"})
   @RequestMapping({"/surveytheme/v_add.do"})
   public String add(Integer naireId, ModelMap model) {
     model.addAttribute("naireId", naireId);
     return "extraFunc/questionnaire/surveytheme/add";
   }
   @RequiresPermissions({"admin:surveytheme:edit"})
   @RequestMapping({"/surveytheme/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("surveyTheme", this.service.findById(id));
     return "extraFunc/questionnaire/surveytheme/edit"; }
 
   @RequiresPermissions({"admin:surveytheme:save"})
   @RequestMapping({"/surveytheme/o_save.do"})
   public String save(SurveyTheme bean, Integer naireId, Integer showType1, Integer showType2, String[] names, Integer[] votes, Integer[] prioritys, HttpServletRequest request, ModelMap model)
   {
     bean = this.service.save(bean, naireId, showType1, showType2, names, votes, 
       prioritys);
     log.info("save SurveyTheme id={}", bean.getId());
     model.addAttribute("msg", "调查项添加成功!");
     return add(naireId, model);
   }
 
   @RequiresPermissions({"admin:surveytheme:update"})
   @RequestMapping({"/surveytheme/o_update.do"})
   public String update(SurveyTheme bean, Integer showType1, Integer showType2, String[] names, Integer[] votes, Integer[] prioritys, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean, showType1, showType2, names, votes, 
       prioritys);
     log.info("update SurveyTheme id={}.", bean.getId());
     model.addAttribute("msg", "调查项修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/surveytheme/jsonData.do"})
   public String dataPageByJosn(Integer naireId, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Page p = this.service.getPage(naireId, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/questionnaire/surveytheme/data";
   }
   @RequiresPermissions({"admin:surveytheme:delete"})
   @RequestMapping({"/surveytheme/o_ajax_delete.do"})
   public void deleteSurveyTheme(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     SurveyTheme[] beans = this.service.deleteByIds(ids);
     for (SurveyTheme bean : beans) {
       log.info("delete SurveyTheme id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 