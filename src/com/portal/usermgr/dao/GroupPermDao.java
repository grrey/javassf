package com.portal.usermgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.usermgr.entity.GroupPerm;
import org.springframework.data.domain.Page;

public abstract interface GroupPermDao extends QueryDao<GroupPerm>
{
  public abstract Page<GroupPerm> getPage(int paramInt1, int paramInt2);
}


 
 
 