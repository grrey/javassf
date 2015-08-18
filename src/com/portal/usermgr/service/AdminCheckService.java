package com.portal.usermgr.service;

import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.Admin;
import com.portal.usermgr.entity.AdminCheck;

public abstract interface AdminCheckService
{
  public abstract AdminCheck findById(Integer paramInteger);

  public abstract AdminCheck save(Site paramSite, Admin paramAdmin, Byte paramByte, Boolean paramBoolean, Integer[] paramArrayOfInteger);

  public abstract AdminCheck update(AdminCheck paramAdminCheck);

  public abstract void updateByAdmin(Admin paramAdmin, Site paramSite, Byte paramByte, Boolean paramBoolean, Integer[] paramArrayOfInteger);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract AdminCheck deleteById(Integer paramInteger);

  public abstract AdminCheck[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 