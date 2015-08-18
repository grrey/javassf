package com.portal.doccenter.service;

import com.portal.doccenter.entity.ArticleSign;
import com.portal.usermgr.entity.User;
import org.springframework.data.domain.Page;

public abstract interface ArticleSignService
{
  public abstract Page<ArticleSign> getPage(int paramInt1, int paramInt2);

  public abstract ArticleSign findById(Integer paramInteger);

  public abstract ArticleSign save(Integer paramInteger, User paramUser);

  public abstract ArticleSign update(ArticleSign paramArticleSign);

  public abstract ArticleSign deleteById(Integer paramInteger);

  public abstract ArticleSign[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 