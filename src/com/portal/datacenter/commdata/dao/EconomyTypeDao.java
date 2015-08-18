package com.portal.datacenter.commdata.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.datacenter.commdata.entity.EconomyType;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface EconomyTypeDao extends QueryDao<EconomyType>
{
  public abstract Page<EconomyType> getPage(int paramInt1, int paramInt2);

  public abstract List<EconomyType> getEconomyTypeList();

  public abstract EconomyType findByCode(String paramString);
}
