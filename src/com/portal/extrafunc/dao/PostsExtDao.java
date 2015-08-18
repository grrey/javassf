package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.PostsExt;
import org.springframework.data.domain.Page;

public abstract interface PostsExtDao extends QueryDao<PostsExt>
{
  public abstract Page<PostsExt> getPage(int paramInt1, int paramInt2);
}


 
 
 