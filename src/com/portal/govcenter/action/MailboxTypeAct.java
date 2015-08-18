 package com.portal.govcenter.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.govcenter.entity.MailboxType;
 import com.portal.govcenter.service.MailboxTypeService;
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
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class MailboxTypeAct
 {
   private static final Logger log = LoggerFactory.getLogger(MailboxTypeAct.class);
 
   @Autowired
   private MailboxTypeService service;
 
   @Autowired
   private LogService logService;
 
   @RequiresPermissions({"admin:mailboxType:list"})
   @RequestMapping({"/mailboxType/v_list.do"})
   public String list() { return "govCenter/type/list"; } 
   @RequiresPermissions({"admin:mailboxType:add"})
   @RequestMapping({"/mailboxType/v_add.do"})
   public String add(ModelMap model) {
     return "govCenter/type/add";
   }
   @RequiresPermissions({"admin:mailboxType:edit"})
   @RequestMapping({"/mailboxType/v_edit.do"})
   public String edit(Integer id, ModelMap model) { model.addAttribute("mailboxType", this.service.findById(id));
     return "govCenter/type/edit"; }
 
   @RequiresPermissions({"admin:mailboxType:save"})
   @RequestMapping({"/mailboxType/o_save.do"})
   public String save(MailboxType bean, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, site);
     log.info("save MailboxType id={}", bean.getId());
     model.addAttribute("msg", "类型添加成功!");
     return list();
   }
   @RequiresPermissions({"admin:mailboxType:update"})
   @RequestMapping({"/mailboxType/o_update.do"})
   public String update(MailboxType bean, ModelMap model) { bean = this.service.update(bean);
     log.info("update MailboxType id={}.", bean.getId());
     model.addAttribute("msg", "类型修改成功!");
     return list();
   }
 
   @RequestMapping({"/mailboxType/jsonData.do"})
   public String dataPageByJosn(String name, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     List list = this.service.getList(site.getId());
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "govCenter/type/data";
   }
   @RequiresPermissions({"admin:mailboxType:delete"})
   @RequestMapping({"/mailboxType/o_ajax_delete.do"})
   public void deleteMailbox(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     MailboxType[] beans = this.service.deleteByIds(ids);
     for (MailboxType bean : beans) {
       log.info("delete MailboxType id={}", bean.getId());
       this.logService.operating(request, "删除局长邮箱类型", "id=" + bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 