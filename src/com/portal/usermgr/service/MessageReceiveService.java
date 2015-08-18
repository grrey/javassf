package com.portal.usermgr.service;

import com.portal.usermgr.entity.MessageReceive;
import com.portal.usermgr.entity.SiteMessage;
import org.springframework.data.domain.Page;

public abstract interface MessageReceiveService
{
  public abstract Page<MessageReceive> getPage(int paramInt1, int paramInt2);

  public abstract MessageReceive findById(Integer paramInteger);

  public abstract MessageReceive save(SiteMessage paramSiteMessage, Integer[] paramArrayOfInteger);

  public abstract MessageReceive update(MessageReceive paramMessageReceive);

  public abstract MessageReceive deleteById(Integer paramInteger);

  public abstract MessageReceive[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 