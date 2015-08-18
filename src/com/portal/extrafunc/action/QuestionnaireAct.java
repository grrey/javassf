 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.extrafunc.entity.Questionnaire;
 import com.portal.extrafunc.entity.SurveyTheme;
 import com.portal.extrafunc.service.QuestionnaireService;
 import com.portal.extrafunc.service.SurveyThemeService;
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
 public class QuestionnaireAct
 {
   private static final Logger log = LoggerFactory.getLogger(QuestionnaireAct.class);
 
   @Autowired
   private QuestionnaireService service;
 
   @Autowired
   private SurveyThemeService themeService;
 
   @RequiresPermissions({"admin:questionnaire:list"})
   @RequestMapping({"/questionnaire/v_list.do"})
   public String list() { return "extraFunc/questionnaire/list"; } 
   @RequiresPermissions({"admin:questionnaire:add"})
   @RequestMapping({"/questionnaire/v_add.do"})
   public String add(ModelMap model) {
     return "extraFunc/questionnaire/add";
   }
   @RequiresPermissions({"admin:questionnaire:edit"})
   @RequestMapping({"/questionnaire/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("questionnaire", this.service.findById(id));
     return "extraFunc/questionnaire/edit"; }
 
   @RequiresPermissions({"admin:questionnaire:save"})
   @RequestMapping({"/questionnaire/o_save.do"})
   public String save(Questionnaire bean, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, site);
     log.info("save Questionnaire id={}", bean.getId());
     model.addAttribute("msg", "调查问卷添加成功!");
     return add(model);
   }
   @RequiresPermissions({"admin:questionnaire:update"})
   @RequestMapping({"/questionnaire/o_update.do"})
   public String update(Questionnaire bean, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean);
     log.info("update Questionnaire id={}.", bean.getId());
     model.addAttribute("msg", "调查问卷修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/questionnaire/jsonData.do"})
   public String dataPageByJosn(Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page p = this.service.getPage(site.getId(), false, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/questionnaire/data";
   }
   @RequiresPermissions({"admin:questionnaire:delete"})
   @RequestMapping({"/questionnaire/o_ajax_delete.do"})
   public void deleteQuestionnaire(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Questionnaire[] beans = this.service.deleteByIds(ids);
     for (Questionnaire bean : beans) {
       log.info("delete Questionnaire id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
   @RequiresPermissions({"admin:questionnaire:result"})
   @RequestMapping({"/questionnaire/v_result.do"})
   public String result(Integer id, HttpServletRequest request, ModelMap model) {
     List themeList = this.themeService.getList(id);
     model.addAttribute("question", this.service.findById(id));
     model.addAttribute("themeList", themeList);
     model.addAttribute("NORMAL", SurveyTheme.NORMAL);
     return "extraFunc/questionnaire/result";
   }
 }


 
 
 