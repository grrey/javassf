package com.portal.doccenter.service;

import com.portal.doccenter.entity.Model;
import java.util.List;

public abstract interface ModelService
{
  public abstract List<Model> getList(boolean paramBoolean, String paramString1, String paramString2);

  public abstract Model getDefModel();

  public abstract Model findById(Integer paramInteger);

  public abstract Model save(Model paramModel);

  public abstract Model update(Model paramModel);

  public abstract Model deleteById(Integer paramInteger);

  public abstract Model[] deleteByIds(Integer[] paramArrayOfInteger);

  public abstract List<Model> getModelList(List<Integer> paramList);

  public abstract Model[] updatePriority(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Boolean[] paramArrayOfBoolean, Integer paramInteger);
}


 
 
 