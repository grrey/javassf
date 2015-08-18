package com.portal.sysmgr.service;

import com.portal.sysmgr.entity.DatabaseConfig;
import org.springframework.data.domain.Page;

public abstract interface DatabaseConfigService
{
  public abstract Page<DatabaseConfig> getPage(int paramInt1, int paramInt2);

  public abstract DatabaseConfig findUnique();

  public abstract DatabaseConfig findById(Integer paramInteger);

  public abstract DatabaseConfig save(DatabaseConfig paramDatabaseConfig);

  public abstract DatabaseConfig update(DatabaseConfig paramDatabaseConfig);

  public abstract DatabaseConfig deleteById(Integer paramInteger);

  public abstract DatabaseConfig[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 