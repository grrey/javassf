 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.extrafunc.entity.Comment;
 import com.portal.extrafunc.entity.CommentExt;
 import com.portal.extrafunc.service.CommentService;
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
 public class CommentAct
 {
   private static final Logger log = LoggerFactory.getLogger(CommentAct.class);
 
   @Autowired
   private LogService logService;
 
   @Autowired
   private CommentService service;
 
   @RequiresPermissions({"admin:comment:list"})
   @RequestMapping({"/comment/v_list.do"})
   public String list(Integer docId, ModelMap model) { model.addAttribute("docId", docId);
     return "extraFunc/comment/list"; } 
   @RequiresPermissions({"admin:comment:edit"})
   @RequestMapping({"/comment/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) {
     model.addAttribute("comment", this.service.findById(id));
     return "extraFunc/comment/edit";
   }
   @RequiresPermissions({"admin:comment:update"})
   @RequestMapping({"/comment/o_update.do"})
   public String update(Comment bean, CommentExt ext, Integer pageNo, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean, ext);
     log.info("update Comment id={}.", bean.getId());
     this.logService.operating(request, "修改评论", "id=" + bean.getId());
     model.addAttribute("msg", "评论修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/comment/jsonData.do"})
   public String dataPageByJosn(Integer docId, Integer parentId, Boolean checked, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page p = this.service.getPage(site.getId(), docId, parentId, 
       checked, null, 0, sortname, sortorder, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/comment/data";
   }
   @RequiresPermissions({"admin:comment:delete"})
   @RequestMapping({"/comment/o_ajax_delete.do"})
   public void deleteComment(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Comment[] beans = this.service.deleteByIds(ids);
     for (Comment bean : beans) {
       log.info("delete Comment id={}", bean.getId());
       this.logService.operating(request, "删除评论", "id=" + bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:comment:check"})
   @RequestMapping({"/comment/o_ajax_check.do"})
   public void checkComment(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Comment[] beans = this.service.checkByIds(ids);
     for (Comment bean : beans) {
       log.info("check Comment id={}", bean.getId());
       this.logService.operating(request, "审核评论", "id=" + bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 