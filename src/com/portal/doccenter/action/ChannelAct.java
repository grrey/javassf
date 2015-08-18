 package com.portal.doccenter.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.entity.ChannelExt;
 import com.portal.doccenter.entity.ChannelTxt;
 import com.portal.doccenter.entity.ChnlTplSelection;
 import com.portal.doccenter.service.ArticleService;
 import com.portal.doccenter.service.ChannelService;
 import com.portal.doccenter.service.ModelService;
 import com.portal.doccenter.service.WorkFlowService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.service.TplService;
 import com.portal.sysmgr.staticpage.StaticPageService;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.Group;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.GroupService;
 import freemarker.template.TemplateException;
 import java.io.IOException;
 import java.util.Collection;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.lang.StringUtils;
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
 public class ChannelAct
 {
   private static final Logger log = LoggerFactory.getLogger(ChannelAct.class);
 
   @Autowired
   private ModelService modelService;
 
   @Autowired
   private StaticPageService staticPageService;
 
   @Autowired
   private GroupService groupService;
 
   @Autowired
   private WorkFlowService workFlowService;
 
   @Autowired
   private LogService logService;
 
   @Autowired
   private TplService tplService;
 
   @Autowired
   private ChannelService service;
 
   @Autowired
   private ArticleService articleService;
 
   @RequestMapping(value={"/channel/v_tpl_dirtree.do"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String dirtree(String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) { Site site = ContextTools.getSite(request);
     String root = site.getSolutionPath();
     log.debug("tree path={}", root);
     if (StringUtils.isBlank(path)) {
       model.addAttribute("isRoot", Boolean.valueOf(true));
       path = "";
     } else {
       model.addAttribute("isRoot", Boolean.valueOf(false));
     }
     List tplList = this.tplService.getDirChild(root, path);
     model.addAttribute("path", path);
     model.addAttribute("tplList", tplList);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/channel/dirtree"; }
 
   @RequestMapping(value={"/channel/v_tpl_filetree.do"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
   public String tpltree(String path, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     String root = site.getSolutionPath();
     if (StringUtils.isBlank(path)) {
       path = "";
     }
     List tplList = this.tplService.getFileChild(root, path);
     model.addAttribute("path", path);
     model.addAttribute("tplList", tplList);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/channel/filetree";
   }
 
   @RequestMapping({"/channel/v_edittree.do"})
   public String edittree(Integer parentId, Integer channelId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     model.addAttribute("parentId", parentId);
     Integer siteId = ContextTools.getSiteId(request);
     Integer userId = ContextTools.getUserId(request);
     List list = this.service.getChannelByAdmin(userId, siteId, 
       parentId, null, null, null, null);
     list.remove(this.service.findById(channelId));
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/channel/edittree";
   }
 
   @RequestMapping({"/channel/v_seltree.do"})
   public String seltree(Integer parentId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
     model.addAttribute("parentId", parentId);
     Integer siteId = ContextTools.getSiteId(request);
     Integer userId = ContextTools.getUserId(request);
     List list = this.service.getChannelByAdmin(userId, siteId, 
       parentId, null, null, null, Boolean.valueOf(false));
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/channel/edittree";
   }
 
   @RequestMapping({"/channel/v_inserttree.do"})
   public String addtree(Integer parentId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
     model.addAttribute("parentId", parentId);
     Integer siteId = ContextTools.getSiteId(request);
     Integer userId = ContextTools.getUserId(request);
     List list = this.service.getChannelByAdmin(userId, siteId, 
       parentId, null, null, null, Boolean.valueOf(false));
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/channel/inserttree";
   }
 
   @RequestMapping({"/channel/v_tree.do"})
   public String tree(Integer parentId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
     model.addAttribute("parentId", parentId);
     Integer siteId = ContextTools.getSiteId(request);
     Integer userId = ContextTools.getUserId(request);
     List list = this.service.getChannelByAdmin(userId, siteId, 
       parentId, null, null, null, null);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/channel/tree";
   }
   @RequiresPermissions({"admin:channel:list"})
   @RequestMapping({"/channel/v_list.do"})
   public String list(Integer parentId, ModelMap model) { model.addAttribute("parentId", parentId);
     return "docCenter/channel/list"; }
 
   @RequiresPermissions({"admin:channel:add"})
   @RequestMapping({"/channel/v_add.do"})
   public String add(Integer parentId, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     Channel parent = null;
     if (parentId != null) {
       parent = this.service.findById(parentId);
       model.addAttribute("parent", parent);
       model.addAttribute("parentId", parentId);
       model.addAttribute("modelList", parent.getModelList());
     } else {
       model.addAttribute("modelList", 
         this.modelService.getList(false, null, null));
     }
     List flowList = this.workFlowService.findByList(site.getId());
     List groupList = this.groupService.getList(site.getId(), null, null);
     List viewGroups = groupList;
     Collection contriGroups;
     //Collection contriGroups;
     if (parent != null)
       contriGroups = parent.getContriGroups();
     else {
       contriGroups = groupList;
     }
     model.addAttribute("viewGroups", viewGroups);
     model.addAttribute("contriGroups", contriGroups);
     model.addAttribute("contriGroupIds", Group.fetchIds(contriGroups));
     model.addAttribute("flowList", flowList);
     return "docCenter/channel/add";
   }
   @RequiresPermissions({"admin:channel:edit"})
   @RequestMapping({"/channel/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
 
     Channel channel = this.service.findById(id);
 
     if (channel.getParent() != null)
       model.addAttribute("modelList", channel.getParent().getModelList());
     else {
       model.addAttribute("modelList", 
         this.modelService.getList(false, null, null));
     }
     Integer[] modelIds = ChnlTplSelection.fetchIds(channel.getTpls());
     List groupList = this.groupService.getList(site.getId(), null, null);
     List viewGroups = groupList;
     Integer[] viewGroupIds = Group.fetchIds(channel.getViewGroups());
     Channel parent = channel.getParent();
     Collection contriGroups;
     //Collection contriGroups;
     if (parent != null)
       contriGroups = parent.getContriGroups();
     else {
       contriGroups = groupList;
     }
     Integer[] contriGroupIds = Group.fetchIds(channel.getContriGroups());
     List flowList = this.workFlowService.findByList(site.getId());
     model.addAttribute("chnlmodelList", channel.getModelList());
     model.addAttribute("modelIds", modelIds);
     model.addAttribute("viewGroups", viewGroups);
     model.addAttribute("viewGroupIds", viewGroupIds);
     model.addAttribute("contriGroups", contriGroups);
     model.addAttribute("contriGroupIds", contriGroupIds);
     model.addAttribute("flowList", flowList);
     model.addAttribute("channel", channel);
     return "docCenter/channel/edit";
   }
 
   @RequiresPermissions({"admin:channel:save"})
   @RequestMapping({"/channel/o_save.do"})
   public String save(Integer parentId, Channel bean, ChannelExt ext, ChannelTxt txt, Integer flowId, Integer[] viewGroupIds, Integer[] contriGroupIds, String[] modelIds, HttpServletRequest request, ModelMap model)
   {
     User user = ContextTools.getUser(request);
     Map tpls = ServicesUtils.getRequestMapWithPrefix(
       request, "tpl_", modelIds);
     bean = this.service.save(bean, ext, txt, ContextTools.getSite(request), 
       user, flowId, viewGroupIds, contriGroupIds, parentId, tpls);
     log.info("save Channel id={}, name={}", bean.getId(), bean.getName());
     this.logService.operating(request, "channel.log.save", "id=" + bean.getId() + 
       ";title=" + bean.getTitle());
     model.addAttribute("msg", "栏目添加成功!");
     return add(parentId, request, model);
   }
 
   @RequiresPermissions({"admin:channel:update"})
   @RequestMapping({"/channel/o_update.do"})
   public String update(Channel bean, ChannelExt ext, ChannelTxt txt, Integer flowId, Integer[] viewGroupIds, Integer[] contriGroupIds, Integer parentId, String[] modelIds, HttpServletRequest request, ModelMap model)
   {
     Map tpls = ServicesUtils.getRequestMapWithPrefix(
       request, "tpl_", modelIds);
     bean = this.service.update(bean, ext, txt, flowId, viewGroupIds, 
       contriGroupIds, parentId, tpls);
     log.info("update Channel id={}.", bean.getId());
     this.logService.operating(request, "channel.log.update", 
       "id=" + bean.getId() + ";name=" + bean.getName());
     model.addAttribute("msg", "栏目修改成功!");
     return edit(bean.getId(), request, model);
   }
 
   @RequestMapping({"/channel/jsonData.do"})
   public String dataPageByJosn(Integer parentId, String key, String sortname, String sortorder, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Integer userId = ContextTools.getUserId(request);
     Integer siteId = ContextTools.getSiteId(request);
     List list = this.service.getChannelByAdmin(userId, siteId, 
       parentId, key, sortname, sortorder, null);
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/channel/listdata";
   }
   @RequiresPermissions({"admin:channel:delete"})
   @RequestMapping({"/channel/o_delchnl.do"})
   public String delChannel(Integer chnlId, Boolean del, Integer cid, HttpServletRequest request, ModelMap model) {
     if ((del == null) || (del.booleanValue())) {
       long count = this.articleService.getCountDoc(chnlId);
       if (count > 0L) {
         model.addAttribute("msge", "请先转移或者清除该栏目下的文档!");
         return edit(chnlId, request, model);
       }
     }
     Channel parent = this.service.findById(chnlId).getParent();
     this.service.delChannel(chnlId, del, cid);
     model.addAttribute("msg", "删除栏目成功!");
     if (parent != null) {
       return edit(parent.getId(), request, model);
     }
     return list(null, model);
   }
 
   @RequiresPermissions({"admin:channel:delete"})
   @RequestMapping({"/channel/o_ajax_delete.do"})
   public void deleteChannel(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     long count;
     for (Integer cid : ids) {
       count = this.articleService.getCountDoc(cid);
       if (count > 0L) {
         json.put("success", false);
         json.put("status", 0);
         json.put("msg", "请先清空文档再删除栏目!");
         ResponseUtils.renderJson(response, json.toString());
         return;
       }
     }
     Channel[] beans = this.service.deleteByIds(ids);
     for (Channel bean : beans) {
       log.info("delete Channel id={}", bean.getId());
       this.logService.operating(request, "channel.log.delete", 
         "id=" + bean.getId() + ";title=" + bean.getTitle());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:channel:static"})
   @RequestMapping({"/channel/o_static.do"})
   public void staticChannel(Integer chnlId, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, JSONException
   {
     JSONObject json = new JSONObject();
     Channel c = this.service.findById(chnlId);
     this.staticPageService.staticChannelPage(c);
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:channel:priority"})
   @RequestMapping({"/channel/o_priority.do"})
   public void itemPriority(Integer id, Integer priority, HttpServletRequest request, HttpServletResponse response) throws JSONException
   {
     JSONObject json = new JSONObject();
     this.service.updatePrio(id, priority);
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 }


 
 
 