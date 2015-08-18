package com.portal.doccenter.action.fnt;

import com.portal.doccenter.entity.Model;
import com.portal.doccenter.service.ModelService;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.utils.ContextTools;
import com.portal.sysmgr.utils.URLTools;
import com.portal.sysmgr.utils.ViewTools;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchAct {
	public static final String SEARCH_INPUT = "tpl.searchInput";
	public static final String SEARCH_RESULT = "tpl.searchResult";
	public static final String STATIS_SEARCH = "tpl.statisSearch";

	@Autowired
	private ModelService modelService;

	@RequestMapping(value = { "/query.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String query(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String url = URLTools.getUrlFromParamter(request);
		return "redirect:search" + url + ".jsp";
	}

	@RequestMapping(value = { "/search-{q}.jsp" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String search(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws UnsupportedEncodingException {
		Integer pageNo = URLTools.getPageNo(request);
		Map<String, String> map = URLTools.getAllParamter(request);
		model.addAllAttributes(map);
		String mId = (String) map.get("mId");
		return searchpage(pageNo, mId, request, response, model);
	}

	private String searchpage(Integer page, String mId, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		ViewTools.frontData(request, model, site);
		ViewTools.frontPageData(request, model, page);
		if (!StringUtils.isBlank(mId)) {
			Integer modelId = Integer.valueOf(Integer.parseInt(mId));
			Model m = this.modelService.findById(modelId);
			if ((m != null) && (!StringUtils.isBlank(m.getTplSearch()))) {
				String root = site.getSolutionPath();
				return root + m.getTplSearch();
			}
		}
		return ViewTools.getTplPath(request, site.getSolutionPath(), "extrafunc/search", "tpl.searchResult");
	}

	@RequestMapping({ "/statisSearch.jsp" })
	public String countStatis(Integer cid, Integer did, String start, String end, HttpServletRequest request, ModelMap model) {
		return countStatisPage(Integer.valueOf(1), cid, did, start, end, request, model);
	}

	@RequestMapping({ "/statisSearch_{page:[0-9]+}.jsp" })
	public String countStatisPage(@PathVariable Integer page, Integer cid, Integer did, String start, String end, HttpServletRequest request, ModelMap model) {
		Site site = ContextTools.getSite(request);
		model.addAttribute("cid", cid);
		model.addAttribute("did", did);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		ViewTools.frontData(request, model, site);
		ViewTools.frontPageData(request, model, page);
		return ViewTools.getTplPath(request, site.getSolutionPath(), "extrafunc/search", "tpl.statisSearch");
	}
}
