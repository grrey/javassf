package com.portal.usermgr.service;

import com.portal.usermgr.entity.SiteMessage;
import com.portal.usermgr.entity.User;
import org.springframework.data.domain.Page;

public abstract interface SiteMessageService
{
  public abstract Page<SiteMessage> getPage(int paramInt1, int paramInt2);

  public abstract Page<SiteMessage> getPageByTag(Integer paramInteger1, Integer paramInteger2, int paramInt1, int paramInt2);

  public abstract SiteMessage findById(Integer paramInteger);

  public abstract SiteMessage save(SiteMessage paramSiteMessage, Integer paramInteger, Integer[] paramArrayOfInteger);

  public abstract SiteMessage update(SiteMessage paramSiteMessage);

  public abstract SiteMessage deleteById(Integer paramInteger, User paramUser);

  public abstract SiteMessage deleteById(Integer paramInteger);

  public abstract SiteMessage[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 