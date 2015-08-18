package com.portal.sysmgr.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class URLTools {
	public static String getUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			uri = uri + "?" + queryString;
		}
		return uri;
	}

	public static Integer getPageNo(HttpServletRequest request) {
		String uri = request.getRequestURI();
		int i = uri.lastIndexOf(".");
		int j = uri.lastIndexOf("_");
		if ((i > -1) && (j > -1) && (i > j)) {
			String pageStr = uri.substring(j + 1, i);
			return Integer.valueOf(pageStr);
		}
		return Integer.valueOf(1);
	}

	public static String getUrlFromParamter(HttpServletRequest request) throws UnsupportedEncodingException {
		Map<String, String[]> map = request.getParameterMap();
		StringBuilder sb = new StringBuilder("-");
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			sb.append((String) entry.getKey());
			sb.append("-");
			String value = URLEncoder.encode(((String[]) entry.getValue())[0], "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
			sb.append(value);
			sb.append("-");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public static Map<String, String> getAllParamter(HttpServletRequest request) throws UnsupportedEncodingException {
		String uri = request.getRequestURI();
		int i = uri.indexOf("-");
		int j = uri.lastIndexOf("_");
		if ((j == -1) || (j < i)) {
			j = uri.lastIndexOf(".");
		}
		Map map = new HashMap();
		if ((i > -1) && (j > -1) && (j > i)) {
			String[] paramters = uri.substring(i + 1, j).split("-");

			for (int l = 0; l < paramters.length - 1; l++) {
				if ((l % 2 != 0) || (!StringUtils.isNotBlank(paramters[l])))
					continue;
				String value = URLDecoder.decode(paramters[(l + 1)], "UTF-8");
				value = URLDecoder.decode(value, "UTF-8");
				map.put(paramters[l], value);
			}

			return map;
		}
		return null;
	}

	public static String getUrlChanged(HttpServletRequest request, boolean encode) {
		String uri = request.getRequestURI();
		String url = getUrl(request);
		if (encode) {
			uri = uri.replace(".jsp", ".html");
			uri = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
			try {
				uri = URLEncoder.encode(uri, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			url = url.substring(0, url.lastIndexOf("/") + 1);
			url = url + uri;
		} else {
			url = url.replace(".jsp", ".html");
		}
		return url;
	}
}
