package com.portal.doccenter.service;

import com.portal.doccenter.entity.Article;
import com.portal.doccenter.entity.DocStatis;

public abstract interface DocStatisService
{
  public abstract DocStatis save(Article paramArticle);

  public abstract DocStatis save(Article paramArticle, DocStatis paramDocStatis);

  public abstract DocStatis update(Integer paramInteger1, Integer paramInteger2);

  public abstract DocStatis findById(Integer paramInteger);

  public abstract DocStatis ups(Integer paramInteger);

  public abstract DocStatis treads(Integer paramInteger);
}


 
 
 