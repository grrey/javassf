 package com.portal.datacenter.commdata.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.datacenter.commdata.dao.UnitTypeDao;
 import com.portal.datacenter.commdata.entity.UnitType;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class UnitTypeDaoImpl extends QueryDaoImpl<UnitType, Integer>
   implements UnitTypeDao
 {
   public Page<UnitType> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     crit.addOrder(Order.asc("code"));
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<UnitType> getEntityClass()
   {
     return UnitType.class;
   }
 }

