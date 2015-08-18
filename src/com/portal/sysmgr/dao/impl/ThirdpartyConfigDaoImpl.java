 package com.portal.sysmgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.sysmgr.dao.ThirdpartyConfigDao;
 import com.portal.sysmgr.entity.ThirdpartyConfig;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class ThirdpartyConfigDaoImpl extends QueryDaoImpl<ThirdpartyConfig, Integer>
   implements ThirdpartyConfigDao
 {
   protected Class<ThirdpartyConfig> getEntityClass()
   {
     return ThirdpartyConfig.class;
   }
 }


 
 
 