 package com.portal.usermgr.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.service.ChannelService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.Admin;
 import com.portal.usermgr.entity.Depart;
 import com.portal.usermgr.entity.Role;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.AdminService;
 import com.portal.usermgr.service.RoleService;
 import com.portal.usermgr.service.UserService;
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
 public class AdminAct
 {
   private static final Logger log = LoggerFactory.getLogger(AdminAct.class);
 
   @Autowired
   private AdminService service;
 
   @Autowired
   private UserService userService;
 
   @Autowired
   private ChannelService channelService;
 
   @Autowired
   private RoleService roleService;
 
   @Autowired
   private LogService logService;
 
   @RequestMapping({"/admin/v_chnltree.do"})
   public String chnltree(Integer adminId, HttpServletRequest request, HttpServletResponse response, ModelMap model) { Integer siteId = ContextTools.getSiteId(request);
     List chnlList = this.channelService.getChannelBySite(siteId, null, 
       null, null, null, null);
     model.addAttribute("chnlList", chnlList);
     if (adminId != null) {
       Integer[] channelIds = Channel.fetchIds(this.service.findById(adminId)
         .getChannels(siteId));
       model.addAttribute("chnlIds", channelIds);
     }
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "userMgr/depart/tree"; } 
   @RequiresPermissions({"admin:admin:list"})
   @RequestMapping({"/admin/v_list.do"})
   public String list() {
     return "userMgr/admin/list";
   }
   @RequiresPermissions({"admin:admin:add"})
   @RequestMapping({"/admin/v_add.do"})
   public String add(HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     Depart depart = user.getAdmin().getDepart(site.getId());
     List roleList = this.roleService.getListBySite(site.getId());
     model.addAttribute("roleList", roleList);
     model.addAttribute("depart", depart);
     model.addAttribute("user", user);
     return "userMgr/admin/add"; } 
   @RequiresPermissions({"admin:admin:edit"})
   @RequestMapping({"/admin/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     Admin admin = this.service.findById(id);
     Role role = admin.getRole(site.getId());
     Depart depart = admin.getDepart(site.getId());
     List roleList = this.roleService.getListBySite(site.getId());
     model.addAttribute("roleList", roleList);
     model.addAttribute("user", user);
     model.addAttribute("depart", depart);
     model.addAttribute("admin", admin);
     model.addAttribute("site", site);
     model.addAttribute("roleId", role.getId());
     model.addAttribute("manageStatus", admin.getManageStatus(site.getId()));
     return "userMgr/admin/edit";
   }
 
   @RequiresPermissions({"admin:admin:save"})
   @RequestMapping({"/admin/o_save.do"})
   public String save(User user, Admin admin, Integer roleId, Integer departId, Byte manageStatus, Boolean takeDepart, Integer[] channelIds, HttpServletRequest request, ModelMap model) {
     if (this.userService.findByUsername(user.getUsername()) != null) {
       model.addAttribute("msg", "该用户已经存在，添加失败!");
       model.addAttribute("status", Integer.valueOf(0));
       return add(request, model);
     }
     Site site = ContextTools.getSite(request);
     String ip = ServicesUtils.getIpAddr(request);
     admin = this.service.saveAdmin(user, admin, ip, roleId, departId, site, 
       manageStatus, takeDepart, channelIds);
     log.info("save Admin id={}", admin.getId());
     model.addAttribute("msg", "管理员添加成功!");
     model.addAttribute("status", Integer.valueOf(1));
     return add(request, model);
   }
 
   @RequiresPermissions({"admin:admin:update"})
   @RequestMapping({"/admin/o_update.do"})
   public String update(User user, Admin bean, Integer roleId, Integer departId, Byte manageStatus, Boolean takeDepart, Integer[] channelIds, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.updateAdmin(user, bean, roleId, departId, site, 
       manageStatus, takeDepart, channelIds);
     log.info("update Admin id={}.", bean.getId());
     model.addAttribute("msg", "管理员修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/admin/jsonData.do"})
   public String dataPageByJosn(String key, Integer departId, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
 
     if ((!user.getAdmin().getManageStatus(site.getId())
       .equals(Byte.valueOf((byte) 4))) && 
       (user.getAdmin().getDepart(site.getId()) != null)) {
       departId = user.getAdmin().getDepart(site.getId()).getId();
     }
 
     Page p = this.service.getPage(key, site.getId(), departId, null, 
       sortname, sortorder, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     model.addAttribute("siteId", site.getId());
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "userMgr/admin/data";
   }
   @RequiresPermissions({"admin:admin:delete"})
   @RequestMapping({"/admin/o_ajax_delete.do"})
   public void deleteAdmin(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Admin[] beans = this.service.deleteByIds(ids);
     for (Admin bean : beans) {
       log.info("delete Admin id={}", bean.getId());
       this.logService.operating(request, "删除管理员", "id=" + bean.getId() + 
         ";name=" + bean.getUser().getUsername());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
   @RequiresPermissions({"admin:admin:updatePass"})
   @RequestMapping({"/admin/o_updatePass.do"})
   public String updatePass(Integer adminId, String password, ModelMap model) {
     this.service.updatePass(adminId, password);
     log.info("update Admin Password id={}.", adminId);
     model.addAttribute("msg", "密码修改成功!");
     return list();
   }
 
   @RequestMapping({"/admin/o_checkuser.do"})
   public void checkUser(String username, HttpServletRequest request, HttpServletResponse response) {
     User user = this.userService.findByUsername(username);
     if (user != null) {
       ResponseUtils.renderJson(response, "false");
       return;
     }
     ResponseUtils.renderJson(response, "true");
   }
 }


 
 
 