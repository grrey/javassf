package com.portal.govcenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.govcenter.entity.MailboxType;
import java.util.List;

public abstract interface MailboxTypeDao extends QueryDao<MailboxType>
{
  public abstract List<MailboxType> getList(Integer paramInteger);
}


 
 
 