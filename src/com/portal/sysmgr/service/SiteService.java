package com.portal.sysmgr.service;

import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.User;
import java.util.List;

public abstract interface SiteService
{
  public abstract List<Site> getList();

  public abstract List<Site> getListFromCache();

  public abstract Site findByDomain(String paramString, boolean paramBoolean);

  public abstract Site findById(Integer paramInteger);

  public abstract Site save(Site paramSite, User paramUser, Integer paramInteger, Integer[] paramArrayOfInteger);

  public abstract Site update(Site paramSite);

  public abstract void updateTplStyle(Integer paramInteger, String paramString);

  public abstract Site deleteById(Integer paramInteger);

  public abstract Site[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 