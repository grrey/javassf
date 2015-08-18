package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.Comment;
import org.springframework.data.domain.Page;

public abstract interface CommentDao extends QueryDao<Comment>
{
  public abstract Page<Comment> getPage(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Boolean paramBoolean1, Boolean paramBoolean2, int paramInt1, String paramString1, String paramString2, boolean paramBoolean, int paramInt2, int paramInt3);

  public abstract Integer getAllCommentCount(Integer paramInteger);

  public abstract Integer getFloorByComment(Integer paramInteger);

  public abstract int deleteByParentId(Integer paramInteger);

  public abstract int deleteByDocId(Integer paramInteger);

  public abstract int deleteByTreeNumber(String paramString);

  public abstract int deleteByDocUserId(Integer paramInteger);

  public abstract int deleteByUserId(Integer paramInteger);

  public abstract int deleteByUserIdAndParent(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);
}


 
 
 