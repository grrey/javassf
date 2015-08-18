package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.MessageType;
import com.portal.sysmgr.entity.Site;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface MessageTypeService
{
  public abstract Page<MessageType> getPage(int paramInt1, int paramInt2);

  public abstract List<MessageType> getList(Integer paramInteger, String paramString1, String paramString2);

  public abstract MessageType getUniqueType(Integer paramInteger);

  public abstract MessageType findById(Integer paramInteger);

  public abstract MessageType save(MessageType paramMessageType, Site paramSite);

  public abstract MessageType update(MessageType paramMessageType);

  public abstract MessageType deleteById(Integer paramInteger);

  public abstract MessageType[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 