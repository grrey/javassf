 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.extrafunc.entity.SurveyDetail;
 import com.portal.extrafunc.entity.SurveyTheme;
 import com.portal.extrafunc.service.SurveyDetailService;
 import com.portal.extrafunc.service.SurveyThemeService;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.User;
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
 public class SurveyDetailAct
 {
   private static final Logger log = LoggerFactory.getLogger(SurveyDetailAct.class);
 
   @Autowired
   private SurveyDetailService service;
 
   @Autowired
   private SurveyThemeService themeService;
 
   @RequiresPermissions({"admin:surveydetail:list"})
   @RequestMapping({"/surveydetail/v_list.do"})
   public String list() { return "surveydetail/list"; } 
   @RequiresPermissions({"admin:surveydetail:add"})
   @RequestMapping({"/surveydetail/v_add.do"})
   public String add(ModelMap model) {
     return "surveydetail/add";
   }
   @RequiresPermissions({"admin:surveydetail:edit"})
   @RequestMapping({"/surveydetail/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("surveyDetail", this.service.findById(id));
     return "surveydetail/edit"; }
 
   @RequiresPermissions({"admin:surveydetail:edit"})
   @RequestMapping({"/surveydetail/o_save.do"})
   public String save(String content, Integer surveyId, HttpServletRequest request, ModelMap model) {
     User user = ContextTools.getUser(request);
     SurveyTheme st = this.themeService.findById(surveyId);
     this.service.save(content, st, user);
     return add(model);
   }
   @RequiresPermissions({"admin:surveydetail:update"})
   @RequestMapping({"/surveydetail/o_update.do"})
   public String update(SurveyDetail bean, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean);
     log.info("update SurveyDetail id={}.", bean.getId());
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/surveydetail/jsonData.do"})
   public String dataPageByJosn(Integer surveyId, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Page p = this.service.getPage(surveyId, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "surveydetail/data";
   }
   @RequiresPermissions({"admin:surveydetail:delete"})
   @RequestMapping({"/surveydetail/o_ajax_delete.do"})
   public void deleteSurveyDetail(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     SurveyDetail[] beans = this.service.deleteByIds(ids);
     for (SurveyDetail bean : beans) {
       log.info("delete SurveyDetail id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 