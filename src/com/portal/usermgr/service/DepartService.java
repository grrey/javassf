package com.portal.usermgr.service;

import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.Depart;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface DepartService
{
  public abstract Page<Depart> getPage(String paramString1, Integer paramInteger, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract List<Depart> getListByTag(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);

  public abstract List<Depart> getAllList(Integer paramInteger);

  public abstract List<Depart> getListNoParent(Integer paramInteger);

  public abstract List<Depart> getListByParent(Integer paramInteger);

  public abstract Depart getDepartByPath(String paramString, Integer paramInteger);

  public abstract Depart getDepartByName(String paramString, Integer paramInteger);

  public abstract Depart findById(Integer paramInteger);

  public abstract Depart save(Depart paramDepart, Site paramSite, Integer paramInteger, Integer[] paramArrayOfInteger);

  public abstract Depart update(Depart paramDepart, Integer paramInteger, Integer[] paramArrayOfInteger);

  public abstract Depart updatePrio(Integer paramInteger1, Integer paramInteger2);

  public abstract void deleteBySiteId(Integer paramInteger);

  public abstract Depart deleteById(Integer paramInteger);

  public abstract Depart[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 