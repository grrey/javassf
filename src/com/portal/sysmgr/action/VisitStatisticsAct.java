package com.portal.sysmgr.action;

import com.javassf.basic.utils.ResponseUtils;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.service.VisitStatisticsService;
import com.portal.sysmgr.utils.ContextTools;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VisitStatisticsAct {

	@Autowired
	private VisitStatisticsService statisticsService;

	@RequestMapping({ "/views/jsonData.do" })
	public void dataPageByJosn(String datestr, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
		Site site = ContextTools.getSite(request);
		Long[] views = new Long[24];
		for (int i = 0; i < 24; i++) {
			views[i] = Long.valueOf(this.statisticsService.getStatisticsByHour(site.getId(), Integer.valueOf(i)));
		}
		JSONObject json = new JSONObject();
		json.put("data", views);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequestMapping({ "/views/monthData.do" })
	public void dataPageMonth(String datestr, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
		Site site = ContextTools.getSite(request);
		Long[] views = new Long[24];
		for (int i = 0; i < 24; i++) {
			views[i] = Long.valueOf(this.statisticsService.getStatisticsByHour(site.getId(), Integer.valueOf(i)));
		}
		JSONObject json = new JSONObject();
		json.put("data", views);
		ResponseUtils.renderJson(response, json.toString());
	}
}
