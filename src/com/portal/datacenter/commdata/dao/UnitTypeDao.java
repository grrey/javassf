package com.portal.datacenter.commdata.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.datacenter.commdata.entity.UnitType;
import org.springframework.data.domain.Page;

public abstract interface UnitTypeDao extends QueryDao<UnitType>
{
  public abstract Page<UnitType> getPage(int paramInt1, int paramInt2);
}

