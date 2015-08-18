package com.portal.doccenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.doccenter.entity.FlowStep;
import org.springframework.data.domain.Page;

public abstract interface FlowStepDao extends QueryDao<FlowStep>
{
  public abstract Page<FlowStep> getPage(int paramInt1, int paramInt2);
}


 
 
 