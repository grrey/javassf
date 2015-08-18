package com.portal.datacenter.operatedata.action;

import com.javassf.basic.utils.ResponseUtils;
import com.portal.datacenter.operatedata.entity.Log;
import com.portal.datacenter.operatedata.service.LogService;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.utils.ContextTools;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

@Controller
public class LogAct {
	private static final Logger log = LoggerFactory.getLogger(LogAct.class);

	@Autowired
	private LogService manager;

	@RequiresPermissions({ "admin:log:list_operating" })
	@RequestMapping({ "/log/v_list_operating.do" })
	public String listOperating() {
		return "dataCenter/logData/operaLog";
	}

	@RequiresPermissions({ "admin:log:list_success" })
	@RequestMapping({ "/log/v_list_success.do" })
	public String listLoginSuccess() {
		return "dataCenter/logData/sloginLog";
	}

	@RequiresPermissions({ "admin:log:list_failure" })
	@RequestMapping({ "/log/v_list_failure.do" })
	public String listLoginFailure() {
		return "dataCenter/logData/floginLog";
	}

	@RequestMapping({ "/operating/jsonData.do" })
	public String operatingPageByJosn(String queryUsername, String queryTitle, String queryIp, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Site site = ContextTools.getSite(request);
		Page p = this.manager.getPage(Integer.valueOf(3), site.getId(), queryUsername, queryTitle, queryIp, page.intValue(), pagesize.intValue());
		model.addAttribute("p", p);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "dataCenter/logData/opdatatree";
	}

	@RequiresPermissions({ "admin:operating:delete" })
	@RequestMapping({ "/operating/o_ajax_delete.do" })
	public void deleteOperating(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		Log[] beans = this.manager.deleteByIds(ids);
		for (Log bean : beans) {
			log.info("delete Log id={}", bean.getId());
		}
		json.put("success", true);
		json.put("status", 1);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequiresPermissions({ "admin:operating:clear" })
	@RequestMapping({ "/operating/o_ajax_clear.do" })
	public void clearOperating(Integer days, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		Site site = ContextTools.getSite(request);
		this.manager.deleteBatch(Integer.valueOf(3), site.getId(), days);
		json.put("success", true);
		json.put("status", 1);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequestMapping({ "/logsuccess/jsonData.do" })
	public String logsuccessPageByJosn(String queryUsername, String queryTitle, String queryIp, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Page p = this.manager.getPage(Integer.valueOf(1), null, queryUsername, queryTitle, queryIp, page.intValue(), pagesize.intValue());
		model.addAttribute("p", p);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "dataCenter/logData/sdatatree";
	}

	@RequiresPermissions({ "admin:logsuccess:delete" })
	@RequestMapping({ "/logsuccess/o_ajax_delete.do" })
	public void deleteLogsuccess(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		Log[] beans = this.manager.deleteByIds(ids);
		for (Log bean : beans) {
			log.info("delete Log id={}", bean.getId());
		}
		json.put("success", true);
		json.put("status", 1);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequiresPermissions({ "admin:logsuccess:clear" })
	@RequestMapping({ "/logsuccess/o_ajax_clear.do" })
	public void clearLogsuccess(Integer days, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		if (days == null) {
			days = Integer.valueOf(0);
		}
		this.manager.deleteBatch(Integer.valueOf(1), null, days);
		json.put("success", true);
		json.put("status", 1);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequestMapping({ "/logfailure/jsonData.do" })
	public String logfailurePageByJosn(String queryUsername, String queryTitle, String queryIp, Integer page, Integer pagesize, HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Page p = this.manager.getPage(Integer.valueOf(2), null, null, queryTitle, queryIp, page.intValue(), pagesize.intValue());
		model.addAttribute("p", p);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "dataCenter/logData/fdatatree";
	}

	@RequiresPermissions({ "admin:logfailure:delete" })
	@RequestMapping({ "/logfailure/o_ajax_delete.do" })
	public void deleteLogfailure(Integer[] ids, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		Log[] beans = this.manager.deleteByIds(ids);
		for (Log bean : beans) {
			log.info("delete Log id={}", bean.getId());
		}
		json.put("success", true);
		json.put("status", 1);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequiresPermissions({ "admin:logfailure:clear" })
	@RequestMapping({ "/logfailure/o_ajax_clear.do" })
	public void clearLogfailure(Integer days, HttpServletRequest request, HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		if (days == null) {
			days = Integer.valueOf(0);
		}
		this.manager.deleteBatch(Integer.valueOf(2), null, days);
		json.put("success", true);
		json.put("status", 1);
		ResponseUtils.renderJson(response, json.toString());
	}
}
