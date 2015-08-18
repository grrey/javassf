package com.portal.usermgr.service;

import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.Role;
import com.portal.usermgr.entity.RolePerm;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface RoleService
{
  public abstract Page<Role> getPage(String paramString1, Integer paramInteger, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract List<Role> getListBySite(Integer paramInteger);

  public abstract Role findById(Integer paramInteger);

  public abstract Role saveRole(Role paramRole, RolePerm paramRolePerm, Site paramSite);

  public abstract Role updateRole(Role paramRole, RolePerm paramRolePerm);

  public abstract Role save(Role paramRole);

  public abstract Role update(Role paramRole);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract Role deleteById(Integer paramInteger);

  public abstract Role[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 