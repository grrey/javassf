 package com.portal.usermgr.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.Member;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.GroupService;
 import com.portal.usermgr.service.MemberService;
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
 public class MemberAct
 {
   private static final Logger log = LoggerFactory.getLogger(MemberAct.class);
 
   @Autowired
   private MemberService service;
 
   @Autowired
   private UserService userService;
 
   @Autowired
   private GroupService groupService;
 
   @Autowired
   private LogService logService;
 
   @RequiresPermissions({"admin:member:list"})
   @RequestMapping({"/member/v_list.do"})
   public String list(HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     List groupList = this.groupService.getList(site.getId(), null, null);
     model.addAttribute("groupList", groupList);
     return "userMgr/member/list"; } 
   @RequiresPermissions({"admin:member:add"})
   @RequestMapping({"/member/v_add.do"})
   public String add(HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     List groupList = this.groupService.getList(site.getId(), null, null);
     model.addAttribute("groupList", groupList);
     return "userMgr/member/add";
   }
   @RequiresPermissions({"admin:member:edit"})
   @RequestMapping({"/member/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     List groupList = this.groupService.getList(site.getId(), null, null);
     model.addAttribute("member", this.service.findById(id));
     model.addAttribute("groupList", groupList);
     model.addAttribute("siteId", site.getId());
     return "userMgr/member/edit"; }
 
   @RequiresPermissions({"admin:member:save"})
   @RequestMapping({"/member/o_save.do"})
   public String save(User user, Member bean, Integer groupId, HttpServletRequest request, ModelMap model) {
     if (this.userService.findByUsername(user.getUsername()) != null) {
       model.addAttribute("msg", "该用户已经存在，添加失败!");
       model.addAttribute("status", Integer.valueOf(0));
       return add(request, model);
     }
     String ip = ServicesUtils.getIpAddr(request);
     bean = this.service.registerMember(user, bean, ip, groupId);
     log.info("save Member id={}", bean.getId());
     model.addAttribute("msg", "会员添加成功!");
     model.addAttribute("status", Integer.valueOf(1));
     return add(request, model);
   }
   @RequiresPermissions({"admin:member:update"})
   @RequestMapping({"/member/o_update.do"})
   public String update(User user, Member bean, Integer groupId, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     bean = this.service.updateMember(user, bean, groupId, site.getId());
     log.info("update Member id={}.", bean.getId());
     model.addAttribute("msg", "会员修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/member/jsonData.do"})
   public String dataPageByJosn(String key, Integer groupId, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Page p = this.service.getPage(key, site.getId(), groupId, 
       sortname, sortorder, page.intValue(), pagesize.intValue());
     model.addAttribute("p", p);
     model.addAttribute("siteId", site.getId());
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "userMgr/member/data";
   }
   @RequiresPermissions({"admin:member:delete"})
   @RequestMapping({"/member/o_ajax_delete.do"})
   public void deleteRole(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Member[] beans = this.service.deleteByIds(ids);
     for (Member bean : beans) {
       log.info("delete Member id={}", bean.getId());
       this.logService.operating(request, "删除会员", "id=" + bean.getId() + 
         ";name=" + bean.getUser().getUsername());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:member:updatePass"})
   @RequestMapping({"/member/o_updatePass.do"})
   public String updatePass(Integer memberId, String password, HttpServletRequest request, ModelMap model) {
     this.service.updatePass(memberId, password);
     log.info("update Member Password id={}.", memberId);
     model.addAttribute("msg", "密码修改成功!");
     return list(request, model);
   }
 
   @RequestMapping({"/member/o_checkuser.do"})
   public void checkUser(String username, HttpServletRequest request, HttpServletResponse response) {
     User user = this.userService.findByUsername(username);
     if (user != null) {
       ResponseUtils.renderJson(response, "false");
       return;
     }
     ResponseUtils.renderJson(response, "true");
   }
 }


 
 
 