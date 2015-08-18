 package com.portal.sysmgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.sysmgr.dao.SiteConfigDao;
 import com.portal.sysmgr.entity.SiteConfig;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class SiteConfigDaoImpl extends QueryDaoImpl<SiteConfig, Integer>
   implements SiteConfigDao
 {
   protected Class<SiteConfig> getEntityClass()
   {
     return SiteConfig.class;
   }
 }


 
 
 