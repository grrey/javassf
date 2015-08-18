package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.Questionnaire;
import org.springframework.data.domain.Page;

public abstract interface QuestionnaireDao extends QueryDao<Questionnaire>
{
  public abstract Page<Questionnaire> getPage(Integer paramInteger, boolean paramBoolean, int paramInt1, int paramInt2);
}


 
 
 