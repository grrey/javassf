package com.portal.datacenter.commdata.service;

import com.portal.datacenter.commdata.entity.ForeignLang;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface ForeignLangService
{
  public abstract Page<ForeignLang> getPage(int paramInt1, int paramInt2);

  public abstract List<ForeignLang> getForeignLangList();

  public abstract ForeignLang findById(Integer paramInteger);

  public abstract ForeignLang save(ForeignLang paramForeignLang);

  public abstract ForeignLang update(ForeignLang paramForeignLang);

  public abstract ForeignLang deleteById(Integer paramInteger);

  public abstract ForeignLang[] deleteByIds(Integer[] paramArrayOfInteger);
}

