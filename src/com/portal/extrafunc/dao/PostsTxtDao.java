package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.PostsTxt;
import org.springframework.data.domain.Page;

public abstract interface PostsTxtDao extends QueryDao<PostsTxt>
{
  public abstract Page<PostsTxt> getPage(int paramInt1, int paramInt2);
}


 
 
 