package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.SurveyDetail;
import org.springframework.data.domain.Page;

public abstract interface SurveyDetailDao extends QueryDao<SurveyDetail>
{
  public abstract Page<SurveyDetail> getPage(Integer paramInteger, int paramInt1, int paramInt2);
}


 
 
 