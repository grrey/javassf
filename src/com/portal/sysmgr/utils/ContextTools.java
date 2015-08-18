package com.portal.sysmgr.utils;

import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.entity.SiteConfig;
import com.portal.usermgr.entity.User;
import java.util.UUID;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.WebUtils;
/**
 * 
 * 该工具类主要作用是：将对应的 用户信息、站点等存储到request上下文中
 * 
 */
public class ContextTools {
	public static final String USER_KEY = "_request_user_key";//用户信息key
	public static final String SITE_KEY = "_request_site_key";//站点key
	public static final String SITE_CONFIG_KEY = "_request_config_key";
	public static final String IDENTITY_COOKIE_NAME = "_javassf";
	/**
	 * 采用ThreadLocal 解决线程安全问题
	 */
	private static ThreadLocal<Site> siteHolder = new ThreadLocal();

	private static ThreadLocal<Integer> totalpageHolder = new ThreadLocal();

	public static User getUser(ServletRequest request) {
		return (User) request.getAttribute("_request_user_key");
	}

	public static Integer getUserId(ServletRequest request) {
		if (getUser(request) != null) {
			return getUser(request).getId();
		}
		return null;
	}
	
	public static void setUser(ServletRequest request, User user) {
		request.setAttribute("_request_user_key", user);
	}

	public static Site getSite(ServletRequest request) {
		return (Site) request.getAttribute("_request_site_key");
	}

	public static Integer getSiteId(ServletRequest request) {
		return getSite(request).getId();
	}

	public static SiteConfig getSiteConfig(ServletRequest request) {
		return (SiteConfig) request.getAttribute("_request_config_key");
	}

	public static void setSite(ServletRequest request, Site site) {
		request.setAttribute("_request_site_key", site);
	}

	public static void setSiteConfig(ServletRequest request, SiteConfig siteConfig) {
		request.setAttribute("_request_config_key", siteConfig);
	}

	public static String getIdentityCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = WebUtils.getCookie(request, "_javassf");
		String value;
		// String value;
		if ((cookie != null) && (StringUtils.isNotBlank(cookie.getValue()))) {
			value = cookie.getValue();
		} else {
			value = UUID.randomUUID().toString();
			value = StringUtils.remove(value, '-');
			cookie = new Cookie("_javassf", value);
			String ctx = request.getContextPath();
			if (StringUtils.isBlank(ctx)) {
				ctx = "/";
			}
			cookie.setPath(ctx);
			cookie.setMaxAge(2147483647);
			response.addCookie(cookie);
		}
		return value;
	}

	public static void setSite(Site site) {
		siteHolder.set(site);
	}

	public static Site getSite() {
		return (Site) siteHolder.get();
	}

	public static void resetSite() {
		siteHolder.remove();
	}

	public static void setTotalPages(Integer totalPages) {
		totalpageHolder.set(totalPages);
	}

	public static Integer getTotalPages() {
		return (Integer) totalpageHolder.get();
	}

	public static void resetTotalPages() {
		totalpageHolder.remove();
	}
}
