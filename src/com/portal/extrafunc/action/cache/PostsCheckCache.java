package com.portal.extrafunc.action.cache;

import java.util.Date;

public abstract interface PostsCheckCache
{
  public abstract void updateCheck(String paramString);

  public abstract Date postsTime(String paramString);

  public abstract void refreshCheck();

  public abstract long getInterval();
}


 
 
 