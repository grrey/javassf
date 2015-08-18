 package com.portal.doccenter.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.doccenter.entity.WorkFlow;
 import com.portal.doccenter.service.WorkFlowService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.service.RoleService;
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
 public class WorkFlowAct
 {
   private static final Logger log = LoggerFactory.getLogger(WorkFlowAct.class);
 
   @Autowired
   private WorkFlowService service;
 
   @Autowired
   private RoleService roleService;
 
   @RequiresPermissions({"admin:workflow:list"})
   @RequestMapping({"/workflow/v_list.do"})
   public String list(Integer pageNo, HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     List list = this.roleService.getListBySite(site.getId());
     model.addAttribute("list", list);
     return "docCenter/config/workflow/list"; }
 
   @RequiresPermissions({"admin:workflow:save"})
   @RequestMapping({"/workflow/o_save.do"})
   public String save(WorkFlow bean, Integer[] step, Integer[] roleIds, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, site, step, roleIds);
     log.info("save WorkFlow id={}", bean.getId());
     return "redirect:v_list.do";
   }
   @RequiresPermissions({"admin:workflow:update"})
   @RequestMapping({"/workflow/o_update.do"})
   public String update(WorkFlow bean, Integer[] step, Integer[] roleIds, Integer pageNo, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean, step, roleIds);
     log.info("update WorkFlow id={}.", bean.getId());
     return list(pageNo, request, model);
   }
 
   @RequestMapping({"/workflow/jsonData.do"})
   public String dataPageByJosn(String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page p = this.service.getPage(site.getId(), sortname, 
       sortorder, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/config/workflow/dataJson";
   }
   @RequiresPermissions({"admin:workflow:delete"})
   @RequestMapping({"/workflow/o_ajax_delete.do"})
   public void deleteWorkflow(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     WorkFlow[] beans = this.service.deleteByIds(ids);
     for (WorkFlow bean : beans) {
       log.info("delete WorkFlow id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequestMapping({"/workflow/o_ajax_find.do"})
   public String workflowFind(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     WorkFlow wf = this.service.findById(id);
     List list = this.roleService.getListBySite(site.getId());
     model.addAttribute("list", list);
     model.addAttribute("wf", wf);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/config/workflow/stepData";
   }
 }


 
 
 