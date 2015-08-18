package com.portal.extrafunc.service;

import com.portal.doccenter.entity.Article;
import com.portal.extrafunc.entity.Comment;
import com.portal.extrafunc.entity.CommentExt;
import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.User;
import org.springframework.data.domain.Page;

public abstract interface CommentService
{
  public abstract Page<Comment> getPage(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Boolean paramBoolean1, Boolean paramBoolean2, int paramInt1, String paramString1, String paramString2, int paramInt2, int paramInt3);

  public abstract Page<Comment> getPageForTag(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Boolean paramBoolean1, Boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3);

  public abstract Integer getAllCommentCount(Integer paramInteger);

  public abstract Comment findById(Integer paramInteger);

  public abstract Comment comment(String paramString1, String paramString2, Integer paramInteger, Article paramArticle, User paramUser, Site paramSite);

  public abstract Comment update(Comment paramComment, CommentExt paramCommentExt);

  public abstract int deleteByDocId(Integer paramInteger);

  public abstract int deleteByTreeNumber(String paramString);

  public abstract int deleteByUserId(Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract Comment checkById(Integer paramInteger);

  public abstract Comment[] checkByIds(Integer[] paramArrayOfInteger);

  public abstract Comment deleteById(Integer paramInteger);

  public abstract Comment[] deleteByIds(Integer[] paramArrayOfInteger);

  public abstract void ups(Integer paramInteger);

  public abstract void ups(Integer paramInteger1, Integer paramInteger2);
}


 
 
 