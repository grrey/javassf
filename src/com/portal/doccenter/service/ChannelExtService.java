package com.portal.doccenter.service;

import com.portal.doccenter.entity.Channel;
import com.portal.doccenter.entity.ChannelExt;

public abstract interface ChannelExtService
{
  public abstract ChannelExt save(ChannelExt paramChannelExt, Channel paramChannel);

  public abstract ChannelExt update(ChannelExt paramChannelExt);
}


 
 
 