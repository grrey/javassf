package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.ForumExt;
import org.springframework.data.domain.Page;

public abstract interface ForumExtDao extends QueryDao<ForumExt>
{
  public abstract Page<ForumExt> getPage(int paramInt1, int paramInt2);

  public abstract int deleteByCategoryId(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);
}


 
 
 