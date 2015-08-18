 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ChannelTxtDao;
 import com.portal.doccenter.entity.ChannelTxt;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ChannelTxtDaoImpl extends QueryDaoImpl<ChannelTxt, Integer>
   implements ChannelTxtDao
 {
   protected Class<ChannelTxt> getEntityClass()
   {
     return ChannelTxt.class;
   }
 }


 
 
 