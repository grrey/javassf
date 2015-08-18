package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.ForumOperate;
import org.springframework.data.domain.Page;

public abstract interface ForumOperateDao extends QueryDao<ForumOperate>
{
  public abstract Page<ForumOperate> getPage(int paramInt1, int paramInt2);
}


 
 
 