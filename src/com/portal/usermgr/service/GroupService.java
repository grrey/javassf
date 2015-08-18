package com.portal.usermgr.service;

import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.Group;
import com.portal.usermgr.entity.GroupPerm;
import java.util.List;

public abstract interface GroupService
{
  public abstract List<Group> getList(Integer paramInteger, String paramString1, String paramString2);

  public abstract Group findById(Integer paramInteger);

  public abstract Group saveGroup(Group paramGroup, GroupPerm paramGroupPerm, Site paramSite);

  public abstract Group updateGroup(Group paramGroup, GroupPerm paramGroupPerm);

  public abstract Group save(Group paramGroup);

  public abstract Group update(Group paramGroup);

  public abstract Group deleteById(Integer paramInteger);

  public abstract Group[] deleteByIds(Integer[] paramArrayOfInteger);

  public abstract Group[] updatePriority(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);
}


 
 
 