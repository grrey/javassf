package com.portal.sysmgr.action.tag;

import com.portal.sysmgr.utils.TagModelTools;
import com.portal.sysmgr.utils.ViewTools;
import freemarker.core.Environment;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class PageModel implements TemplateMethodModelEx {
	public Object exec(List args) throws TemplateModelException {
		Integer page = null;
		if (args.size() > 0) {
			TemplateModel arg0 = (TemplateModel) args.get(0);
			page = TagModelTools.getInt("arg0", arg0);
		}
		if ((page == null) || (page.intValue() < 1)) {
			page = Integer.valueOf(1);
		}
		Environment env = Environment.getCurrentEnvironment();
		String url = ViewTools.getUrl(env);
		if (StringUtils.isBlank(url)) {
			return "";
		}
		String queryString = null;
		String uri = url;
		int pos = url.indexOf("?");
		if (pos != -1) {
			queryString = url.substring(pos + 1);
			uri = url.substring(0, pos);
		}
		uri = pageUrl(uri, page);
		String result;
		// String result;
		if (StringUtils.isNotBlank(queryString))
			result = uri + "?" + queryString;
		else {
			result = uri;
		}
		return result;
	}

	private String pageUrl(String uri, Integer page) {
		if (StringUtils.isBlank(uri)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		if (uri.lastIndexOf("_") > -1) {
			sb.append(uri.substring(0, uri.lastIndexOf("_")));
		} else if (uri.lastIndexOf(".") > -1)
			sb.append(uri.substring(0, uri.lastIndexOf(".")));
		else {
			sb.append(uri.substring(0, uri.lastIndexOf("/")));
		}

		if (page.intValue() > 1) {
			sb.append("_").append(page);
		}
		if (uri.lastIndexOf(".") > -1)
			sb.append(uri.substring(uri.lastIndexOf("."), uri.length()));
		else {
			sb.append(uri.substring(uri.lastIndexOf("/"), uri.length()));
		}
		return sb.toString();
	}
}