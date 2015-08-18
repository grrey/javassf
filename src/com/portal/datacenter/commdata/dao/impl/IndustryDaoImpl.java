package com.portal.datacenter.commdata.dao.impl;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.portal.datacenter.commdata.dao.IndustryDao;
import com.portal.datacenter.commdata.entity.Industry;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class IndustryDaoImpl extends QueryDaoImpl<Industry, Integer> implements IndustryDao {
	public Page<Industry> getPage(String key, int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		if (!StringUtils.isBlank(key)) {
			crit.add(Restrictions.or(Restrictions.like("code", "%" + key + "%"), Restrictions.like("name", "%" + key + "%")));
		}
		crit.addOrder(Order.asc("code"));
		return findByCriteria(crit, pageNo, pageSize);
	}

	public List<Industry> getIndustryList(Integer id) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.isNull("parent"));
		if (id != null) {
			crit.add(Restrictions.ne("id", id));
		}
		crit.addOrder(Order.asc("code"));
		return findByCriteria(crit);
	}

	public List<Industry> getIndustryChild(Integer id) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("parent.id", id));
		crit.addOrder(Order.asc("code"));
		return findByCriteria(crit);
	}

	public Industry findByCode(String code) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("code", code));
		return (Industry) findUnique(crit);
	}

	protected Class<Industry> getEntityClass() {
		return Industry.class;
	}
}
