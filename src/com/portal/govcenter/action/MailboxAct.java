 package com.portal.govcenter.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.govcenter.entity.Mailbox;
 import com.portal.govcenter.entity.MailboxExt;
 import com.portal.govcenter.service.MailboxService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.Admin;
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
 public class MailboxAct
 {
   private static final Logger log = LoggerFactory.getLogger(MailboxAct.class);
 
   @Autowired
   private MailboxService service;
 
   @Autowired
   private LogService logService;
 
   @RequiresPermissions({"admin:mailbox:list"})
   @RequestMapping({"/mailbox/v_list.do"})
   public String list(Integer typeId, HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     if (user.getAdmin().haveAllManage(site.getId()))
       model.addAttribute("all", Boolean.valueOf(true));
     else {
       model.addAttribute("all", Boolean.valueOf(false));
     }
     model.addAttribute("typeId", typeId);
     return "govCenter/mailbox/list"; } 
   @RequiresPermissions({"admin:mailbox:add"})
   @RequestMapping({"/mailbox/v_add.do"})
   public String add(HttpServletRequest request, ModelMap model) {
     return "govCenter/mailbox/add";
   }
   @RequiresPermissions({"admin:mailbox:edit"})
   @RequestMapping({"/mailbox/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("mailbox", this.service.findById(id));
     return "govCenter/mailbox/edit"; }
 
   @RequiresPermissions({"admin:mailbox:save"})
   @RequestMapping({"/mailbox/o_save.do"})
   public String save(Mailbox bean, MailboxExt ext, Integer departId, Integer typeId, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, ext, site, departId, typeId);
     log.info("save Mailbox id={}", bean.getId());
     return add(request, model);
   }
   @RequiresPermissions({"admin:mailbox:update"})
   @RequestMapping({"/mailbox/o_update.do"})
   public String update(Mailbox bean, MailboxExt ext, Integer pageNo, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     bean = this.service.update(bean, ext, user, site.getId());
     log.info("update Mailbox id={}.", bean.getId());
     model.addAttribute("msg", "信件回复成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/mailbox/jsonData.do"})
   public String dataPageByJosn(String name, Integer typeId, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     Page p = this.service.getPage(name, site.getId(), user, typeId, page.intValue(), 
       pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "govCenter/mailbox/data";
   }
   @RequiresPermissions({"admin:mailbox:delete"})
   @RequestMapping({"/mailbox/o_ajax_delete.do"})
   public void deleteMailbox(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Mailbox[] beans = this.service.deleteByIds(ids);
     for (Mailbox bean : beans) {
       log.info("delete Mailbox id={}", bean.getId());
       this.logService.operating(request, "删除局长信箱", "id=" + bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:mailbox:show"})
   @RequestMapping({"/mailbox/o_ajax_show.do"})
   public void showMailbox(Integer id, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Mailbox mailbox = this.service.findById(id);
     if (mailbox == null) {
       json.put("success", false);
       json.put("status", 0);
       ResponseUtils.renderJson(response, json.toString());
       return;
     }
     this.service.showMailbox(id);
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequestMapping({"/mailbox/o_forward.do"})
   public String forward(Integer id, Integer departId, Integer typeId, HttpServletRequest request, ModelMap model)
   {
     this.service.forwardMailbox(id, departId);
     return list(typeId, request, model);
   }
 
   @RequestMapping({"/mailbox/o_back.do"})
   public String back(Integer id, Integer typeId, HttpServletRequest request, ModelMap model) {
     this.service.backMailbox(id);
     return list(typeId, request, model);
   }
 }


 
 
 