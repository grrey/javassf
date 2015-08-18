package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.Links;
import com.portal.sysmgr.entity.Site;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface LinksService
{
  public abstract Page<Links> getPage(int paramInt1, int paramInt2);

  public abstract Links findById(Integer paramInteger);

  public abstract Page<Links> getPage(Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List<Links> getListByTag(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);

  public abstract Links save(Links paramLinks, Integer paramInteger, Site paramSite);

  public abstract Links update(Links paramLinks, Integer paramInteger);

  public abstract Links deleteById(Integer paramInteger);

  public abstract Links[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 