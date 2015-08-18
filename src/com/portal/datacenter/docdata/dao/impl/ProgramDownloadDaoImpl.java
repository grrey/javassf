package com.portal.datacenter.docdata.dao.impl;

import com.javassf.basic.hibernate4.QueryDaoImpl;
import com.portal.datacenter.docdata.dao.ProgramDownloadDao;
import com.portal.datacenter.docdata.entity.ProgramDownload;
import org.hibernate.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public class ProgramDownloadDaoImpl extends QueryDaoImpl<ProgramDownload, Integer> implements ProgramDownloadDao {
	public Page<ProgramDownload> getPage(int pageNo, int pageSize) {
		Criteria crit = createCriteria();
		return findByCriteria(crit, pageNo, pageSize);
	}

	public ProgramDownload findUnique() {
		Criteria crit = createCriteria();
		return (ProgramDownload) findUnique(crit);
	}

	protected Class<ProgramDownload> getEntityClass() {
		return ProgramDownload.class;
	}
}
