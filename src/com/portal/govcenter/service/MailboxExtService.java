package com.portal.govcenter.service;

import com.portal.govcenter.entity.Mailbox;
import com.portal.govcenter.entity.MailboxExt;
import org.springframework.data.domain.Page;

public abstract interface MailboxExtService
{
  public abstract Page<MailboxExt> getPage(int paramInt1, int paramInt2);

  public abstract MailboxExt findById(Integer paramInteger);

  public abstract MailboxExt save(Mailbox paramMailbox, MailboxExt paramMailboxExt);

  public abstract MailboxExt update(Mailbox paramMailbox, MailboxExt paramMailboxExt);

  public abstract MailboxExt deleteById(Integer paramInteger);

  public abstract MailboxExt[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 