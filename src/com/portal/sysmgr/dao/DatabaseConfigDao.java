package com.portal.sysmgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.sysmgr.entity.DatabaseConfig;

public abstract interface DatabaseConfigDao extends QueryDao<DatabaseConfig>
{
  public abstract DatabaseConfig findUnique();
}


 
 
 