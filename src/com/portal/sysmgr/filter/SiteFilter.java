package com.portal.sysmgr.filter;

import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.service.SiteService;
import com.portal.sysmgr.utils.ContextTools;
import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 站点过滤器：用于存储站点信息到request中
 */
public class SiteFilter implements Filter {
	private SiteService siteService;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Site site = getSite(request);
		ContextTools.setSite(site);
		ContextTools.setSite(request, site);
		chain.doFilter(request, response);
		ContextTools.resetSite();
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	private Site getSite(ServletRequest request) {
		Site site = getByDomain(request);
		if (site == null) {
			site = getByDefault();
		}
		if (site == null) {
			throw new RuntimeException("site not found!");
		}
		return site;
	}

	private Site getByDomain(ServletRequest request) {
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
}
