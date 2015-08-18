package com.portal.govcenter.service;

import com.portal.govcenter.entity.Mailbox;
import com.portal.govcenter.entity.MailboxExt;
import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.User;
import org.springframework.data.domain.Page;

public abstract interface MailboxService
{
  public abstract Page<Mailbox> getPage(String paramString, Integer paramInteger1, User paramUser, Integer paramInteger2, int paramInt1, int paramInt2);

  public abstract Page<Mailbox> getPageByTag(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, int paramInt1, int paramInt2);

  public abstract Mailbox findById(Integer paramInteger);

  public abstract Mailbox save(Mailbox paramMailbox, MailboxExt paramMailboxExt, Site paramSite, Integer paramInteger1, Integer paramInteger2);

  public abstract Mailbox update(Mailbox paramMailbox, MailboxExt paramMailboxExt, User paramUser, Integer paramInteger);

  public abstract Mailbox showMailbox(Integer paramInteger);

  public abstract Mailbox forwardMailbox(Integer paramInteger1, Integer paramInteger2);

  public abstract Mailbox backMailbox(Integer paramInteger);

  public abstract Mailbox deleteById(Integer paramInteger);

  public abstract Mailbox[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 