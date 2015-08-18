package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.CommentExt;

public abstract interface CommentExtDao extends QueryDao<CommentExt>
{
  public abstract int deleteByParentId(Integer paramInteger);

  public abstract int deleteByDocId(Integer paramInteger);

  public abstract int deleteByTreeNumber(String paramString);

  public abstract int deleteByDocUserId(Integer paramInteger);

  public abstract int deleteByUserId(Integer paramInteger);

  public abstract int deleteByUserIdAndParent(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);
}


 
 
 