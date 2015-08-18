package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.Theme;
import com.portal.extrafunc.entity.ThemeTxt;
import org.springframework.data.domain.Page;

public abstract interface ThemeTxtService
{
  public abstract Page<ThemeTxt> getPage(int paramInt1, int paramInt2);

  public abstract ThemeTxt findById(Integer paramInteger);

  public abstract ThemeTxt save(Theme paramTheme);

  public abstract ThemeTxt update(Integer paramInteger1, Integer paramInteger2);

  public abstract int deleteByForumId(Integer paramInteger);

  public abstract int deleteByUserId(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract ThemeTxt deleteById(Integer paramInteger);

  public abstract ThemeTxt[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 