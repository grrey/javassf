package com.portal.datacenter.commdata.dao.impl;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.portal.datacenter.commdata.dao.ForeignLangDao;
import com.portal.datacenter.commdata.entity.ForeignLang;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class ForeignLangDaoImpl extends QueryDaoImpl<ForeignLang, Integer> implements ForeignLangDao {
	public Page<ForeignLang> getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		crit.addOrder(Order.asc("code"));
		return findByCriteria(crit, pageNo, pageSize);
	}

	public List<ForeignLang> getForeignLangList() {
		Criteria crit = createCriteria();
		crit.addOrder(Order.asc("code"));
		return findByCriteria(crit);
	}

	protected Class<ForeignLang> getEntityClass() {
		return ForeignLang.class;
	}
}
