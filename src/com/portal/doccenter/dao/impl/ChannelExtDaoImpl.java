 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.ChannelExtDao;
 import com.portal.doccenter.entity.ChannelExt;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ChannelExtDaoImpl extends QueryDaoImpl<ChannelExt, Integer>
   implements ChannelExtDao
 {
   protected Class<ChannelExt> getEntityClass()
   {
     return ChannelExt.class;
   }
 }


 
 
 