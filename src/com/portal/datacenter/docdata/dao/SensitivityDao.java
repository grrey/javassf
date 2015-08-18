package com.portal.datacenter.docdata.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.datacenter.docdata.entity.Sensitivity;
import java.util.List;

public abstract interface SensitivityDao extends QueryDao<Sensitivity> {
	public abstract List<Sensitivity> getList(boolean paramBoolean, String paramString1, String paramString2);
}
