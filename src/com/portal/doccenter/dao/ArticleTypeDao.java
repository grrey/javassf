package com.portal.doccenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.doccenter.entity.ArticleType;
import java.util.List;

public abstract interface ArticleTypeDao extends QueryDao<ArticleType>
{
  public abstract List<ArticleType> getList(boolean paramBoolean, String paramString1, String paramString2);

  public abstract ArticleType getDef();
}


 
 
 