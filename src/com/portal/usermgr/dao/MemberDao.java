package com.portal.usermgr.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.usermgr.entity.Member;
import org.springframework.data.domain.Page;

public abstract interface MemberDao extends QueryDao<Member>
{
  public abstract Page<Member> getPage(int paramInt1, int paramInt2);

  public abstract Page<Member> getPage(String paramString1, Integer paramInteger1, Integer paramInteger2, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract int getNoCheckMemberCount();
}


 
 
 