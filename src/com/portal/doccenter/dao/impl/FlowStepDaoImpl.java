 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.FlowStepDao;
 import com.portal.doccenter.entity.FlowStep;
 import org.hibernate.Criteria;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class FlowStepDaoImpl extends QueryDaoImpl<FlowStep, Integer>
   implements FlowStepDao
 {
   public Page<FlowStep> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   protected Class<FlowStep> getEntityClass()
   {
     return FlowStep.class;
   }
 }


 
 
 