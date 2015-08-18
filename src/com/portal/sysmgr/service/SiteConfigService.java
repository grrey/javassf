package com.portal.sysmgr.service;

import com.portal.sysmgr.entity.Site;
import com.portal.sysmgr.entity.SiteConfig;
import org.springframework.data.domain.Page;

public abstract interface SiteConfigService
{
  public abstract Page<SiteConfig> getPage(int paramInt1, int paramInt2);

  public abstract SiteConfig findById(Integer paramInteger);

  public abstract SiteConfig save(SiteConfig paramSiteConfig);

  public abstract SiteConfig update(SiteConfig paramSiteConfig, Site paramSite);

  public abstract SiteConfig deleteById(Integer paramInteger);

  public abstract SiteConfig[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 