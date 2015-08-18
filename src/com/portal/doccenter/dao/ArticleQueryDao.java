package com.portal.doccenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.doccenter.entity.Article;

public abstract interface ArticleQueryDao extends QueryDao<Article>
{
  public abstract int emptyArticlePage(String paramString);
}


 
 
 