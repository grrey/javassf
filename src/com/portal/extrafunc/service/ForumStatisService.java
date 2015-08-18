package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.ForumStatis;
import com.portal.sysmgr.entity.Site;
import org.springframework.data.domain.Page;

public abstract interface ForumStatisService
{
  public abstract Page<ForumStatis> getPage(int paramInt1, int paramInt2);

  public abstract ForumStatis findById(Integer paramInteger);

  public abstract ForumStatis save(Site paramSite);

  public abstract ForumStatis update(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);

  public abstract ForumStatis updateOnday(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);

  public abstract ForumStatis update(ForumStatis paramForumStatis);

  public abstract ForumStatis deleteById(Integer paramInteger);

  public abstract ForumStatis[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 