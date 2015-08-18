package com.portal.extrafunc.action.cache;

import com.portal.extrafunc.entity.ForumStatis;
import com.portal.sysmgr.entity.Site;

public abstract interface ForumStatisCache
{
  public abstract ForumStatis updateStatis(Site paramSite, Integer paramInteger1, Integer paramInteger2);

  public abstract ForumStatis getStatis(Site paramSite);

  public abstract void statisToDB();

  public abstract void statisOneday();
}


 
 
 