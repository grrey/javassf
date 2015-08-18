package com.portal.doccenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.doccenter.entity.ArticleTxt;

public abstract interface ArticleTxtDao extends QueryDao<ArticleTxt>
{
  public abstract int delDocByInputUser(Integer paramInteger);

  public abstract int delDocBySite(Integer paramInteger);
}


 
 
 