 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.extrafunc.entity.MessageType;
 import com.portal.extrafunc.service.MessageTypeService;
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
 public class MessageTypeAct
 {
   private static final Logger log = LoggerFactory.getLogger(MessageTypeAct.class);
 
   @Autowired
   private MessageTypeService service;
 
   @Autowired
   private LogService logService;
 
   @RequiresPermissions({"admin:messageType:list"})
   @RequestMapping({"/messageType/v_list.do"})
   public String list() { return "extraFunc/board/type/list"; } 
   @RequiresPermissions({"admin:messageType:add"})
   @RequestMapping({"/messageType/v_add.do"})
   public String add(ModelMap model) {
     return "extraFunc/board/type/add";
   }
   @RequiresPermissions({"admin:messageType:edit"})
   @RequestMapping({"/messageType/v_edit.do"})
   public String edit(Integer id, ModelMap model) { model.addAttribute("messageType", this.service.findById(id));
     return "extraFunc/board/type/edit"; }
 
   @RequiresPermissions({"admin:messageType:save"})
   @RequestMapping({"/messageType/o_save.do"})
   public String save(MessageType bean, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, site);
     log.info("save MessageType id={}", bean.getId());
     model.addAttribute("msg", "类型添加成功!");
     return list();
   }
   @RequiresPermissions({"admin:messageType:update"})
   @RequestMapping({"/messageType/o_update.do"})
   public String update(MessageType bean, ModelMap model) { bean = this.service.update(bean);
     log.info("update MessageType id={}.", bean.getId());
     model.addAttribute("msg", "类型修改成功!");
     return list();
   }
 
   @RequestMapping({"/messageType/jsonData.do"})
   public String dataPageByJosn(String name, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     List list = this.service.getList(site.getId(), sortname, 
       sortorder);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/board/type/data";
   }
   @RequiresPermissions({"admin:messageType:delete"})
   @RequestMapping({"/messageType/o_ajax_delete.do"})
   public void deleteMailbox(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     MessageType[] beans = this.service.deleteByIds(ids);
     for (MessageType bean : beans) {
       log.info("delete MessageType id={}", bean.getId());
       this.logService.operating(request, "删除留言板类型", "id=" + bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 