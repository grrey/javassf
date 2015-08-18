package com.portal.govcenter.service;

import com.portal.govcenter.entity.MailboxType;
import com.portal.sysmgr.entity.Site;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface MailboxTypeService
{
  public abstract Page<MailboxType> getPage(int paramInt1, int paramInt2);

  public abstract List<MailboxType> getList(Integer paramInteger);

  public abstract MailboxType findById(Integer paramInteger);

  public abstract MailboxType save(MailboxType paramMailboxType, Site paramSite);

  public abstract MailboxType update(MailboxType paramMailboxType);

  public abstract MailboxType deleteById(Integer paramInteger);

  public abstract MailboxType[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 