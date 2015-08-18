package com.portal.extrafunc.service;

import com.portal.extrafunc.entity.AdvertSlot;
import com.portal.sysmgr.entity.Site;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface AdvertSlotService
{
  public abstract Page<AdvertSlot> getPage(Integer paramInteger, String paramString1, String paramString2, int paramInt1, int paramInt2);

  public abstract List<AdvertSlot> getList(Integer paramInteger);

  public abstract AdvertSlot findById(Integer paramInteger);

  public abstract AdvertSlot save(AdvertSlot paramAdvertSlot, Site paramSite);

  public abstract AdvertSlot update(AdvertSlot paramAdvertSlot);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract AdvertSlot deleteById(Integer paramInteger);

  public abstract AdvertSlot[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 