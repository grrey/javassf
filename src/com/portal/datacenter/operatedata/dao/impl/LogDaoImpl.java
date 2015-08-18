package com.portal.datacenter.operatedata.dao.impl;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.javassf.basic.utils.ExecuteQueryUtils;
import com.portal.datacenter.operatedata.dao.LogDao;
import com.portal.datacenter.operatedata.entity.Log;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class LogDaoImpl extends QueryDaoImpl<Log, Integer> implements LogDao {
	public Page<Log> getPage(Integer category, Integer siteId, Integer userId, String title, String ip, int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		if (category != null) {
			crit.add(Restrictions.eq("category", category));
		}
		if (siteId != null) {
			crit.add(Restrictions.eq("site.id", siteId));
		}
		if (userId != null) {
			crit.add(Restrictions.eq("user.id", userId));
		}
		if (StringUtils.isNotBlank(title)) {
			crit.add(Restrictions.like("title", "%" + title + "%"));
		}
		if (StringUtils.isNotBlank(ip)) {
			crit.add(Restrictions.like("ip", ip + "%"));
		}
		crit.addOrder(Order.desc("id"));
		return findByCriteria(crit, pageNo, pageSize);
	}

	public int deleteBatch(Integer category, Integer siteId, Date before) {
		ExecuteQueryUtils eq = ExecuteQueryUtils.create("delete Log bean where 1=1");
		if (category != null) {
			eq.append(" and bean.category=:category");
			eq.setParameter("category", category);
		}
		if (siteId != null) {
			eq.append(" and bean.site.id=:siteId");
			eq.setParameter("siteId", siteId);
		}
		if (before != null) {
			eq.append(" and bean.time<=:before");
			eq.setParameter("before", before);
		}
		return executeQuery(eq);
	}

	protected Class<Log> getEntityClass() {
		return Log.class;
	}
}
