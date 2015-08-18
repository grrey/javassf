package com.javassf.basic.hibernate4;

import com.javassf.basic.utils.HibernateUtils;
import java.util.Collections;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.OrderEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class BaseQueryDaoImpl<T> {
	protected Logger log = LoggerFactory.getLogger(getClass());
	protected SessionFactory sessionFactory;
	
	 /**  
     * 根据Criteria 查询 且要求对象唯一.  
     *  
     * @return 符合条件的唯一对象.  
     */  
	protected T findUnique(Criteria crit) {
		crit.setMaxResults(1);
		return (T) crit.uniqueResult();
	}
	
	/**
	 * 查询实体类列表
	 * @param crit
	 * @return
	 */
	protected List<T> findByCriteria(Criteria crit) {
		return crit.list();
	}

	/**
	 * 分页查询
	 * @param crit
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	protected Page<T> findByCriteria(Criteria crit, int pageNo, int pageSize) {
		CriteriaImpl.OrderEntry[] orderEntries = HibernateUtils.getOrders(crit);
		crit = HibernateUtils.removeOrders(crit);
		Projection projection = HibernateUtils.getProjection(crit);
		//获取总数
		int totalCount = ((Number) crit.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		crit.setProjection(projection);
		if (projection == null) {
			crit.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		crit = HibernateUtils.addOrders(crit, orderEntries);
	    //分页查询  
		Pageable p = new PageRequest(pageNo - 1, pageSize);
		List<T> list;
		// List list;
		if (totalCount > p.getOffset()) {
			crit.setFirstResult(p.getOffset());
			crit.setMaxResults(p.getPageSize());
			list = crit.list();
		} else {
			list = Collections.emptyList();
		}
		Page<T> page = new PageImpl<T>(list, p, totalCount);
		return page;
	}

	//注入sessionFactory
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	/**
	 * 获取session
	 * @return
	 */
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
}