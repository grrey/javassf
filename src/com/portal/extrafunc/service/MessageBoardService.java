package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.MessageBoard;
import com.portal.extrafunc.entity.MessageBoardExt;
import com.portal.sysmgr.entity.Site;
import org.springframework.data.domain.Page;

public abstract interface MessageBoardService
{
  public abstract Page<MessageBoard> getPage(String paramString1, Integer paramInteger, Boolean paramBoolean, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract Integer getAllMessageCount(Integer paramInteger);

  public abstract Integer getNoRepMessageCount(Integer paramInteger);

  public abstract MessageBoard findById(Integer paramInteger);

  public abstract MessageBoard save(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Integer paramInteger, String paramString7, Site paramSite);

  public abstract MessageBoard save(MessageBoard paramMessageBoard, MessageBoardExt paramMessageBoardExt, Site paramSite, Integer paramInteger);

  public abstract MessageBoard update(MessageBoard paramMessageBoard, MessageBoardExt paramMessageBoardExt, Integer paramInteger);

  public abstract MessageBoard showBoard(Integer paramInteger);

  public abstract MessageBoard deleteById(Integer paramInteger);

  public abstract MessageBoard[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 