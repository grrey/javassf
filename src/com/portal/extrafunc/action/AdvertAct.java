 package com.portal.extrafunc.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.extrafunc.entity.Advert;
 import com.portal.extrafunc.service.AdvertService;
 import com.portal.extrafunc.service.AdvertSlotService;
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
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 
 @Controller
 public class AdvertAct
 {
   private static final Logger log = LoggerFactory.getLogger(AdvertAct.class);
 
   @Autowired
   private AdvertService service;
 
   @Autowired
   private AdvertSlotService slotService;
 
   @Autowired
   private LogService logService;
 
   @RequiresPermissions({"admin:advert:list"})
   @RequestMapping({"/advert/v_list.do"})
   public String list(Integer slotId, ModelMap model) { model.addAttribute("slotId", slotId);
     return "extraFunc/advert/list"; } 
   @RequiresPermissions({"admin:advert:add"})
   @RequestMapping({"/advert/v_add.do"})
   public String add(HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     List slotList = this.slotService.getList(site.getId());
     model.addAttribute("slotList", slotList);
     return "extraFunc/advert/add";
   }
   @RequiresPermissions({"admin:advert:edit"})
   @RequestMapping({"/advert/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     List slotList = this.slotService.getList(site.getId());
     model.addAttribute("slotList", slotList);
     model.addAttribute("advert", this.service.findById(id));
     return "extraFunc/advert/edit"; }
 
   @RequiresPermissions({"admin:advert:save"})
   @RequestMapping({"/advert/o_save.do"})
   public String save(Advert bean, Integer slotId, boolean advtype, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.save(bean, slotId, advtype, site);
     log.info("save Advert id={}", bean.getId());
     model.addAttribute("msg", "广告添加成功!");
     return add(request, model);
   }
   @RequiresPermissions({"admin:advert:update"})
   @RequestMapping({"/advert/o_update.do"})
   public String update(Advert bean, Integer slotId, boolean advtype, HttpServletRequest request, ModelMap model) {
     bean = this.service.update(bean, slotId, advtype);
     log.info("update Advert id={}.", bean.getId());
     model.addAttribute("msg", "广告修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/advert/jsonData.do"})
   public String dataPageByJosn(Integer slotId, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page p = this.service.getPage(site.getId(), slotId, sortname, 
       sortorder, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "extraFunc/advert/data";
   }
   @RequiresPermissions({"admin:advert:delete"})
   @RequestMapping({"/advert/o_ajax_delete.do"})
   public void deleteAdvert(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Advert[] beans = this.service.deleteByIds(ids);
     for (Advert bean : beans) {
       log.info("delete Advert id={}", bean.getId());
       this.logService.operating(request, "删除广告", "id=" + bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 