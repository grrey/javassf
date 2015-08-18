package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.QuestionDetail;
import com.portal.extrafunc.entity.Questionnaire;
import com.portal.usermgr.entity.User;
import org.springframework.data.domain.Page;

public abstract interface QuestionDetailService
{
  public abstract Page<QuestionDetail> getPage(Integer paramInteger, int paramInt1, int paramInt2);

  public abstract QuestionDetail getDetail(Integer paramInteger1, Integer paramInteger2, String paramString);

  public abstract long getCountDetail(Integer paramInteger);

  public abstract QuestionDetail findById(Integer paramInteger);

  public abstract QuestionDetail save(Questionnaire paramQuestionnaire, User paramUser, String paramString);

  public abstract QuestionDetail update(QuestionDetail paramQuestionDetail);

  public abstract QuestionDetail deleteById(Integer paramInteger);

  public abstract QuestionDetail[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 