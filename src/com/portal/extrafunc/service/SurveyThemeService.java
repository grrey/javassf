package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.SurveyTheme;
import com.portal.usermgr.entity.User;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public abstract interface SurveyThemeService
{
  public abstract Page<SurveyTheme> getPage(Integer paramInteger, int paramInt1, int paramInt2);

  public abstract List<SurveyTheme> getList(Integer paramInteger);

  public abstract SurveyTheme findById(Integer paramInteger);

  public abstract SurveyTheme save(SurveyTheme paramSurveyTheme, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String[] paramArrayOfString, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

  public abstract SurveyTheme update(SurveyTheme paramSurveyTheme, Integer paramInteger1, Integer paramInteger2, String[] paramArrayOfString, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

  public abstract void voteSurvey(Integer paramInteger, Map<String, String> paramMap, Map<String, String[]> paramMap1, String paramString, User paramUser);

  public abstract SurveyTheme deleteById(Integer paramInteger);

  public abstract SurveyTheme[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 