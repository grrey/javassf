package com.portal.doccenter.service;

import com.portal.doccenter.entity.ModelField;
import java.util.List;

public abstract interface ModelFieldService
{
  public abstract List<ModelField> getList(Integer paramInteger, boolean paramBoolean, String paramString1, String paramString2);

  public abstract ModelField findById(Integer paramInteger);

  public abstract ModelField save(ModelField paramModelField);

  public abstract ModelField save(ModelField paramModelField, Integer paramInteger);

  public abstract void saveList(List<ModelField> paramList);

  public abstract void updatePriority(Integer paramInteger1, Integer paramInteger2);

  public abstract ModelField update(ModelField paramModelField);

  public abstract ModelField deleteById(Integer paramInteger);

  public abstract ModelField[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 