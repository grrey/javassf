package com.portal.govcenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.govcenter.entity.Mailbox;
import org.springframework.data.domain.Page;

public abstract interface MailboxDao extends QueryDao<Mailbox>
{
  public abstract Page<Mailbox> getPage(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, int paramInt1, int paramInt2);

  public abstract Page<Mailbox> getPageByTag(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, int paramInt1, int paramInt2);
}


 
 
 