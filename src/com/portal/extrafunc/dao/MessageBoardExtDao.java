package com.portal.extrafunc.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.extrafunc.entity.MessageBoardExt;
import org.springframework.data.domain.Page;

public abstract interface MessageBoardExtDao extends QueryDao<MessageBoardExt>
{
  public abstract Page<MessageBoardExt> getPage(int paramInt1, int paramInt2);
}


 
 
 