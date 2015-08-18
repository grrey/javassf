package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.Questionnaire;
import com.portal.sysmgr.entity.Site;
import org.springframework.data.domain.Page;

public abstract interface QuestionnaireService
{
  public abstract Page<Questionnaire> getPage(Integer paramInteger, boolean paramBoolean, int paramInt1, int paramInt2);

  public abstract Questionnaire findById(Integer paramInteger);

  public abstract Questionnaire save(Questionnaire paramQuestionnaire, Site paramSite);

  public abstract Questionnaire update(Questionnaire paramQuestionnaire);

  public abstract Questionnaire deleteById(Integer paramInteger);

  public abstract Questionnaire[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 