package com.portal.sysmgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.sysmgr.entity.Site;
import java.util.List;

public abstract interface SiteDao extends QueryDao<Site> {
	public abstract List<Site> getList(boolean paramBoolean);

	public abstract Site findByDomain(String paramString, boolean paramBoolean);
}