package com.portal.sysmgr.interceptor;

import com.javassf.basic.utils.StringBeanUtils;
import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.service.SiteService;
import com.portal.sysmgr.utils.ContextTools;
import com.portal.usermgr.entity.User;
import com.portal.usermgr.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

/**
 * 
 * 此拦截器主要作用是：存储站点、和用户信息到request作用域中和防止xss攻击和权限验证
 * 
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {
	private SiteService siteService;
	private UserService userService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 获取站点
		Site site = getSite(request, response);
		// 存储站点到request作用域中
		ContextTools.setSite(request, site);
		UrlPathHelper helper = new UrlPathHelper();
		String queryString = helper.getOriginatingQueryString(request);
		// 防止xss攻击
		if ((!StringUtils.isBlank(queryString)) && (StringBeanUtils.hasHtml(queryString))) {
			try {
				response.setContentType("text/html;charset=UTF-8");
				response.sendError(404);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * 权限验证
		 */
		Subject subject = SecurityUtils.getSubject();
		if ((subject.isAuthenticated()) || (subject.isRemembered())) {
			String username = (String) subject.getPrincipal();
			User user = this.userService.findByUsername(username);
			ContextTools.setUser(request, user);
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) throws Exception {
	}

	/**
	 * 获取对应站点，如果根据域名找不到对应站点，则默认获取数据库中第一个站点
	 * 
	 * @param request
	 * @param response
	 * @return Site
	 */
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

	/**
	 * 获取站点对象
	 * 
	 * @param request
	 * @return Site
	 */
	private Site getByDomain(HttpServletRequest request) {
		// 获取域名
		String domain = request.getServerName();
		if (!StringUtils.isBlank(domain)) {
			return this.siteService.findByDomain(domain, true);
		}
		return null;
	}

	/**
	 * 默认获取第一个站点
	 */
	private Site getByDefault() {
		List list = this.siteService.getListFromCache();
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
