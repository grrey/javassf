 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.extrafunc.entity.QuestionDetail;
 import com.portal.extrafunc.service.QuestionDetailService;
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
 public class QuestionDetailAct
 {
   private static final Logger log = LoggerFactory.getLogger(QuestionDetailAct.class);
 
   @Autowired
   private QuestionDetailService service;
 
   @RequiresPermissions({"admin:questiondetail:list"})
   @RequestMapping({"/questiondetail/v_list.do"})
   public String list() { return "questiondetail/list"; } 
   @RequiresPermissions({"admin:questiondetail:add"})
   @RequestMapping({"/questiondetail/v_add.do"})
   public String add(ModelMap model) {
     return "questiondetail/add";
   }
   @RequiresPermissions({"admin:questiondetail:edit"})
   @RequestMapping({"/questiondetail/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("questionDetail", this.service.findById(id));
     return "questiondetail/edit"; }
 
   @RequiresPermissions({"admin:questiondetail:update"})
   @RequestMapping({"/questiondetail/o_update.do"})
   public String update(QuestionDetail bean, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean);
     log.info("update QuestionDetail id={}.", bean.getId());
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/questiondetail/jsonData.do"})
   public String dataPageByJosn(Integer questionId, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Page p = this.service.getPage(questionId, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "questiondetail/data";
   }
   @RequiresPermissions({"admin:questiondetail:delete"})
   @RequestMapping({"/questiondetail/o_ajax_delete.do"})
   public void deleteQuestionDetail(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     QuestionDetail[] beans = this.service.deleteByIds(ids);
     for (QuestionDetail bean : beans) {
       log.info("delete QuestionDetail id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 