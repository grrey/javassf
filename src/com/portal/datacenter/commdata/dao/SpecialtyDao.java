package com.portal.datacenter.commdata.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.datacenter.commdata.entity.Specialty;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface SpecialtyDao extends QueryDao<Specialty>
{
  public abstract Page<Specialty> getPage(String paramString, int paramInt1, int paramInt2);

  public abstract List<Specialty> getSpecialtyList(Integer paramInteger);

  public abstract List<Specialty> getSpecialtyChild(Integer paramInteger);

  public abstract Specialty findByCode(String paramString);
}
