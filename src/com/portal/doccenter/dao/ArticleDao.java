package com.portal.doccenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.doccenter.entity.Article;
import com.portal.doccenter.entity.Channel;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface ArticleDao extends QueryDao<Article>
{
  public abstract Page<Article> getPageArticle(
		  String paramString1, Integer[] paramArrayOfInteger1, 
		  Integer[] paramArrayOfInteger2, Integer paramInteger1,
		  boolean paramBoolean1, boolean paramBoolean2, 
		  Byte[] paramArrayOfByte, Integer paramInteger2, 
		  Integer paramInteger3, Integer paramInteger4, 
		  Integer paramInteger5, String paramString2, Byte paramByte,
		  Boolean paramBoolean3, Boolean paramBoolean4, 
		  int paramInt1, String paramString3,
		  String paramString4, 
		  int paramInt2,
		  int paramInt3);

  public abstract Page<Article> getPageDocByMember(String paramString1, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, boolean paramBoolean1, boolean paramBoolean2, Integer paramInteger1, Integer paramInteger2, String paramString2, int paramInt1, int paramInt2);

  public abstract long getCountByChecking(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, Byte paramByte, Boolean paramBoolean, boolean paramBoolean1);

  public abstract long getCountDoc(String paramString);

  public abstract long getCountByDepartSign(Integer paramInteger1, Integer paramInteger2);

  public abstract Article getSide(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, boolean paramBoolean);

  public abstract List<Article> getListTagByIds(Integer[] paramArrayOfInteger, int paramInt);

  public abstract Page<Article> getPageTagByChannelIds(Integer[] paramArrayOfInteger1, Integer paramInteger1, Integer[] paramArrayOfInteger2, Integer[] paramArrayOfInteger3, Integer paramInteger2, Integer paramInteger3, String paramString, Boolean paramBoolean, Date paramDate, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public abstract List<Article> getListTagByChannelIds(Integer[] paramArrayOfInteger1, Integer paramInteger1, Integer[] paramArrayOfInteger2, Integer[] paramArrayOfInteger3, Integer paramInteger2, Integer paramInteger3, String paramString, Boolean paramBoolean, Date paramDate, int paramInt1, int paramInt2, Integer paramInteger4, Integer paramInteger5);

  public abstract Page<Article> getPageTagByModelIds(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Integer paramInteger, Boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3);

  public abstract List<Article> getListTagByModelIds(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Integer paramInteger1, Boolean paramBoolean, int paramInt, Integer paramInteger2, Integer paramInteger3);

  public abstract List<Article> getListByUp(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);

  public abstract List<Object> getCountByDepart(Integer paramInteger1, String paramString, Integer paramInteger2, Date paramDate1, Date paramDate2);

  public abstract int getAllArtiCount(Integer paramInteger, boolean paramBoolean);

  public abstract int updateMoveDoc(String paramString, Integer paramInteger, Channel paramChannel);

  public abstract int updateDocByInputUser(Integer paramInteger);

  public abstract int updateDocByCheckUser(Integer paramInteger);

  public abstract int updateDocByInputDepart(Integer paramInteger);

  public abstract int updateDocByRole(Integer paramInteger);

  public abstract int delDocByInputUser(Integer paramInteger);

  public abstract int delDocBySite(Integer paramInteger);
}


 
 
 