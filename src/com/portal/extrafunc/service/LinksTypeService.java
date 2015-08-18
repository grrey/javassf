package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.LinksType;
import com.portal.sysmgr.entity.Site;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface LinksTypeService
{
  public abstract Page<LinksType> getPage(int paramInt1, int paramInt2);

  public abstract LinksType findById(Integer paramInteger);

  public abstract List<LinksType> getList(Integer paramInteger, String paramString1, String paramString2);

  public abstract LinksType save(LinksType paramLinksType, Site paramSite);

  public abstract LinksType update(LinksType paramLinksType);

  public abstract LinksType deleteById(Integer paramInteger);

  public abstract LinksType[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 