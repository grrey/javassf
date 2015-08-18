 package com.portal.sysmgr.action;
 
 import com.javassf.basic.utils.ResponseUtils;
 import com.portal.datacenter.operatedata.service.LogService;
 import com.portal.sysmgr.entity.Site;
 import com.portal.sysmgr.service.ResourceService;
 import com.portal.sysmgr.utils.ContextTools;
 import java.io.IOException;
 import java.util.List;
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
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.multipart.MultipartFile;
 
 @Controller
 public class ResourceAct
 {
   private static final Logger log = LoggerFactory.getLogger(ResourceAct.class);
 
   @Autowired
   private LogService logService;
   private ResourceService resourceService;
 
   @RequestMapping({"/res/v_tree.do"})
   public String tree(String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) { Site site = ContextTools.getSite(request);
     String root = site.getResSolutionPath();
     if (StringUtils.isBlank(path)) {
       path = "";
     }
     List resList = this.resourceService.listFile(root, path, 
       true);
     model.addAttribute("path", path);
     model.addAttribute("resList", resList);
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "sysMgr/resMgr/tree"; } 
   @RequiresPermissions({"admin:res:list"})
   @RequestMapping({"/res/v_list.do"})
   public String list(String path, HttpServletRequest request, ModelMap model) {
     model.addAttribute("path", path);
     return "sysMgr/resMgr/list";
   }
   @RequiresPermissions({"admin:res:createdir"})
   @RequestMapping({"/res/o_create_dir.do"})
   public String createDir(String path, String dirName, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     String root = site.getResSolutionPath();
     if (StringUtils.isBlank(path)) {
       path = "";
     }
     this.resourceService.createDir(root + path, dirName);
     model.addAttribute("path", path);
     model.addAttribute("msg", "目录新建成功!");
     return list(path, request, model);
   }
   @RequiresPermissions({"admin:res:add"})
   @RequestMapping({"/res/v_add.do"})
   public String add(String path, HttpServletRequest request, ModelMap model) { model.addAttribute("path", path);
     return "sysMgr/resMgr/add"; }
 
   @RequiresPermissions({"admin:res:edit"})
   @RequestMapping({"/res/v_edit.do"})
   public String edit(String name, HttpServletRequest request, ModelMap model) throws IOException {
     Site site = ContextTools.getSite(request);
     String root = site.getResSolutionPath();
     model.addAttribute("source", this.resourceService.readFile(root + name));
     model.addAttribute("name", name);
     model.addAttribute("filename", 
       name.substring(name.lastIndexOf('/') + 1));
     return "sysMgr/resMgr/edit";
   }
   @RequiresPermissions({"admin:res:save"})
   @RequestMapping({"/res/o_save.do"})
   public String save(String path, String filename, String source, HttpServletRequest request, ModelMap model) throws IOException {
     Site site = ContextTools.getSite(request);
     String root = site.getResSolutionPath();
     if (StringUtils.isBlank(path)) {
       path = "";
     }
     this.resourceService.createFile(root + path, filename, source);
     this.logService.operating(request, "添加资源", "filename=" + filename);
     model.addAttribute("msg", "资源创建成功!");
     return add(path, request, model);
   }
 
   @RequiresPermissions({"admin:res:update"})
   @RequestMapping({"/res/o_update.do"})
   public String update(String name, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
     Site site = ContextTools.getSite(request);
     String root = site.getResSolutionPath();
     this.resourceService.updateFile(root + name, source);
     log.info("update Resource name={}.", name);
     this.logService.operating(request, "修改资源", "filename=" + name);
     model.addAttribute("msg", "资源修改成功!");
     return edit(name, request, model);
   }
 
   @RequestMapping({"/res/jsonData.do"})
   public String dataPageByJosn(String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
     Site site = ContextTools.getSite(request);
     String root = site.getResSolutionPath();
     log.debug("list Res root: {}", root);
     if (StringUtils.isBlank(path)) {
       path = "";
     }
     model.addAttribute("path", path);
     model.addAttribute("list", this.resourceService.listFile(root, path, false));
     response.setHeader("Cache-Control", "no-cache");
     response.setContentType("text/json;charset=UTF-8");
     return "sysMgr/resMgr/listdata";
   }
   @RequiresPermissions({"admin:res:delete"})
   @RequestMapping({"/res/o_ajax_delete.do"})
   public void deleteGroup(String[] names, HttpServletRequest request, HttpServletResponse response) throws JSONException {
     JSONObject json = new JSONObject();
     Site site = ContextTools.getSite(request);
     String root = site.getResSolutionPath();
     this.resourceService.delete(root, names);
     json.put("success", true);
     json.put("status", 1);
     ResponseUtils.renderJson(response, json.toString());
   }
 
   @RequiresPermissions({"admin:res:rename"})
   @RequestMapping(value={"/res/o_rename.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public String renameSubmit(String path, String origName, String distName, HttpServletRequest request, ModelMap model) {
     Site site = ContextTools.getSite(request);
     String root = site.getResSolutionPath();
     if (StringUtils.isBlank(path)) {
       path = "";
     }
     String orig = root + path + "/" + origName;
     String dist = root + path + "/" + distName;
     this.resourceService.rename(orig, dist);
     log.info("name Resource from {} to {}", orig, dist);
     model.addAttribute("path", path);
     model.addAttribute("msg", "资源重命名成功!");
     return list(path, request, model);
   }
 
   @RequiresPermissions({"admin:res:swfupload"})
   @RequestMapping(value={"/res/o_swfupload.do"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
   public void swfUpload(String path, @RequestParam(value="resatt", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response, ModelMap model)
     throws IllegalStateException, IOException
   {
     Site site = ContextTools.getSite(request);
     String root = site.getResSolutionPath();
     if (StringUtils.isBlank(path)) {
       path = "";
     }
     this.resourceService.saveFile(root + path, file);
     log.info("file upload seccess: {}, size:{}.", 
       file.getOriginalFilename(), Long.valueOf(file.getSize()));
     ResponseUtils.renderText(response, "");
   }
 
   @Autowired
   public void setResourceService(ResourceService resourceService)
   {
     this.resourceService = resourceService;
   }
 }


 
 
 