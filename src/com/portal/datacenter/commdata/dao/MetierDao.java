package com.portal.datacenter.commdata.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.datacenter.commdata.entity.Metier;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface MetierDao extends QueryDao<Metier>
{
  public abstract Page<Metier> getPage(String paramString, int paramInt1, int paramInt2);

  public abstract List<Metier> getMetierList(Integer paramInteger);

  public abstract List<Metier> getMetierChild(Integer paramInteger);

  public abstract Metier findByCode(String paramString);
}
