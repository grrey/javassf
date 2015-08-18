package com.portal.sysmgr.service;

import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.entity.ThirdpartyConfig;
import org.springframework.data.domain.Page;

public abstract interface ThirdpartyConfigService
{
  public abstract Page<ThirdpartyConfig> getPage(int paramInt1, int paramInt2);

  public abstract ThirdpartyConfig findById(Integer paramInteger);

  public abstract ThirdpartyConfig save(ThirdpartyConfig paramThirdpartyConfig);

  public abstract ThirdpartyConfig update(ThirdpartyConfig paramThirdpartyConfig, Site paramSite);

  public abstract ThirdpartyConfig deleteById(Integer paramInteger);

  public abstract ThirdpartyConfig[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 