package com.portal.doccenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.doccenter.entity.Model;
import java.util.List;

public abstract interface ModelDao extends QueryDao<Model>
{
  public abstract List<Model> getList(boolean paramBoolean, String paramString1, String paramString2);

  public abstract Model getDefModel();
}


 
 
 