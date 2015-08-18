 package com.portal.usermgr.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.Role;
 import com.portal.usermgr.entity.RolePerm;
 import com.portal.usermgr.service.RoleService;
 import java.util.Set;
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
 public class RoleAct
 {
   private static final Logger log = LoggerFactory.getLogger(RoleAct.class);
 
   @Autowired
   private LogService logService;
 
   @Autowired
   private RoleService service;
 
   @RequiresPermissions({"admin:role:list"})
   @RequestMapping({"/role/v_list.do"})
   public String list(HttpServletRequest request, ModelMap model) { return "userMgr/role/list"; } 
   @RequiresPermissions({"admin:role:add"})
   @RequestMapping({"/role/v_add.do"})
   public String add(HttpServletRequest request, ModelMap model) {
     return "userMgr/role/add";
   }
   @RequiresPermissions({"admin:role:edit"})
   @RequestMapping({"/role/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { Role role = this.service.findById(id);
     Set perms = role.getPermsSet();
     model.addAttribute("role", role);
     model.addAttribute("perms", perms);
     return "userMgr/role/edit"; }
 
   @RequiresPermissions({"admin:role:save"})
   @RequestMapping({"/role/o_save.do"})
   public String save(Role bean, RolePerm rolePerm, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.saveRole(bean, rolePerm, site);
     log.info("save Role id={}", bean.getId());
     this.logService.operating(request, "添加角色", "id=" + bean.getId() + ";name=" + 
       bean.getName());
     model.addAttribute("msg", "角色添加成功!");
     return add(request, model);
   }
   @RequiresPermissions({"admin:role:update"})
   @RequestMapping({"/role/o_update.do"})
   public String update(Role bean, RolePerm rolePerm, HttpServletRequest request, ModelMap model) {
     bean = this.service.updateRole(bean, rolePerm);
     log.info("update Role id={}.", bean.getId());
     this.logService.operating(request, "更新角色", "id=" + bean.getId() + ";name=" + 
       bean.getName());
     model.addAttribute("msg", "角色修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/role/jsonData.do"})
   public String dataPageByJosn(String name, Integer departId, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page p = this.service.getPage(name, site.getId(), sortname, 
       sortorder, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "userMgr/role/data";
   }
   @RequiresPermissions({"admin:role:delete"})
   @RequestMapping({"/role/o_ajax_delete.do"})
   public void deleteRole(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Role[] beans = this.service.deleteByIds(ids);
     for (Role bean : beans) {
       log.info("delete PmsRole id={}", bean.getId());
       this.logService.operating(request, "删除角色", "id=" + bean.getId() + 
         ";name=" + bean.getName());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 