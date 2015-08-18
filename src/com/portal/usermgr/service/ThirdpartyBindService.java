package com.portal.usermgr.service;

import com.portal.usermgr.entity.ThirdpartyBind;
import org.springframework.data.domain.Page;

public abstract interface ThirdpartyBindService
{
  public abstract Page<ThirdpartyBind> getPage(int paramInt1, int paramInt2);

  public abstract ThirdpartyBind findByOpenId(String paramString1, String paramString2);

  public abstract ThirdpartyBind findById(Integer paramInteger);

  public abstract ThirdpartyBind save(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract ThirdpartyBind update(ThirdpartyBind paramThirdpartyBind);

  public abstract ThirdpartyBind deleteById(Integer paramInteger);

  public abstract ThirdpartyBind[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 