 package com.portal.usermgr.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.usermgr.dao.AdminCheckDao;
 import com.portal.usermgr.entity.AdminCheck;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class AdminCheckDaoImpl extends QueryDaoImpl<AdminCheck, Integer>
   implements AdminCheckDao
 {
   public int deleteBySiteId(Integer siteId)
   {
     String hql = "delete from AdminCheck bean where bean.site.id=?";
     return executeQuery(hql, new Object[] { siteId });
   }
 
   protected Class<AdminCheck> getEntityClass()
   {
     return AdminCheck.class;
   }
 }


 
 
 