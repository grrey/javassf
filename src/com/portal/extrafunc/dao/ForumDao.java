package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.Forum;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface ForumDao extends QueryDao<Forum>
{
  public abstract Page<Forum> getPage(Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List<Forum> getList(Integer paramInteger);

  public abstract List<Forum> getList();

  public abstract int deleteByCategoryId(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);
}


 
 
 