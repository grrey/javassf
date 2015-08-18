package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.Advert;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface AdvertDao extends QueryDao<Advert>
{
  public abstract Page<Advert> getPage(Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List<Advert> getListByTag(Integer paramInteger1, Integer paramInteger2);

  public abstract int deleteBySlotId(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);
}


 
 
 