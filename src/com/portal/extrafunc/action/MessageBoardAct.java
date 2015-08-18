 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.extrafunc.entity.MessageBoard;
 import com.portal.extrafunc.entity.MessageBoardExt;
 import com.portal.extrafunc.service.MessageBoardService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
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
 public class MessageBoardAct
 {
   private static final Logger log = LoggerFactory.getLogger(MessageBoardAct.class);
 
   @Autowired
   private MessageBoardService service;
 
   @Autowired
   private LogService logService;
 
   @RequiresPermissions({"admin:board:list"})
   @RequestMapping({"/board/v_list.do"})
   public String list(HttpServletRequest request, ModelMap model) { return "extraFunc/board/list"; } 
   @RequiresPermissions({"admin:board:add"})
   @RequestMapping({"/board/v_add.do"})
   public String add(HttpServletRequest request, ModelMap model) {
     return "extraFunc/board/add";
   }
   @RequiresPermissions({"admin:board:edit"})
   @RequestMapping({"/board/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { model.addAttribute("board", this.service.findById(id));
     return "extraFunc/board/edit"; }
 
   @RequiresPermissions({"admin:board:save"})
   @RequestMapping({"/board/o_save.do"})
   public String save(MessageBoard bean, MessageBoardExt ext, Integer typeId, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, ext, site, typeId);
     log.info("save board id={}", bean.getId());
     model.addAttribute("msg", "留言添加成功!");
     return add(request, model);
   }
 
   @RequiresPermissions({"admin:board:update"})
   @RequestMapping({"/board/o_update.do"})
   public String update(MessageBoard bean, MessageBoardExt ext, Integer typeId, Integer pageNo, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean, ext, typeId);
     log.info("update board id={}.", bean.getId());
     model.addAttribute("msg", "留言回复成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/board/jsonData.do"})
   public String dataPageByJosn(String name, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page p = this.service.getPage(name, site.getId(), null, sortname, 
       sortorder, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/board/data";
   }
   @RequiresPermissions({"admin:board:delete"})
   @RequestMapping({"/board/o_ajax_delete.do"})
   public void deleteboard(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     MessageBoard[] beans = this.service.deleteByIds(ids);
     for (MessageBoard bean : beans) {
       log.info("delete board id={}", bean.getId());
       this.logService.operating(request, "删除留言信息", "id=" + bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:board:show"})
   @RequestMapping({"/board/o_ajax_show.do"})
   public void showboard(Integer id, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     MessageBoard board = this.service.findById(id);
     if (board == null) {
       json.put("success", false);
       json.put("status", 0);
       ResponseUtils.renderJson(response, json.toString());
       return;
     }
     this.service.showBoard(id);
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 