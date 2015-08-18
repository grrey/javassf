package com.portal.extrafunc.action.cache;

import com.portal.extrafunc.entity.Forum;
import com.portal.extrafunc.entity.Theme;

public abstract interface ForumCache
{
  public abstract Forum updateForum(Theme paramTheme, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);

  public abstract Forum getForum(Integer paramInteger);

  public abstract void statisToDB();

  public abstract void statisOneday();
}


 
 
 