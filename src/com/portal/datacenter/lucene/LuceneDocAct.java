package com.portal.datacenter.lucene;

import com.javassf.basic.utils.ResponseUtils;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.utils.ContextTools;
import java.io.IOException;
import java.util.Date;
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
public class LuceneDocAct {

	@Autowired
	private LuceneDocService luceneDocService;

	@RequiresPermissions({ "admin:lucene:index" })
	@RequestMapping({ "/lucene/v_index.do" })
	public String index(HttpServletRequest request, ModelMap model) {
		Site site = ContextTools.getSite(request);
		model.addAttribute("site", site);
		return "dataCenter/docData/lucene/index";
	}

	@RequiresPermissions({ "admin:lucene:create" })
	@RequestMapping({ "/lucene/o_create.do" })
	public void create(Integer channelId, Date startDate, Date endDate, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws JSONException {
		Site site = ContextTools.getSite(request);
		try {
			Integer docId = this.luceneDocService.createSearchIndex(site.getId(), channelId, startDate, endDate, null, Integer.valueOf(1000), true);
			JSONObject json = new JSONObject();
			while (docId != null) {
				docId = this.luceneDocService.createSearchIndex(site.getId(), channelId, startDate, endDate, docId, Integer.valueOf(1000), false);
			}
			json.put("success", true);
			ResponseUtils.renderJson(response, json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
