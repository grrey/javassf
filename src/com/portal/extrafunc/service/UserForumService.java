package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.UserForum;
import com.portal.usermgr.entity.User;
import java.util.Date;
import org.springframework.data.domain.Page;

public abstract interface UserForumService
{
  public abstract Page<UserForum> getPage(int paramInt1, int paramInt2);

  public abstract UserForum findById(Integer paramInteger);

  public abstract UserForum save(User paramUser);

  public abstract UserForum update(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Integer paramInteger5);

  public abstract UserForum shieldUserForum(Integer paramInteger, Date paramDate);

  public abstract UserForum update(UserForum paramUserForum);

  public abstract UserForum deleteById(Integer paramInteger);

  public abstract UserForum[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 