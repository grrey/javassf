package com.portal.sysmgr.dao.impl;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.portal.sysmgr.dao.SiteDao;
import com.portal.sysmgr.entity.Site;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class SiteDaoImpl extends QueryDaoImpl<Site, Integer> implements SiteDao {
	public List<Site> getList(boolean cacheable) {
		Criteria crit = createCriteria();
		crit.addOrder(Order.asc("id"));
		crit.setCacheable(cacheable);
		return findByCriteria(crit);
	}

	public Site findByDomain(String domain, boolean cacheable) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("domain", domain));
		crit.setCacheable(cacheable);
		return (Site) findUnique(crit);
	}

	protected Class<Site> getEntityClass() {
		return Site.class;
	}
}
