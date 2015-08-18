package com.portal.usermgr.service;

import com.portal.usermgr.entity.SiteMessage;
import com.portal.usermgr.entity.SiteMessageStatus;
import org.springframework.data.domain.Page;

public abstract interface SiteMessageStatusService
{
  public abstract Page<SiteMessageStatus> getPage(int paramInt1, int paramInt2);

  public abstract SiteMessageStatus findByRecive(Integer paramInteger1, Integer paramInteger2);

  public abstract SiteMessageStatus findById(Integer paramInteger);

  public abstract void save(SiteMessage paramSiteMessage, Integer[] paramArrayOfInteger);

  public abstract SiteMessageStatus update(SiteMessageStatus paramSiteMessageStatus);

  public abstract SiteMessageStatus deleteByReceive(Integer paramInteger1, Integer paramInteger2);

  public abstract SiteMessageStatus deleteById(Integer paramInteger);

  public abstract SiteMessageStatus[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 