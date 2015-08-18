 package com.portal.usermgr.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.service.ChannelService;
 import com.portal.doccenter.service.WorkFlowService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.Depart;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.DepartService;
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
 public class DepartAct
 {
   private static final Logger log = LoggerFactory.getLogger(DepartAct.class);
 
   @Autowired
   private DepartService service;
 
   @Autowired
   private ChannelService channelService;
 
   @Autowired
   private WorkFlowService workFlowService;
 
   @RequestMapping({"/depart/v_chnltree.do"})
   public String chnltree(Integer departId, HttpServletRequest request, HttpServletResponse response, ModelMap model) { Integer siteId = ContextTools.getSiteId(request);
     List chnlList = this.channelService.getChannelBySite(siteId, null, 
       null, null, null, null);
     model.addAttribute("chnlList", chnlList);
     if (departId != null) {
       Integer[] channelIds = Channel.fetchIds(this.service.findById(departId)
         .getChannels());
       model.addAttribute("chnlIds", channelIds);
     }
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "userMgr/depart/tree";
   }
 
   @RequestMapping({"/depart/v_addtree.do"})
   public String addtree(Integer departId, Integer parentId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     model.addAttribute("parentId", parentId);
     Integer siteId = ContextTools.getSiteId(request);
     List list = null;
     if (parentId != null) {
       list = this.service.getListByParent(parentId);
       model.addAttribute("list", list);
     } else {
       list = this.service.getListNoParent(siteId);
       model.addAttribute("list", list);
     }
     if (departId != null) {
       list.remove(this.service.findById(departId));
     }
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "userMgr/depart/addtree";
   }
   @RequiresPermissions({"admin:depart:list"})
   @RequestMapping({"/depart/v_list.do"})
   public String list(HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (!user.getAdmin().haveAllManage(site.getId())) {
       Integer departId = user.getAdmin().getDepart(site.getId()).getId();
       return edit(departId, request, model);
     }
     return "userMgr/depart/list"; } 
   @RequiresPermissions({"admin:depart:add"})
   @RequestMapping({"/depart/v_add.do"})
   public String add(HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (!user.getAdmin().haveAllManage(site.getId())) {
       Integer departId = user.getAdmin().getDepart(site.getId()).getId();
       return edit(departId, request, model);
     }
     List chnlList = this.channelService.getChannelByAdmin(user.getId(), 
       site.getId(), null, null, null, null, Boolean.valueOf(true));
     List flowList = this.workFlowService.findByList(site.getId());
     model.addAttribute("chnlList", chnlList);
     model.addAttribute("flowList", flowList);
     return "userMgr/depart/add";
   }
   @RequiresPermissions({"admin:depart:edit"})
   @RequestMapping({"/depart/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     Depart depart = this.service.findById(id);
     List flowList = this.workFlowService.findByList(site.getId());
     List chnlList = this.channelService.getChannelByAdmin(user.getId(), 
       site.getId(), null, null, null, null, Boolean.valueOf(true));
     if (depart.getParent() != null) {
       model.addAttribute("parentId", depart.getParent().getId());
     }
     model.addAttribute("chnlList", chnlList);
     model.addAttribute("flowList", flowList);
     model.addAttribute("depart", depart);
     return "userMgr/depart/edit"; }
 
   @RequiresPermissions({"admin:depart:save"})
   @RequestMapping({"/depart/o_save.do"})
   public String save(Depart bean, Integer parentId, Integer[] channelIds, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, site, parentId, channelIds);
     log.info("save PmsDepartment id={}", bean.getId());
     model.addAttribute("msg", "部门添加成功!");
     return add(request, model);
   }
   @RequiresPermissions({"admin:depart:update"})
   @RequestMapping({"/depart/o_update.do"})
   public String update(Depart bean, Integer parentId, Integer[] channelIds, Integer pageNo, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean, parentId, channelIds);
     log.info("update PmsDepartment id={}.", bean.getId());
     model.addAttribute("msg", "部门修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/depart/jsonData.do"})
   public String dataPageByJosn(String key, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page p = this.service.getPage(key, site.getId(), sortname, 
       sortorder, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "userMgr/depart/data";
   }
   @RequiresPermissions({"admin:depart:delete"})
   @RequestMapping({"/depart/o_ajax_delete.do"})
   public void deleteDepart(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Depart[] beans = this.service.deleteByIds(ids);
     for (Depart bean : beans) {
       log.info("delete PmsDepartment id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequestMapping({"/depart/v_depart.do"})
   public String treeDepart(Integer id, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Integer siteId = ContextTools.getSiteId(request);
     List list = this.service.getListNoParent(siteId);
     list.remove(this.service.findById(id));
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "userMgr/depart/tree_depart";
   }
 
   @RequiresPermissions({"admin:depart:priority"})
   @RequestMapping({"/depart/o_priority.do"})
   public void itemPriority(Integer id, Integer priority, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     this.service.updatePrio(id, priority);
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 