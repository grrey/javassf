package com.portal.doccenter.action.fnt;

import com.javassf.basic.utils.ServicesUtils;
import com.portal.doccenter.entity.Channel;
import com.portal.doccenter.service.ChannelService;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.staticpage.StaticPageService;
import com.portal.sysmgr.utils.ContextTools;
import com.portal.sysmgr.utils.ViewTools;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ChannelAct {
	public static final String TPL_INDEX = "tpl.index";
	public static final String TPL_CHANNEL_LEADER = "tpl.channelLeader";

	@Autowired
	private ChannelService channelService;

	@Autowired
	private StaticPageService staticPageService;

	@RequestMapping({ "/", "/index.jsp" })
	public String index(HttpServletRequest request, ModelMap model) {
		Site site = ContextTools.getSite(request);
		if (!site.getStaticChannel().equals(Site.NO_STATIC)) {
			System.out.println("---sp:"+site.getStaticRealPath());
			return site.getStaticRealPath();
		}
		ViewTools.frontData(request, model, site);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "doc/index", "tpl.index");
	}

	@RequestMapping({ "/{path}/index.jsp" })
	public String channelIndex(@PathVariable String path, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return channelIndexpage(path, Integer.valueOf(1), request, response, model);
	}

	@RequestMapping({ "/{path}/index_{page:[0-9]+}.jsp" })
	public String channelIndexpage(@PathVariable String path, @PathVariable Integer page, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		Channel channel = this.channelService.findByPathForTag(path, site.getId());
		if (channel == null) {
			return ViewTools.pageNotFound(response);
		}
		if (!StringUtils.isBlank(channel.getLink())) {
			return "redirect:" + channel.getLink();
		}
		if (channel.getStaticChannel()) {
			this.staticPageService.staticChannelCheck(channel, page);
			return channel.getChannelRealPath(page);
		}
		model.addAttribute("channel", channel);
		ViewTools.frontData(request, model, site);
		ViewTools.frontPageData(channel.getUrl(page), model, page);
		return channel.getTplChannelOrDef();
	}

	@RequestMapping({ "/channel/leader.jsp" })
	public String channeltpl(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		return channeltplpage(Integer.valueOf(1), request, response, model);
	}

	@RequestMapping({ "/channel/leader_{page:[0-9]+}.jsp" })
	public String channeltplpage(@PathVariable Integer page, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		model.putAll(ServicesUtils.getQueryParams(request));
		ViewTools.frontData(request, model, site);
		ViewTools.frontPageData(request, model, page);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "doc/channel", "tpl.channelLeader");
	}
}
