 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.ChannelExtDao;
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.entity.ChannelExt;
 import com.portal.doccenter.service.ChannelExtService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ChannelExtServiceImpl
   implements ChannelExtService
 {
   private ChannelExtDao dao;
 
   public ChannelExt save(ChannelExt ext, Channel channel)
   {
     channel.setExt(ext);
     ext.setChannel(channel);
     ext.init();
     this.dao.save(ext);
     return ext;
   }
 
   public ChannelExt update(ChannelExt ext) {
     ChannelExt entity = (ChannelExt)this.dao.update(ext);
     entity.blankToNull();
     return entity;
   }
 
   @Autowired
   public void setDao(ChannelExtDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 