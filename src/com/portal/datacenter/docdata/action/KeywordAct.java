 package com.portal.datacenter.docdata.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.docdata.entity.Keyword;
 import com.portal.datacenter.docdata.service.KeywordService;
 import com.portal.datacenter.operatedata.service.LogService;
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
 public class KeywordAct
 {
   private static final Logger log = LoggerFactory.getLogger(KeywordAct.class);
 
   @Autowired
   private LogService logService;
 
   @Autowired
   private KeywordService service;
 
   @RequiresPermissions({"admin:keyword:list"})
   @RequestMapping({"/keyword/v_list.do"})
   public String list() { return "dataCenter/docData/keyword/list"; } 
   @RequiresPermissions({"admin:keyword:save"})
   @RequestMapping({"/keyword/o_save.do"})
   public String save(Keyword bean, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, site);
     log.info("save Keyword id={}", bean.getId());
     this.logService.operating(request, "添加关键字", "id=" + bean.getId() + ";name=" + 
       bean.getName());
     model.addAttribute("msg", "关键字添加成功!");
     return list();
   }
   @RequiresPermissions({"admin:keyword:update"})
   @RequestMapping({"/keyword/o_update.do"})
   public String update(Keyword bean, HttpServletRequest request, ModelMap model) {
     this.service.update(bean);
     log.info("update Keyword");
     this.logService.operating(request, "修改关键字", null);
     model.addAttribute("msg", "关键字修改成功!");
     return list();
   }
 
   @RequestMapping({"/keyword/jsonData.do"})
   public String dataPageByJosn(String name, Integer departId, String sortname, String sortorder, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     List list = this.service.getListBySiteId(site.getId(), false, 
       false, sortname, sortorder);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "dataCenter/docData/keyword/listdata";
   }
   @RequiresPermissions({"admin:keyword:delete"})
   @RequestMapping({"/keyword/o_ajax_delete.do"})
   public void deleteRole(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Keyword[] beans = this.service.deleteByIds(ids);
     for (Keyword bean : beans) {
       log.info("delete Keyword id={}", bean.getId());
       this.logService.operating(request, "删除关键字", "id=" + bean.getId() + 
         ";name=" + bean.getName());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }
