package com.portal.usermgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.usermgr.entity.User;

public abstract interface UserDao extends QueryDao<User>
{
  public abstract int getAllUserCount();

  public abstract User findByUsername(String paramString);
}


 
 
 