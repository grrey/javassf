package com.portal.usermgr.service;

import com.portal.usermgr.entity.Member;
import com.portal.usermgr.entity.User;
import org.springframework.data.domain.Page;

public abstract interface MemberService
{
  public abstract Page<Member> getPage(int paramInt1, int paramInt2);

  public abstract Page<Member> getPage(String paramString1, Integer paramInteger1, Integer paramInteger2, String paramString2, String paramString3, int paramInt1, int paramInt2);

  public abstract int getNoCheckMemberCount();

  public abstract Member findById(Integer paramInteger);

  public abstract Member registerMember(User paramUser, Member paramMember, String paramString, Integer paramInteger);

  public abstract Member updateMember(User paramUser, Member paramMember, Integer paramInteger1, Integer paramInteger2);

  public abstract void updateLoginInfo(User paramUser, String paramString);

  public abstract Member save(Member paramMember);

  public abstract Member update(Member paramMember);

  public abstract Member updatePass(Integer paramInteger, String paramString);

  public abstract Member deleteById(Integer paramInteger);

  public abstract Member[] deleteByIds(Integer[] paramArrayOfInteger);
}


 
 
 