package com.portal.doccenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.doccenter.entity.FlowDetail;
import org.springframework.data.domain.Page;

public abstract interface FlowDetailDao extends QueryDao<FlowDetail>
{
  public abstract Page<FlowDetail> getPage(int paramInt1, int paramInt2);

  public abstract FlowDetail getLastFlowDetail(Integer paramInteger);
}


 
 
 