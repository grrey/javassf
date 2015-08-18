package com.portal.usermgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.usermgr.entity.SiteMessageStatus;
import org.springframework.data.domain.Page;

public abstract interface SiteMessageStatusDao extends QueryDao<SiteMessageStatus>
{
  public abstract Page<SiteMessageStatus> getPage(int paramInt1, int paramInt2);

  public abstract SiteMessageStatus findByRecive(Integer paramInteger1, Integer paramInteger2);
}


 
 
 