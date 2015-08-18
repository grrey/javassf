package com.portal.datacenter.commdata.dao.impl;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.portal.datacenter.commdata.dao.MetierDao;
import com.portal.datacenter.commdata.entity.Metier;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class MetierDaoImpl extends QueryDaoImpl<Metier, Integer> implements MetierDao {
	public Page<Metier> getPage(String key, int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		if (!StringUtils.isBlank(key)) {
			crit.add(Restrictions.or(Restrictions.like("code", "%" + key + "%"), Restrictions.like("name", "%" + key + "%")));
		}
		crit.addOrder(Order.asc("code"));
		return findByCriteria(crit, pageNo, pageSize);
	}

	public List<Metier> getMetierList(Integer id) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.isNull("parent"));
		if (id != null) {
			crit.add(Restrictions.ne("id", id));
		}
		crit.addOrder(Order.asc("code"));
		return findByCriteria(crit);
	}

	public List<Metier> getMetierChild(Integer id) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("parent.id", id));
		crit.addOrder(Order.asc("code"));
		return findByCriteria(crit);
	}

	public Metier findByCode(String code) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("code", code));
		return (Metier) findUnique(crit);
	}

	protected Class<Metier> getEntityClass() {
		return Metier.class;
	}
}
