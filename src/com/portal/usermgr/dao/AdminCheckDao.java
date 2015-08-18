package com.portal.usermgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.usermgr.entity.AdminCheck;

public abstract interface AdminCheckDao extends QueryDao<AdminCheck>
{
  public abstract int deleteBySiteId(Integer paramInteger);
}


 
 
 