package com.portal.doccenter.service;

import com.portal.doccenter.entity.FlowStep;
import com.portal.doccenter.entity.WorkFlow;
import org.springframework.data.domain.Page;

public abstract interface FlowStepService
{
  public abstract Page<FlowStep> getPage(int paramInt1, int paramInt2);

  public abstract FlowStep findById(Integer paramInteger);

  public abstract void save(WorkFlow paramWorkFlow, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

  public abstract void update(WorkFlow paramWorkFlow, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

  public abstract FlowStep deleteById(Integer paramInteger);

  public abstract FlowStep[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 