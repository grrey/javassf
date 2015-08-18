package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.Advert;
import com.portal.sysmgr.entity.Site;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface AdvertService
{
  public abstract Page<Advert> getPage(Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List<Advert> getListByTag(Integer paramInteger1, Integer paramInteger2);

  public abstract Advert findById(Integer paramInteger);

  public abstract Advert save(Advert paramAdvert, Integer paramInteger, boolean paramBoolean, Site paramSite);

  public abstract Advert update(Advert paramAdvert, Integer paramInteger, boolean paramBoolean);

  public abstract int deleteBySlotId(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract Advert deleteById(Integer paramInteger);

  public abstract Advert[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 