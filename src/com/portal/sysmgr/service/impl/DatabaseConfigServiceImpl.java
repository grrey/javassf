package com.portal.sysmgr.service.impl;

import com.portal.sysmgr.dao.DatabaseConfigDao;
import com.portal.sysmgr.entity.DatabaseConfig;
import com.portal.sysmgr.service.DatabaseConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DatabaseConfigServiceImpl
  implements DatabaseConfigService
{
  private DatabaseConfigDao dao;

  @Transactional(readOnly=true)
  public Page<DatabaseConfig> getPage(int pageNo, int pageSize)
  {
    return this.dao.getPage(pageNo, pageSize);
  }
  @Transactional(readOnly=true)
  public DatabaseConfig findUnique() {
    return this.dao.findUnique();
  }
  @Transactional(readOnly=true)
  public DatabaseConfig findById(Integer id) {
    DatabaseConfig entity = (DatabaseConfig)this.dao.findById(id);
    return entity;
  }

  public DatabaseConfig save(DatabaseConfig bean) {
    this.dao.save(bean);
    return bean;
  }

  public DatabaseConfig update(DatabaseConfig bean) {
    if (bean.getId() != null) {
      bean = (DatabaseConfig)this.dao.update(bean);
    } else {
      bean.init();
      save(bean);
    }
    return bean;
  }

  public DatabaseConfig deleteById(Integer id) {
    DatabaseConfig bean = (DatabaseConfig)this.dao.deleteById(id);
    return bean;
  }

  public DatabaseConfig[] deleteByIds(Integer[] ids) {
    DatabaseConfig[] beans = new DatabaseConfig[ids.length];
    int i = 0; for (int len = ids.length; i < len; i++) {
      beans[i] = deleteById(ids[i]);
    }
    return beans;
  }

  @Autowired
  public void setDao(DatabaseConfigDao dao)
  {
    this.dao = dao;
  }
}




