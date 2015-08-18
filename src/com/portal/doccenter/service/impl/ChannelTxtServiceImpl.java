 package com.portal.doccenter.service.impl;
 
 import com.portal.doccenter.dao.ChannelTxtDao;
 import com.portal.doccenter.entity.Channel;
 import com.portal.doccenter.entity.ChannelTxt;
 import com.portal.doccenter.service.ChannelTxtService;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 @Transactional
 public class ChannelTxtServiceImpl
   implements ChannelTxtService
 {
   private ChannelTxtDao dao;
 
   public ChannelTxt save(ChannelTxt txt, Channel channel)
   {
     if (txt.isAllBlank()) {
       return null;
     }
     txt.setChannel(channel);
     txt.init();
     this.dao.save(txt);
     return txt;
   }
 
   public ChannelTxt update(ChannelTxt txt, Channel channel)
   {
     ChannelTxt entity = (ChannelTxt)this.dao.findById(channel.getId());
     if (entity == null) {
       entity = save(txt, channel);
       return entity;
     }
     if (txt.isAllBlank()) {
       channel.setTxt(null);
       return null;
     }
     entity = (ChannelTxt)this.dao.update(txt);
     entity.blankToNull();
     return entity;
   }
 
   @Autowired
   public void setDao(ChannelTxtDao dao)
   {
     this.dao = dao;
   }
 }


 
 
 