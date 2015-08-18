package com.portal.doccenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.doccenter.entity.ArticleExt;

public abstract interface ArticleExtDao extends QueryDao<ArticleExt>
{
  public abstract int delDocByInputUser(Integer paramInteger);

  public abstract int delDocBySite(Integer paramInteger);
}


 
 
 