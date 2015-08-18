package com.portal.usermgr.service;

import com.portal.usermgr.entity.User;
import com.portal.usermgr.entity.UserBind;
import org.springframework.data.domain.Page;

public abstract interface UserBindService
{
  public abstract Page<UserBind> getPage(int paramInt1, int paramInt2);

  public abstract UserBind getBindByUser(Integer paramInteger1, Integer paramInteger2);

  public abstract UserBind findById(Integer paramInteger);

  public abstract UserBind save(User paramUser, String paramString1, String paramString2, Integer paramInteger);

  public abstract UserBind update(UserBind paramUserBind);

  public abstract UserBind deleteById(Integer paramInteger);

  public abstract UserBind[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 