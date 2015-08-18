package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.Theme;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface ThemeDao extends QueryDao<Theme>
{
  public abstract Page<Theme> getPage(int paramInt1, int paramInt2);

  public abstract Page<Theme> getThemePageForTag(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, int paramInt1, int paramInt2, int paramInt3);

  public abstract List<Theme> getThemeByTop();

  public abstract List<Theme> getThemeByLight();

  public abstract List<Theme> getThemeByLock();

  public abstract List<Theme> getThemeAll();

  public abstract int deleteByForumId(Integer paramInteger);

  public abstract int deleteByUserId(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract int deleteByCategoryId(Integer paramInteger);
}


 
 
 