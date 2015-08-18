package com.portal.doccenter.service;

import com.portal.doccenter.entity.WorkFlow;
import com.portal.sysmgr.entity.Site;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface WorkFlowService
{
  public abstract Page<WorkFlow> getPage(Integer paramInteger, String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List<WorkFlow> findByList(Integer paramInteger);

  public abstract WorkFlow findById(Integer paramInteger);

  public abstract WorkFlow save(WorkFlow paramWorkFlow, Site paramSite, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

  public abstract WorkFlow update(WorkFlow paramWorkFlow, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

  public abstract WorkFlow deleteById(Integer paramInteger);

  public abstract WorkFlow[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 