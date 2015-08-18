 package com.portal.doccenter.action;
 
 import com.javassf.basic.plugin.springmvc.RealPathResolver;
 import com.javassf.basic.utils.ResponseUtils;
 import com.javassf.basic.utils.ServicesUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.doccenter.entity.Article;
 import com.portal.doccenter.entity.ArticleExt;
 import com.portal.doccenter.entity.ArticleTxt;
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.entity.Model;
 import com.portal.doccenter.service.ArticleService;
 import com.portal.doccenter.service.ArticleTypeService;
 import com.portal.doccenter.service.ChannelService;
 import com.portal.doccenter.service.ModelFieldService;
 import com.portal.doccenter.service.ModelService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.utils.ContextTools;
 import com.portal.usermgr.entity.Group;
 import com.portal.usermgr.entity.User;
 import com.portal.usermgr.service.GroupService;
 import java.io.File;
 import java.io.IOException;
 import java.io.StringReader;
 import java.util.ArrayList;
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
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.wltea.analyzer.IKSegmentation;
 import org.wltea.analyzer.Lexeme;
 
 @Controller
 public class ArticleAct
 {
   private static final Logger log = LoggerFactory.getLogger(ArticleAct.class);
 
   @Autowired
   private ChannelService channelService;
 
   @Autowired
   private ModelService modelService;
 
   @Autowired
   private ModelFieldService modelFieldService;
 
   @Autowired
   private GroupService groupService;
 
   @Autowired
   private ArticleTypeService articleTypeService;
 
   @Autowired
   private RealPathResolver realPathResolver;
 
   @Autowired
   private LogService logService;
 
   @Autowired
   private ArticleService service;
 
   @RequestMapping({"/doc/v_tree.do"})
   public String tree(Integer parentId, HttpServletRequest request, HttpServletResponse response, ModelMap model) { model.addAttribute("parentId", parentId);
     Integer siteId = ContextTools.getSiteId(request);
     Integer userId = ContextTools.getUserId(request);
     List list = this.channelService.getChannelByAdmin(userId, siteId, 
       parentId, null, null, null, Boolean.valueOf(false));
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/document/tree";
   }
 
   @RequestMapping({"/doc/v_addtree.do"})
   public String addtree(Integer cid, Integer parentId, Integer modelId, Integer chnlId, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     model.addAttribute("parentId", parentId);
     List list = new ArrayList();
     Integer siteId = ContextTools.getSiteId(request);
     User user = ContextTools.getUser(request);
     if ((cid != null) && (parentId == null)) {
       parentId = cid;
       list.add(this.channelService.findById(cid));
     } else {
       list = this.channelService.getChannelByModel(parentId, modelId, user, 
         siteId);
     }
     if (chnlId != null) {
       list.remove(this.channelService.findById(chnlId));
     }
     model.addAttribute("list", list);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/document/addtree";
   }
   @RequiresPermissions({"admin:doc:list"})
   @RequestMapping({"/doc/v_list.do"})
   public String list(Integer chnlId, HttpServletRequest request, ModelMap model) {
     if (chnlId != null) {
       Channel chnl = this.channelService.findById(chnlId);
       model.addAttribute("modelList", chnl.getModelList());
     } else {
       model.addAttribute("modelList", 
         this.modelService.getList(false, null, null));
     }
     List typeList = this.articleTypeService.getList(false, null, 
       null);
     model.addAttribute("allModel", this.modelService.getList(false, null, null));
     model.addAttribute("chnlId", chnlId);
     model.addAttribute("typeList", typeList);
     return "docCenter/document/list";
   }
 
   @RequiresPermissions({"admin:doc:list1"})
   @RequestMapping({"/doc/v_list1.do"})
   public String list1(String title, Byte[] status, Integer[] typeIds, Integer[] modelIds, boolean top, boolean recommend, Integer orderBy, Integer chnlId, Integer page, Integer pagesize, String sortname, String sortorder, HttpServletRequest request, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Integer siteId = site.getId();
     User user = ContextTools.getUser(request);
     if (chnlId != null) {
       Channel chnl = this.channelService.findById(chnlId);
       model.addAttribute("modelList", chnl.getModelList());
     } else {
       model.addAttribute("modelList", 
         this.modelService.getList(false, null, null));
     }
     List typeList = this.articleTypeService.getList(false, null, 
       null);
     Page p = this.service.getPageArticle(StringUtils.trim(title), 
       typeIds, modelIds, null, top, recommend, status, siteId, user, 
       chnlId, 0, sortname, sortorder, 1, 30);
     model.addAttribute("p", p);
     model.addAttribute("siteId", siteId);
     model.addAttribute("allModel", this.modelService.getList(false, null, null));
     model.addAttribute("chnlId", chnlId);
     model.addAttribute("typeList", typeList);
     return "docCenter/document/list1";
   }
   @RequiresPermissions({"admin:doc:add"})
   @RequestMapping({"/doc/v_add.do"})
   public String add(Integer cid, Integer modelId, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     Channel c = null;
     if (cid != null) {
       c = this.channelService.findById(cid);
     }
     Model m = this.modelService.findById(modelId);
     List fieldList = this.modelFieldService.getList(m.getId(), 
       false, null, null);
     List groupList = this.groupService.getList(site.getId(), null, null);
     List typeList = this.articleTypeService.getList(false, null, 
       null);
     model.addAttribute("model", m);
     model.addAttribute("fieldList", fieldList);
     model.addAttribute("groupList", groupList);
     model.addAttribute("typeList", typeList);
     if (cid != null) {
       model.addAttribute("cid", cid);
     }
     if (c != null) {
       model.addAttribute("channel", c);
     }
     return "docCenter/document/add";
   }
   @RequiresPermissions({"admin:doc:edit"})
   @RequestMapping({"/doc/v_edit.do"})
   public String edit(Integer id, HttpServletRequest request, ModelMap model) { Site site = ContextTools.getSite(request);
     Article article = this.service.findById(id);
     List fieldList = this.modelFieldService.getList(article
       .getModel().getId(), false, null, null);
     List groupList = this.groupService.getList(site.getId(), null, null);
     Integer[] groupIds = Group.fetchIds(article.getViewGroups());
     List typeList = this.articleTypeService.getList(false, null, 
       null);
     model.addAttribute("article", article);
     model.addAttribute("channel", article.getChannel());
     model.addAttribute("fieldList", fieldList);
     model.addAttribute("groupList", groupList);
     model.addAttribute("groupIds", groupIds);
     model.addAttribute("typeList", typeList);
     return "docCenter/document/edit";
   }
 
   @RequiresPermissions({"admin:doc:save"})
   @RequestMapping({"/doc/o_save.do"})
   public String save(Article bean, ArticleExt ext, ArticleTxt txt, Integer modelId, Integer[] viewGroupIds, String[] attPaths, String[] attNames, String[] imgPaths, String[] imgDescs, Boolean[] thumbs, String[] imgStyles, Integer channelId, HttpServletRequest request, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     User user = ContextTools.getUser(request);
     bean.setAttr(ServicesUtils.getRequestMap(request, "attr_"));
 
     bean = this.service.save(bean, ext, txt, site, user, viewGroupIds, attPaths, 
       attNames, imgPaths, imgDescs, thumbs, imgStyles, channelId, 
       modelId, false);
     log.info("save Article id={}", bean.getId());
     this.logService.operating(request, "添加文档", "id=" + bean.getId() + ";title=" + 
       bean.getTitle());
     model.addAttribute("msg", "文档添加成功");
     return add(channelId, modelId, request, model);
   }
 
   @RequiresPermissions({"admin:doc:update"})
   @RequestMapping({"/doc/o_update.do"})
   public String update(Article bean, ArticleExt ext, ArticleTxt txt, Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds, String[] attPaths, String[] attNames, String[] imgPaths, String[] imgDescs, Boolean[] thumbs, String[] imgStyles, Integer channelId, HttpServletRequest request, ModelMap model)
   {
     User user = ContextTools.getUser(request);
     Map attr = 
       ServicesUtils.getRequestMap(request, "attr_");
     bean = this.service.update(bean, ext, txt, channelIds, viewGroupIds, 
       attPaths, attNames, imgPaths, imgDescs, thumbs, imgStyles, 
       attr, channelId, user, false);
     log.info("update Article id={}.", bean.getId());
     this.logService.operating(request, "修改文档", "id=" + bean.getId() + ";title=" + 
       bean.getTitle());
     model.addAttribute("msg", "文档修改成功");
     return edit(bean.getId(), request, model);
   }
 
   @RequiresPermissions({"admin:doc:move"})
   @RequestMapping({"/doc/o_move.do"})
   public void move(Integer chnlId, String[] modelIds, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Map channels = ServicesUtils.getRequestMapWithPrefix(
       request, "chnl_", modelIds);
     int count = this.service.moveDoc(chnlId, channels);
     json.put("success", true);
     json.put("count", count);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:doc:empty"})
   @RequestMapping({"/doc/o_empty.do"})
   public void empty(Integer chnlId, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     int count = this.service.emptyDoc(chnlId);
     json.put("success", true);
     json.put("count", count);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequestMapping({"/doc/jsonData.do"})
   public String dataPageByJosn(String title, Byte[] status, Integer[] typeIds, Integer[] modelIds, boolean top, boolean recommend, Integer orderBy, Integer chnlId, Integer page, Integer pagesize, String sortname, String sortorder, HttpServletRequest request, HttpServletResponse response, ModelMap model)
   {
     Site site = ContextTools.getSite(request);
     Integer siteId = site.getId();
     User user = ContextTools.getUser(request);
     Page p = this.service.getPageArticle(StringUtils.trim(title), 
       typeIds, modelIds, null, top, recommend, status, siteId, user, 
       chnlId, 0, sortname, sortorder, page.intValue(), pagesize.intValue());
     List typeList = this.articleTypeService.getList(true, null, 
       null);
     model.addAttribute("p", p);
     model.addAttribute("siteId", siteId);
     model.addAttribute("typeList", typeList);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "docCenter/document/listdata";
   }
   @RequiresPermissions({"admin:doc:delete"})
   @RequestMapping({"/doc/o_ajax_delete.do"})
   public void deleteArticle(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Article[] beans = this.service.deleteByIds(ids);
     for (Article bean : beans) {
       log.info("删除文档: id={}", bean.getId());
       this.logService.operating(request, "删除文档", "id=" + bean.getId() + 
         ";title=" + bean.getTitle());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:doc:cycle"})
   @RequestMapping({"/doc/o_ajax_cycle.do"})
   public void cycleArticle(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Article[] beans = this.service.cycle(ids);
     for (Article bean : beans) {
       log.info("文档放入回收站,id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:doc:reduct"})
   @RequestMapping({"/doc/o_ajax_reduct.do"})
   public void reductArticle(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Article[] beans = this.service.reduct(ids);
     for (Article bean : beans) {
       log.info("文档还原,id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:doc:check"})
   @RequestMapping({"/doc/o_check.do"})
   public void check(Integer[] ids, String chnlNumber, HttpServletRequest request, HttpServletResponse response) throws JSONException
   {
     JSONObject json = new JSONObject();
     User user = ContextTools.getUser(request);
     Article[] beans = this.service.check(ids, user);
     for (Article bean : beans) {
       log.info("check Doc id={}", bean.getId());
     }
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequestMapping({"/doc/o_delete_file.do"})
   public void deleteFile(String path, HttpServletRequest request, HttpServletResponse response)
   {
     if (path.indexOf("/member/upload/") > -1) {
       path = path.substring(path.indexOf("/member/upload/"));
     }
     String realpath = this.realPathResolver.get(path);
     File f = new File(realpath);
     if (f.exists()) {
       f.delete();
     }
     ResponseUtils.renderText(response, "");
   }
 
   @RequestMapping({"/doc/v_titletags.do"})
   public void titleTags(String title, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
     StringBuffer strb = new StringBuffer("");
     JSONObject json = new JSONObject();
     if (!StringUtils.isBlank(title)) {
       IKSegmentation ikSeg = new IKSegmentation(new StringReader(title), 
         true);
       try {
         Lexeme l = null;
         while ((l = ikSeg.next()) != null) {
           if (l.getLexemeType() == 0) {
             strb.append(l.getLexemeText());
             strb.append(",");
           }
         }
         json.put("success", true);
         if (strb.toString().length() > 1) {
           json.put(
             "tag", 
             strb.toString().substring(0, 
             strb.toString().length() - 1));
         }
         ResponseUtils.renderJson(response, json.toString());
       } catch (IOException e) {
         e.printStackTrace();
       }
     }
   }
 }


 
 
 