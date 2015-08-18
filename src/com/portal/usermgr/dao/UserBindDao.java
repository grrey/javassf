package com.portal.usermgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.usermgr.entity.UserBind;

public abstract interface UserBindDao extends QueryDao<UserBind>
{
  public abstract UserBind getBindByUser(Integer paramInteger1, Integer paramInteger2);
}


 
 
 