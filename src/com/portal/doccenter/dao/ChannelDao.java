package com.portal.doccenter.dao;

import com.javassf.basic.hibernate4.QueryDao;
import com.portal.doccenter.entity.Channel;
import java.util.List;
import org.springframework.data.domain.Page;

public abstract interface ChannelDao extends QueryDao<Channel>
{
  public abstract List<Channel> getChannelListByTag(Integer paramInteger1, Integer paramInteger2, Boolean paramBoolean, boolean paramBoolean1, int paramInt);

  public abstract Page<Channel> getChannelPageByTag(Integer paramInteger1, Integer paramInteger2, Boolean paramBoolean, boolean paramBoolean1, int paramInt1, int paramInt2);

  public abstract List<Channel> getChannelBySite(Integer paramInteger1, Integer paramInteger2, String paramString1, String paramString2, String paramString3, Boolean paramBoolean);

  public abstract List<Channel> getChannelByAdmin(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString1, String paramString2, String paramString3, Boolean paramBoolean);

  public abstract List<Channel> getChannelByAdminAndTake(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, String paramString1, String paramString2, String paramString3, Boolean paramBoolean);

  public abstract List<Channel> getChannelByModel(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);

  public abstract List<Channel> getChannelByModelAndTake(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);

  public abstract List<Channel> getChannelByModelAndMember(Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4);

  public abstract Channel findByPath(String paramString, Integer paramInteger, boolean paramBoolean);

  public abstract Channel findByNumber(String paramString);

  public abstract Channel findByName(String paramString);

  public abstract int getAllChannelCount(Integer paramInteger);

  public abstract int updateChannelByInputDepart(Integer paramInteger);

  public abstract int delChannelByInputDepart(Integer paramInteger);

  public abstract int updateChannelByWorkFlow(Integer paramInteger);
}


 
 
 