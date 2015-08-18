package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.SurveyDetail;
import com.portal.extrafunc.entity.SurveyTheme;
import com.portal.usermgr.entity.User;
import org.springframework.data.domain.Page;

public abstract interface SurveyDetailService
{
  public abstract Page<SurveyDetail> getPage(Integer paramInteger, int paramInt1, int paramInt2);

  public abstract SurveyDetail findById(Integer paramInteger);

  public abstract SurveyDetail save(String paramString, SurveyTheme paramSurveyTheme, User paramUser);

  public abstract SurveyDetail update(SurveyDetail paramSurveyDetail);

  public abstract SurveyDetail deleteById(Integer paramInteger);

  public abstract SurveyDetail[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 