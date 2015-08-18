package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.Comment;
import com.portal.extrafunc.entity.CommentExt;

public abstract interface CommentExtService
{
  public abstract CommentExt save(String paramString1, String paramString2, Comment paramComment);

  public abstract CommentExt update(CommentExt paramCommentExt);

  public abstract int deleteByParentId(Integer paramInteger);

  public abstract int deleteByDocId(Integer paramInteger);

  public abstract int deleteByTreeNumber(String paramString);

  public abstract int deleteByDocUserId(Integer paramInteger);

  public abstract int deleteByUserId(Integer paramInteger);

  public abstract int deleteByUserIdAndParent(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);
}


 
 
 