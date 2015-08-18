package com.portal.usermgr.service;

import com.portal.usermgr.entity.User;

public abstract interface UserService
{
  public abstract User findById(Integer paramInteger);

  public abstract User findByUsername(String paramString);

  public abstract int getAllUserCount();

  public abstract User save(User paramUser);

  public abstract User update(User paramUser);

  public abstract User updatePass(Integer paramInteger, String paramString);

  public abstract User updateFailTime(User paramUser);

  public abstract User deleteById(Integer paramInteger);

  public abstract User[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 