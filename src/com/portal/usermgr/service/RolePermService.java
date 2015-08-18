package com.portal.usermgr.service;

import com.portal.usermgr.entity.Role;
import com.portal.usermgr.entity.RolePerm;
import org.springframework.data.domain.Page;

public abstract interface RolePermService
{
  public abstract Page<RolePerm> getPage(int paramInt1, int paramInt2);

  public abstract RolePerm findById(Integer paramInteger);

  public abstract RolePerm save(Role paramRole, RolePerm paramRolePerm);

  public abstract RolePerm save(RolePerm paramRolePerm);

  public abstract RolePerm update(RolePerm paramRolePerm);

  public abstract int deleteBySiteId(Integer paramInteger);

  public abstract RolePerm deleteById(Integer paramInteger);

  public abstract RolePerm[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 