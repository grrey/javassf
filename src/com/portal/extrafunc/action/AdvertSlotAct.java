 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.extrafunc.entity.AdvertSlot;
 import com.portal.extrafunc.service.AdvertSlotService;
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
 public class AdvertSlotAct
 {
   private static final Logger log = LoggerFactory.getLogger(AdvertSlotAct.class);
 
   @Autowired
   private AdvertSlotService service;
 
   @Autowired
   private LogService logService;
 
   @RequiresPermissions({"admin:advertSlot:list"})
   @RequestMapping({"/advertSlot/v_list.do"})
   public String list() { return "extraFunc/advert/slot/list"; } 
   @RequiresPermissions({"admin:advertSlot:add"})
   @RequestMapping({"/advertSlot/v_add.do"})
   public String add() {
     return "extraFunc/advert/slot/add";
   }
   @RequiresPermissions({"admin:advertSlot:edit"})
   @RequestMapping({"/advertSlot/v_edit.do"})
   public String edit(Integer id, ModelMap model) { model.addAttribute("slot", this.service.findById(id));
     return "extraFunc/advert/slot/edit"; }
 
   @RequiresPermissions({"admin:advertSlot:save"})
   @RequestMapping({"/advertSlot/o_save.do"})
   public String save(AdvertSlot bean, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, site);
     log.info("save AdvertSlot id={}", bean.getId());
     model.addAttribute("msg", "广告位添加成功!");
     return add();
   }
   @RequiresPermissions({"admin:advertSlot:update"})
   @RequestMapping({"/advertSlot/o_update.do"})
   public String update(AdvertSlot bean, ModelMap model) { bean = this.service.update(bean);
     log.info("update AdvertSlot id={}.", bean.getId());
     model.addAttribute("msg", "广告位修改成功!");
     return edit(bean.getId(), model);
   }
 
   @RequestMapping({"/advertSlot/jsonData.do"})
   public String dataPageByJosn(Integer slotId, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page p = this.service.getPage(site.getId(), sortname, sortorder, 
       page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/advert/slot/data";
   }
   @RequiresPermissions({"admin:advertSlot:delete"})
   @RequestMapping({"/advertSlot/o_ajax_delete.do"})
   public void deleteAdvertSlot(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     AdvertSlot[] beans = this.service.deleteByIds(ids);
     for (AdvertSlot bean : beans) {
       log.info("delete AdvertSlot id={}", bean.getId());
       this.logService.operating(request, "删除广告位", "id=" + bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 