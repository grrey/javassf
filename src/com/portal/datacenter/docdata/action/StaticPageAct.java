package com.portal.datacenter.docdata.action;

import com.javassf.basic.utils.ResponseUtils;
import com.portal.doccenter.entity.Channel;
import com.portal.doccenter.service.ChannelService;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.staticpage.StaticPageService;
import com.portal.sysmgr.utils.ContextTools;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticPageAct {

	@Autowired
	private StaticPageService staticPageService;

	@Autowired
	private ChannelService channelService;

	@RequiresPermissions({ "admin:static:view" })
	@RequestMapping({ "/static/v_static_index.do" })
	public String staticIndex(HttpServletRequest request, HttpServletResponse response) {
		return "dataCenter/staticPage/index";
	}

	@RequiresPermissions({ "admin:static:index" })
	@RequestMapping({ "/static/o_static_index.do" })
	public void staticIndexop(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, JSONException {
		JSONObject json = new JSONObject();
		Site site = ContextTools.getSite(request);
		int i = this.staticPageService.staticIndex(site);
		json.put("i", i);
		json.put("success", true);
		json.put("status", 1);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequiresPermissions({ "admin:static:channel" })
	@RequestMapping({ "/static/o_static_channel.do" })
	public String staticChannel(Integer chnlId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		List list = new ArrayList();
		if (chnlId != null) {
			Channel c = this.channelService.findById(chnlId);
			list = this.staticPageService.staticChannelPage(c);
		} else {
			list = this.staticPageService.staticChannelPage(null);
		}
		model.addAttribute("list", list);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "dataCenter/staticPage/alert";
	}

	@RequiresPermissions({ "admin:static:article" })
	@RequestMapping({ "/static/o_static_article.do" })
	public void staticArticle(Integer chnlId, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException, JSONException {
		JSONObject json = new JSONObject();
		if (chnlId != null) {
			Channel c = this.channelService.findById(chnlId);
			String s = this.staticPageService.staticArticlePage(c);
			json.put("msg", s);
		} else {
			String s = this.staticPageService.staticArticlePage(null);
			json.put("msg", s);
		}
		json.put("success", true);
		json.put("status", 1);
		ResponseUtils.renderJson(response, json.toString());
	}
}
