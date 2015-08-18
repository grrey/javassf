package com.portal.sysmgr.utils;

import com.javassf.basic.plugin.springmvc.MessageResolver;
import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.User;
import freemarker.core.Environment;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

public class ViewTools {
	public static final String PROTOCOL = "http://";

	public static String getTplPath(HttpServletRequest request, String solutionPath, String dir, String name) {
		if (request != null) {
			return solutionPath + "/" + dir + "/" + MessageResolver.getMessage(request, name, new Object[0]) + ".html";
		}
		return solutionPath + "/" + dir + "/" + name + ".html";
	}

	public static String pageNotFound(HttpServletResponse response) {
		try {
			response.sendError(404);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String showMessage(String nextUrl, HttpServletRequest request, Map<String, Object> model, String msg, Integer status) {
		Site site = ContextTools.getSite(request);
		if (!StringUtils.isBlank(msg)) {
			model.put("msg", msg);
		}
		frontData(request, model, site);
		if (!StringUtils.isBlank(nextUrl)) {
			model.put("url", nextUrl);
		}
		model.put("status", status);
		return getTplPath(request, site.getSolutionPath(), "common/tips", "tpl.msg");
	}

	public static String showLogin(String url, HttpServletRequest request, Map<String, Object> model, String msg) {
		Site site = ContextTools.getSite(request);
		if (!StringUtils.isBlank(msg)) {
			model.put("msg", msg);
		}
		frontData(url, model, site);
		return getTplPath(request, site.getSolutionPath(), "user", "member.login");
	}

	public static String showLogin(HttpServletRequest request, Map<String, Object> model, String msg) {
		Site site = ContextTools.getSite(request);
		if (!StringUtils.isBlank(msg)) {
			model.put("msg", msg);
		}
		frontData(request, model, site);
		return getTplPath(request, site.getSolutionPath(), "user", "member.login");
	}

	public static String showNextUrl(String nextUrl, Site site) {
		if (nextUrl.indexOf("http://") > -1) {
			return nextUrl;
		}
		if (nextUrl.indexOf(site.getDomain()) > -1) {
			return "http://" + nextUrl;
		}
		if ((!StringUtils.isBlank(site.getContextPath())) && (nextUrl.startsWith(site.getContextPath()))) {
			return nextUrl.substring(site.getContextPath().length());
		}
		if (nextUrl.startsWith("/")) {
			return nextUrl.substring(1);
		}
		return nextUrl;
	}

	public static void frontData(String url, Map<String, Object> map, Site site) {
		if (StringUtils.isNotBlank(url)) {
			map.put("url", url);
		}
		map.put("site", site);
		String ctx = site.getContextPath() == null ? "" : site.getContextPath();
		map.put("base", ctx);
		map.put("comm", ctx + "/skin/comm");
		String res_skin = ctx + "/skin" + "/" + site.getPath() + "/" + site.getTplStyle();
		map.put("skin", res_skin.substring(1));
	}

	public static void frontData(HttpServletRequest request, Map<String, Object> map, Site site) {
		String url = URLTools.getUrl(request);
		User user = ContextTools.getUser(request);
		if (user != null) {
			map.put("user", user);
		}
		frontData(url, map, site);
	}

	public static void frontPageData(String url, Map<String, Object> map, Integer page) {
		map.put("pn", page);
		map.put("url", url);
	}

	public static void frontPageData(HttpServletRequest request, Map<String, Object> map, Integer page) {
		String url = URLTools.getUrl(request);
		map.put("pn", page);
		map.put("url", url);
	}

	public static Site getSite(Environment env) throws TemplateModelException {
		TemplateModel model = env.getGlobalVariable("site");
		if ((model instanceof AdapterTemplateModel)) {
			return (Site) ((AdapterTemplateModel) model).getAdaptedObject(Site.class);
		}
		throw new TemplateModelException("'site' not found in DataModel");
	}

	public static int getPageNo(Environment env) throws TemplateException {
		TemplateModel pageNo = env.getGlobalVariable("pn");
		if ((pageNo instanceof TemplateNumberModel)) {
			return ((TemplateNumberModel) pageNo).getAsNumber().intValue();
		}
		return 1;
	}

	public static int getFirst(Map<String, TemplateModel> params) throws TemplateException {
		Integer first = TagModelTools.getInt("first", params);
		if ((first == null) || (first.intValue() <= 0)) {
			return 0;
		}
		return first.intValue() - 1;
	}

	public static int getCount(Map<String, TemplateModel> params) throws TemplateException {
		Integer count = TagModelTools.getInt("count", params);
		if ((count == null) || (count.intValue() <= 0)) {
			return 20;
		}
		return count.intValue();
	}

	public static String getUrl(Environment env) throws TemplateModelException {
		TemplateModel model = env.getDataModel().get("url");
		return TagModelTools.getString("url", model);
	}

	public static void includePagination(Site site, Map<String, TemplateModel> params, Environment env) throws TemplateException, IOException {
		String pageType = TagModelTools.getString("pageType", params);
		if (!StringUtils.isBlank(pageType)) {
			String tpl = site.getSolutionPath() + "/common/style_page/page_" + pageType + ".html";
			env.include(tpl, "UTF-8", true);
		}
	}

	public static void includeTpl(String tplName, Site site, Environment env) throws IOException, TemplateException {
		String tpl = getTplPath(null, site.getSolutionPath(), "common/tags", tplName);
		env.include(tpl, "UTF-8", true);
	}
}
