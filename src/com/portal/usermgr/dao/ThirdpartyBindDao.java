package com.portal.usermgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.usermgr.entity.ThirdpartyBind;

public abstract interface ThirdpartyBindDao extends QueryDao<ThirdpartyBind>
{
  public abstract ThirdpartyBind findByOpenId(String paramString1, String paramString2);
}


 
 
 