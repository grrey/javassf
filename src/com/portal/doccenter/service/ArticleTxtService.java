package com.portal.doccenter.service;

import com.portal.doccenter.entity.Article;
import com.portal.doccenter.entity.ArticleTxt;

public abstract interface ArticleTxtService
{
  public abstract ArticleTxt save(ArticleTxt paramArticleTxt, Article paramArticle);

  public abstract ArticleTxt update(ArticleTxt paramArticleTxt, Article paramArticle);

  public abstract int delDocByInputUser(Integer paramInteger);

  public abstract int delDocBySite(Integer paramInteger);
}


 
 
 