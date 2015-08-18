package com.portal.datacenter.docdata.dao.impl;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.portal.datacenter.docdata.dao.SensitivityDao;
import com.portal.datacenter.docdata.entity.Sensitivity;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository
public class SensitivityDaoImpl extends QueryDaoImpl<Sensitivity, Integer> implements SensitivityDao {
	public List<Sensitivity> getList(boolean cacheable, String sortname, String sortorder) {
		Criteria crit = createCriteria();
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

	protected Class<Sensitivity> getEntityClass() {
		return Sensitivity.class;
	}
}
