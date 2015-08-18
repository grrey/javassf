package com.portal.doccenter.service;

import com.portal.doccenter.entity.Channel;
import com.portal.doccenter.entity.ChannelExt;
import com.portal.doccenter.entity.ChannelTxt;
import com.portal.sysmgr.entity.Site;
import com.portal.usermgr.entity.User;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;

public abstract interface ChannelService
{
  public abstract List<Channel> getChannelListByTag(Integer paramInteger1, Integer paramInteger2, Boolean paramBoolean, boolean paramBoolean1, int paramInt);

  public abstract Page<Channel> getChannelPageByTag(Integer paramInteger1, Integer paramInteger2, Boolean paramBoolean, boolean paramBoolean1, int paramInt1, int paramInt2);

  public abstract List<Channel> getChannelBySite(Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2, String paramString3, Boolean paramBoolean);

  public abstract List<Channel> getChannelByAdmin(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString1, String paramString2, String paramString3, Boolean paramBoolean);

  public abstract List<Channel> getChannelByModel(Integer paramInteger1, Integer paramInteger2, User paramUser, Integer paramInteger3);

  public abstract List<Channel> getChannelByModelAndMember(Integer paramInteger1, Integer paramInteger2, User paramUser, Integer paramInteger3);

  public abstract Channel delChannel(Integer paramInteger1, Boolean paramBoolean, Integer paramInteger2);

  public abstract Channel findByPath(String paramString, Integer paramInteger);

  public abstract Channel findByPathForTag(String paramString, Integer paramInteger);

  public abstract Channel findByNumber(String paramString);

  public abstract Channel findByName(String paramString);

  public abstract Channel findById(Integer paramInteger);

  public abstract int getAllChannelCount(Integer paramInteger);

  public abstract Channel save(Channel paramChannel, ChannelExt paramChannelExt, ChannelTxt paramChannelTxt, Site paramSite, User paramUser, Integer paramInteger1, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Integer paramInteger2, Map<String, String> paramMap);

  public abstract Channel update(Channel paramChannel, ChannelExt paramChannelExt, ChannelTxt paramChannelTxt, Integer paramInteger1, Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2, Integer paramInteger2, Map<String, String> paramMap);

  public abstract Channel updatePrio(Integer paramInteger1, Integer paramInteger2);

  public abstract Channel deleteById(Integer paramInteger);

  public abstract Channel[] deleteByIds(Integer[] paramArrayOfInteger);

  public abstract Channel[] updatePriority(Integer[] paramArrayOfInteger1, Integer[] paramArrayOfInteger2);

  public abstract int updateChannelByInputDepart(Integer paramInteger);

  public abstract int delChannelByInputDepart(Integer paramInteger);

  public abstract int updateChannelByWorkFlow(Integer paramInteger);
}


 
 
 