package com.portal.datacenter.commdata.dao.impl;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.portal.datacenter.commdata.dao.EconomyTypeDao;
import com.portal.datacenter.commdata.entity.EconomyType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class EconomyTypeDaoImpl extends QueryDaoImpl<EconomyType, Integer> implements EconomyTypeDao {
	public Page<EconomyType> getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		crit.addOrder(Order.asc("code"));
		return findByCriteria(crit, pageNo, pageSize);
	}

	public List<EconomyType> getEconomyTypeList() {
		Criteria crit = createCriteria();
		crit.addOrder(Order.asc("code"));
		return findByCriteria(crit);
	}

	public EconomyType findByCode(String code) {
		Criteria crit = createCriteria();
		crit.add(Restrictions.eq("code", code));
		return (EconomyType) findUnique(crit);
	}

	protected Class<EconomyType> getEntityClass() {
		return EconomyType.class;
	}
}
