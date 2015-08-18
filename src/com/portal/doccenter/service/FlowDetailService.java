package com.portal.doccenter.service;

import com.portal.doccenter.entity.Article;
import com.portal.doccenter.entity.FlowDetail;
import com.portal.usermgr.entity.Role;
import com.portal.usermgr.entity.User;
import org.springframework.data.domain.Page;

public abstract interface FlowDetailService
{
  public abstract Page<FlowDetail> getPage(int paramInt1, int paramInt2);

  public abstract FlowDetail getLastFlowDetail(Integer paramInteger);

  public abstract FlowDetail findById(Integer paramInteger);

  public abstract FlowDetail saveCheck(Article paramArticle, User paramUser, Role paramRole);

  public abstract FlowDetail saveReturn(Article paramArticle, User paramUser, Role paramRole, String paramString, boolean paramBoolean);

  public abstract FlowDetail update(FlowDetail paramFlowDetail);

  public abstract FlowDetail deleteById(Integer paramInteger);

  public abstract FlowDetail[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 