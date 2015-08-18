package com.portal.usermgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.usermgr.entity.Depart;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface DepartDao extends QueryDao<Depart>
{
  public abstract Page<Depart> getPage(String paramString1, Integer paramInteger, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract List<Depart> getListByTag(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3);

  public abstract List<Depart> getAllList(Integer paramInteger);

  public abstract List<Depart> getListNoParent(Integer paramInteger);

  public abstract List<Depart> getListByParent(Integer paramInteger);

  public abstract Depart getDepartByPath(String paramString, Integer paramInteger);

  public abstract Depart getDepartByName(String paramString, Integer paramInteger);

  public abstract int deleteBySiteId(Integer paramInteger);
}


 
 
 