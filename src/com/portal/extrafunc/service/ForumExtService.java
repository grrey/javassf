package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.Forum;
import com.portal.extrafunc.entity.ForumExt;
import org.springframework.data.domain.Page;

public abstract interface ForumExtService
{
  public abstract Page<ForumExt> getPage(int paramInt1, int paramInt2);

  public abstract ForumExt findById(Integer paramInteger);

  public abstract ForumExt save(ForumExt paramForumExt, Forum paramForum);

  public abstract ForumExt update(ForumExt paramForumExt, Forum paramForum);

  public abstract int deleteByCategoryId(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract ForumExt deleteById(Integer paramInteger);

  public abstract ForumExt[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 