package com.portal.usermgr.service;

import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.Admin;
import com.portal.usermgr.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface AdminService
{
  public abstract Page<Admin> getPage(int paramInt1, int paramInt2);

  public abstract Page<Admin> getPage(String paramString1, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract List<Admin> getListByDepart(Integer paramInteger);

  public abstract List<Admin> getListByRole(Integer paramInteger);

  public abstract Admin findById(Integer paramInteger);

  public abstract Admin saveAdmin(User paramUser, Admin paramAdmin, String paramString, Integer paramInteger1, Integer paramInteger2, Site paramSite, Byte paramByte, Boolean paramBoolean, Integer[] paramArrayOfInteger);

  public abstract Admin updateAdmin(User paramUser, Admin paramAdmin, Integer paramInteger1, Integer paramInteger2, Site paramSite, Byte paramByte, Boolean paramBoolean, Integer[] paramArrayOfInteger);

  public abstract void updateLoginInfo(User paramUser, String paramString);

  public abstract Admin save(Admin paramAdmin);

  public abstract Admin update(Admin paramAdmin);

  public abstract Admin updatePass(Integer paramInteger, String paramString);

  public abstract Admin deleteById(Integer paramInteger);

  public abstract Admin[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 