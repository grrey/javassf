package com.portal.sysmgr.action;

import com.javassf.basic.utils.ResponseUtils;
import com.javassf.basic.utils.ServicesUtils;
import com.portal.datacenter.operatedata.service.LogService;
import com.portal.doccenter.service.ArticleTypeService;
import com.portal.doccenter.service.ModelService;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.service.ResourceService;
import com.portal.sysmgr.service.SiteService;
import com.portal.sysmgr.service.TplService;
import com.portal.sysmgr.utils.ContextTools;
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

@Controller
public class TemplateAct {
	private static final Logger log = LoggerFactory.getLogger(TemplateAct.class);

	@Autowired
	private LogService logService;
	private TplService tplService;
	private ResourceService resourceService;
	private SiteService siteService;
	private ArticleTypeService typeService;

	@Autowired
	private ModelService modelService;

	@RequestMapping(value = { "/tpl/v_tpl_dirtree.do" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String dirtree(String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
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
		return "sysMgr/tplMgr/dirtree";
	}

	@RequestMapping(value = { "/tpl/v_tpl_filetree.do" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String tpltree(String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
		return "sysMgr/tplMgr/filetree";
	}

	@RequestMapping({ "/tpl/v_tree.do" })
	public String tree(String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		String root = site.getSolutionPath();
		log.debug("tree path={}", root);
		if (StringUtils.isBlank(path)) {
			model.addAttribute("isRoot", Boolean.valueOf(true));
			path = "";
		} else {
			model.addAttribute("isRoot", Boolean.valueOf(false));
		}
		List tplList = this.tplService.getChild(root, path);
		model.addAttribute("path", path);
		model.addAttribute("tplList", tplList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "sysMgr/tplMgr/tree";
	}

	@RequiresPermissions({ "admin:tpl:list" })
	@RequestMapping(value = { "/tpl/v_list.do" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String list(String path, HttpServletRequest request, ModelMap model) {
		model.addAttribute("path", path);
		return "sysMgr/tplMgr/list";
	}

	@RequiresPermissions({ "admin:tpl:createdir" })
	@RequestMapping({ "/tpl/o_create_dir.do" })
	public String createDir(String path, String dirName, HttpServletRequest request, ModelMap model) {
		Site site = ContextTools.getSite(request);
		String root = site.getSolutionPath();
		if (StringUtils.isBlank(path)) {
			path = "";
		}
		this.tplService.save(root + path + "/" + dirName, null, true);
		model.addAttribute("path", path);
		model.addAttribute("msg", "目录新建成功!");
		return list(path, request, model);
	}

	@RequiresPermissions({ "admin:tpl:add" })
	@RequestMapping(value = { "/tpl/v_add.do" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String add(String path, HttpServletRequest request, ModelMap model) {
		List typeList = this.typeService.getList(false, null, null);
		model.addAttribute("path", path);
		model.addAttribute("modelList", this.modelService.getList(false, null, null));
		model.addAttribute("typeList", typeList);
		return "sysMgr/tplMgr/add";
	}

	@RequiresPermissions({ "admin:tpl:edit" })
	@RequestMapping(value = { "/tpl/v_edit.do" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String edit(HttpServletRequest request, ModelMap model) {
		Site site = ContextTools.getSite(request);
		String name = ServicesUtils.getQueryParam(request, "name");
		List typeList = this.typeService.getList(false, null, null);
		String root = site.getSolutionPath();
		model.addAttribute("tpl", this.tplService.get(root, name));
		model.addAttribute("modelList", this.modelService.getList(false, null, null));
		model.addAttribute("typeList", typeList);
		return "sysMgr/tplMgr/edit";
	}

	@RequiresPermissions({ "admin:tpl:save" })
	@RequestMapping({ "/tpl/o_save.do" })
	public String save(String path, String filename, String source, HttpServletRequest request, ModelMap model) {
		Site site = ContextTools.getSite(request);
		String root = site.getSolutionPath();
		if (StringUtils.isBlank(path)) {
			path = "";
		}
		String name = root + path + "/" + filename + ".html";
		this.tplService.save(name, source, false);
		model.addAttribute("path", path);
		log.info("save Tpl name={}", filename);
		this.logService.operating(request, "创建模板", "filename=" + filename);
		model.addAttribute("msg", "模板创建成功!");
		return add(path, request, model);
	}

	@RequiresPermissions({ "admin:tpl:update" })
	@RequestMapping(value = { "/tpl/o_update.do" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String update(String name, String source, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		String root = site.getSolutionPath();
		this.tplService.update(root + name, source);
		log.info("update Tpl name={}.", name);
		this.logService.operating(request, "添加模板", "filename=" + name);
		model.addAttribute("msg", "模板修改成功!");
		return edit(request, model);
	}

	@RequestMapping({ "/tpl/jsonData.do" })
	public String dataPageByJosn(String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		String root = site.getSolutionPath();
		log.debug("list Template root: {}", root);
		if (StringUtils.isBlank(path)) {
			path = "";
		}
		model.addAttribute("path", path);
		model.addAttribute("list", this.tplService.getChild(root, path));
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "sysMgr/tplMgr/listdata";
	}

	@RequiresPermissions({ "admin:tpl:delete" })
	@RequestMapping({ "/tpl/o_ajax_delete.do" })
	public void deleteGroup(String[] names, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		Site site = ContextTools.getSite(request);
		String root = site.getSolutionPath();
		this.tplService.delete(root, names);
		json.put("success", true);
		json.put("status", 1);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequiresPermissions({ "admin:tpl:rename" })
	@RequestMapping(value = { "/tpl/o_rename.do" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String renameSubmit(String path, String origName, String distName, HttpServletRequest request, ModelMap model) {
		Site site = ContextTools.getSite(request);
		String root = site.getSolutionPath();
		String orig = root + path + "/" + origName;
		String dist = root + path + "/" + distName;
		this.tplService.rename(orig, dist);
		log.info("name Tpl from {} to {}", orig, dist);
		model.addAttribute("path", path);
		model.addAttribute("msg", "模板重命名成功!");
		return list(path, request, model);
	}

	@RequiresPermissions({ "admin:tpl:setting" })
	@RequestMapping({ "/tpl/v_setting.do" })
	public String setting(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		String[] styles = this.resourceService.getStyles(site.getTplPath());
		model.addAttribute("styles", styles);
		model.addAttribute("defStyle", site.getTplStyle());
		return "sysMgr/tplMgr/setting";
	}

	@RequiresPermissions({ "admin:tpl:deftemplate" })
	@RequestMapping({ "/tpl/o_def_template.do" })
	public void defTempate(String style, HttpServletRequest request, HttpServletResponse response) {
		Site site = ContextTools.getSite(request);
		this.siteService.updateTplStyle(site.getId(), style);
		ResponseUtils.renderJson(response, "{'success':true}");
	}

	@Autowired
	public void setTplManager(TplService tplService) {
		this.tplService = tplService;
	}

	@Autowired
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Autowired
	public void setTypeService(ArticleTypeService typeService) {
		this.typeService = typeService;
	}
}