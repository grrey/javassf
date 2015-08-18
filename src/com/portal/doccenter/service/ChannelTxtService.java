package com.portal.doccenter.service;

import com.portal.doccenter.entity.Channel;
import com.portal.doccenter.entity.ChannelTxt;

public abstract interface ChannelTxtService
{
  public abstract ChannelTxt save(ChannelTxt paramChannelTxt, Channel paramChannel);

  public abstract ChannelTxt update(ChannelTxt paramChannelTxt, Channel paramChannel);
}


 
 
 