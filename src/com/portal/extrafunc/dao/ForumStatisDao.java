package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.ForumStatis;
import org.springframework.data.domain.Page;

public abstract interface ForumStatisDao extends QueryDao<ForumStatis>
{
  public abstract Page<ForumStatis> getPage(int paramInt1, int paramInt2);
}


 
 
 