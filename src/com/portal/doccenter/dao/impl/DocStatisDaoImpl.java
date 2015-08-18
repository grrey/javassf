 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.DocStatisDao;
 import com.portal.doccenter.entity.DocStatis;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class DocStatisDaoImpl extends QueryDaoImpl<DocStatis, Integer>
   implements DocStatisDao
 {
   protected Class<DocStatis> getEntityClass()
   {
     return DocStatis.class;
   }
 }


 
 
 