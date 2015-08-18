package com.portal.doccenter.service;

import com.portal.doccenter.entity.Article;
import com.portal.doccenter.entity.ArticleExt;
import com.portal.doccenter.entity.ArticleTxt;
import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public abstract interface ArticleService
{
  public abstract Page<Article> getPageArticle(String paramString1, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Integer paramInteger1, boolean paramBoolean1, boolean paramBoolean2, Byte[] paramArrayOfByte, Integer paramInteger2, User paramUser, Integer paramInteger3, int paramInt1, String paramString2, String paramString3, int paramInt2, int paramInt3);

  public abstract Page<Article> getPageDocByMember(String paramString, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, boolean paramBoolean1, boolean paramBoolean2, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, int paramInt1, int paramInt2);

  public abstract long getCountByChecking(Integer paramInteger, User paramUser, boolean paramBoolean);

  public abstract long getCountDoc(Integer paramInteger);

  public abstract long getCountByDepartSign(Integer paramInteger1, Integer paramInteger2);

  public abstract int moveDoc(Integer paramInteger, Map<String, String> paramMap);

  public abstract List<Article> getListTagByIds(Integer[] paramArrayOfInteger, int paramInt);

  public abstract Article getSide(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, boolean paramBoolean);

  public abstract Page<Article> getPageTagByChannelIds(Integer[] paramArrayOfInteger1, Integer paramInteger1, Integer[] paramArrayOfInteger2, Integer[] paramArrayOfInteger3, Integer paramInteger2, Integer paramInteger3, Boolean paramBoolean, Date paramDate, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract List<Article> getListTagByChannelIds(Integer[] paramArrayOfInteger1, Integer paramInteger1, Integer[] paramArrayOfInteger2, Integer[] paramArrayOfInteger3, Integer paramInteger2, Integer paramInteger3, Boolean paramBoolean, Date paramDate, int paramInt1, int paramInt2, Integer paramInteger4, Integer paramInteger5);

  public abstract Page<Article> getPageTagByModelIds(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Integer paramInteger, Boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3);

  public abstract List<Article> getListTagByModelIds(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Integer paramInteger1, Boolean paramBoolean, int paramInt, Integer paramInteger2, Integer paramInteger3);

  public abstract List<Object> getCountByDepart(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Date paramDate1, Date paramDate2);

  public abstract Article findById(Integer paramInteger);

  public abstract Article save(Article paramArticle, ArticleExt paramArticleExt, ArticleTxt paramArticleTxt, Site paramSite, User paramUser, Integer[] paramArrayOfInteger, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, Boolean[] paramArrayOfBoolean, String[] paramArrayOfString5, Integer paramInteger1, Integer paramInteger2, boolean paramBoolean);

  public abstract Article update(Article paramArticle, ArticleExt paramArticleExt, ArticleTxt paramArticleTxt, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4, Boolean[] paramArrayOfBoolean, String[] paramArrayOfString5, Map<String, String> paramMap, Integer paramInteger, User paramUser, boolean paramBoolean);

  public abstract int getAllArtiCount(Integer paramInteger, boolean paramBoolean);

  public abstract Article[] check(Integer[] paramArrayOfInteger, User paramUser);

  public abstract int emptyDoc(Integer paramInteger);

  public abstract Article cycle(Integer paramInteger);

  public abstract Article[] cycle(Integer[] paramArrayOfInteger);

  public abstract Article reduct(Integer paramInteger);

  public abstract Article[] reduct(Integer[] paramArrayOfInteger);

  public abstract int updateDocByInputUser(Integer paramInteger);

  public abstract int updateDocByCheckUser(Integer paramInteger);

  public abstract int updateDocByInputDepart(Integer paramInteger);

  public abstract int updateDocByRole(Integer paramInteger);

  public abstract int delDocByInputUser(Integer paramInteger);

  public abstract int delDocBySite(Integer paramInteger);

  public abstract Article deleteById(Integer paramInteger);

  public abstract Article[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 