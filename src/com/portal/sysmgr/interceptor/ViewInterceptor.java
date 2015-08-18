package com.portal.sysmgr.interceptor;

import com.javassf.basic.utils.StringBeanUtils;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.service.SiteService;
import com.portal.sysmgr.utils.ContextTools;
import com.portal.usermgr.entity.User;
import com.portal.usermgr.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

public class ViewInterceptor extends HandlerInterceptorAdapter {
	private SiteService siteService;
	private UserService userService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws ServletException {
		Site site = getSite(request, response);
		ContextTools.setSite(request, site);
		UrlPathHelper helper = new UrlPathHelper();
		String queryString = helper.getOriginatingQueryString(request);
		if ((!StringUtils.isBlank(queryString)) && (StringBeanUtils.hasHtml(queryString))) {
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.sendError(404);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			String username = (String) subject.getPrincipal();
			User user = this.userService.findByUsername(username);
			ContextTools.setUser(request, user);
		}
		return true;
	}

	private Site getSite(HttpServletRequest request, HttpServletResponse response) {
		Site site = getByDomain(request);
		if (site == null) {
			site = getByDefault();
		}
		if (site == null) {
			throw new RuntimeException("cannot get site!");
		}
		return site;
	}

	private Site getByDomain(HttpServletRequest request) {
		String domain = request.getServerName();
		if (!StringUtils.isBlank(domain)) {
			return this.siteService.findByDomain(domain, true);
		}
		return null;
	}

	private Site getByDefault() {
		List<Site> list = this.siteService.getListFromCache();
		if (list.size() > 0) {
			return (Site) list.get(0);
		}
		return null;
	}

	@Autowired
	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
