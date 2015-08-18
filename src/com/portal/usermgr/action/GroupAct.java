 package com.portal.usermgr.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.Group;
 import com.portal.usermgr.entity.GroupPerm;
 import com.portal.usermgr.service.GroupService;
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
 public class GroupAct
 {
   private static final Logger log = LoggerFactory.getLogger(GroupAct.class);
 
   @Autowired
   private GroupService service;
 
   @RequiresPermissions({"admin:group:list"})
   @RequestMapping({"/group/v_list.do"})
   public String list(HttpServletRequest request, ModelMap model) { return "userMgr/group/list"; } 
   @RequiresPermissions({"admin:group:add"})
   @RequestMapping({"/group/v_add.do"})
   public String add(ModelMap model) {
     return "userMgr/group/add";
   }
   @RequiresPermissions({"admin:group:edit"})
   @RequestMapping({"/group/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("group", this.service.findById(id));
     return "userMgr/group/edit"; }
 
   @RequiresPermissions({"admin:group:save"})
   @RequestMapping({"/group/o_save.do"})
   public String save(Group bean, GroupPerm groupPerm, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.saveGroup(bean, groupPerm, site);
     log.info("save Group id={}", bean.getId());
     model.addAttribute("msg", "会员组添加成功!");
     return add(model);
   }
   @RequiresPermissions({"admin:group:update"})
   @RequestMapping({"/group/o_update.do"})
   public String update(Group bean, GroupPerm groupPerm, HttpServletRequest request, ModelMap model) {
     bean = this.service.updateGroup(bean, groupPerm);
     log.info("update Group id={}.", bean.getId());
     model.addAttribute("msg", "会员组修改成功!");
     return edit(bean.getId(), request, model);
   }
   @RequiresPermissions({"admin:group:priority"})
   @RequestMapping({"/group/o_priority.do"})
   public String priority(Integer[] wids, Integer[] priority, Integer regDefId, HttpServletRequest request, ModelMap model) {
     this.service.updatePriority(wids, priority);
     model.addAttribute("msg", "排序顺序保存成功!");
     return list(request, model);
   }
 
   @RequestMapping({"/group/jsonData.do"})
   public String dataPageByJosn(String sortname, String sortorder, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     List list = this.service.getList(site.getId(), sortname, sortorder);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "userMgr/group/data";
   }
   @RequiresPermissions({"admin:group:delete"})
   @RequestMapping({"/group/o_ajax_delete.do"})
   public void deleteGroup(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Group[] beans = this.service.deleteByIds(ids);
     for (Group bean : beans) {
       log.info("delete Group id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 