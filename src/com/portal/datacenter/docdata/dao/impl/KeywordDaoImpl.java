package com.portal.datacenter.docdata.dao.impl;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.portal.datacenter.docdata.dao.KeywordDao;
import com.portal.datacenter.docdata.entity.Keyword;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class KeywordDaoImpl extends QueryDaoImpl<Keyword, Integer> implements KeywordDao {
	public List<Keyword> getList(Integer siteId, boolean enable, boolean cacheable, String sortname, String sortorder) {
		Criteria crit = createCriteria();
		if (siteId != null) {
			crit.add(Restrictions.eq("site.id", siteId));
		}
		if (enable) {
			crit.add(Restrictions.eq("enable", Boolean.valueOf(true)));
		}
		if (!StringUtils.isBlank(sortname)) {
			if ("asc".equals(sortorder))
				crit.addOrder(Order.asc(sortname));
			else
				crit.addOrder(Order.desc(sortname));
		} else {
			crit.addOrder(Order.desc("id"));
		}
		crit.setCacheable(cacheable);
		return findByCriteria(crit);
	}

	protected Class<Keyword> getEntityClass() {
		return Keyword.class;
	}
}
