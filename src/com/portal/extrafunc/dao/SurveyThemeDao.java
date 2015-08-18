package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.SurveyTheme;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface SurveyThemeDao extends QueryDao<SurveyTheme>
{
  public abstract Page<SurveyTheme> getPage(Integer paramInteger, int paramInt1, int paramInt2);

  public abstract List<SurveyTheme> getList(Integer paramInteger);
}


 
 
 