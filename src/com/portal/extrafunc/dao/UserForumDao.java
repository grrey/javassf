package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.UserForum;
import org.springframework.data.domain.Page;

public abstract interface UserForumDao extends QueryDao<UserForum>
{
  public abstract Page<UserForum> getPage(int paramInt1, int paramInt2);
}


 
 
 