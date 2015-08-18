package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.MessageBoard;
import org.springframework.data.domain.Page;

public abstract interface MessageBoardDao extends QueryDao<MessageBoard>
{
  public abstract Page<MessageBoard> getPage(String paramString1, Integer paramInteger, Boolean paramBoolean, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract Integer getAllMessageCount(Integer paramInteger);

  public abstract Integer getNoRepMessageCount(Integer paramInteger);
}


 
 
 