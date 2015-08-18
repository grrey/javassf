 package com.portal.sysmgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.sysmgr.dao.DatabaseConfigDao;
 import com.portal.sysmgr.entity.DatabaseConfig;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class DatabaseConfigDaoImpl extends QueryDaoImpl<DatabaseConfig, Integer>
   implements DatabaseConfigDao
 {
   public DatabaseConfig findUnique()
   {
     Criteria crit = createCriteria();
     crit.addOrder(Order.asc("id"));
     return (DatabaseConfig)findUnique(crit);
   }
 
   protected Class<DatabaseConfig> getEntityClass()
   {
     return DatabaseConfig.class;
   }
 }


 
 
 