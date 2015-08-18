package com.portal.datacenter.commdata.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.datacenter.commdata.entity.ForeignLang;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface ForeignLangDao extends QueryDao<ForeignLang>
{
  public abstract Page<ForeignLang> getPage(int paramInt1, int paramInt2);

  public abstract List<ForeignLang> getForeignLangList();
}
