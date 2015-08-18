package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.ForumOperate;
import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.User;
import org.springframework.data.domain.Page;

public abstract interface ForumOperateService
{
  public abstract Page<ForumOperate> getPage(int paramInt1, int paramInt2);

  public abstract ForumOperate findById(Integer paramInteger);

  public abstract ForumOperate save(Integer paramInteger, String paramString1, String paramString2, String paramString3, Site paramSite, User paramUser);

  public abstract ForumOperate update(ForumOperate paramForumOperate);

  public abstract ForumOperate deleteById(Integer paramInteger);

  public abstract ForumOperate[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 