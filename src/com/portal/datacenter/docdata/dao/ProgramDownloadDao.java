package com.portal.datacenter.docdata.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.datacenter.docdata.entity.ProgramDownload;
import org.springframework.data.domain.Page;

public abstract interface ProgramDownloadDao extends QueryDao<ProgramDownload> {
	public abstract Page<ProgramDownload> getPage(int paramInt1, int paramInt2);

	public abstract ProgramDownload findUnique();
}
