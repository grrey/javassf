package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.Theme;
import com.portal.usermgr.entity.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface ThemeService
{
  public abstract Page<Theme> getPage(int paramInt1, int paramInt2);

  public abstract Page<Theme> getThemePageForTag(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, int paramInt1, int paramInt2, int paramInt3);

  public abstract List<Theme> getThemeAll();

  public abstract Theme findById(Integer paramInteger);

  public abstract Theme save(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString, boolean paramBoolean1, boolean paramBoolean2);

  public abstract Theme updateReply(Theme paramTheme, User paramUser, boolean paramBoolean);

  public abstract Theme topTheme(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Date paramDate, String paramString);

  public abstract Theme essenaTheme(Integer paramInteger1, Integer paramInteger2, Date paramDate, String paramString);

  public abstract Theme lightTheme(Integer paramInteger1, Integer paramInteger2, String paramString1, Boolean paramBoolean1, Boolean paramBoolean2, Date paramDate, String paramString2);

  public abstract Theme lockTheme(Integer paramInteger1, Integer paramInteger2, Date paramDate, String paramString);

  public abstract Theme moveTheme(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString);

  public abstract void themeTopCheck();

  public abstract void themeLightCheck();

  public abstract void themeLockCheck();

  public abstract Theme update(Theme paramTheme);

  public abstract Theme updateViewCount(Integer paramInteger1, Integer paramInteger2);

  public abstract int deleteByForumId(Integer paramInteger);

  public abstract int deleteByUserId(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract int deleteByCategoryId(Integer paramInteger);

  public abstract Theme deleteById(Integer paramInteger);

  public abstract Theme[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 