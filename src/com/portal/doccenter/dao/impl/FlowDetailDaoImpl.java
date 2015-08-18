 package com.portal.doccenter.dao.impl;
 
 import com.javassf.basic.hibernate4.QueryDaoImpl;
 import com.portal.doccenter.dao.FlowDetailDao;
 import com.portal.doccenter.entity.FlowDetail;
 import org.hibernate.Criteria;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.springframework.data.domain.Page;
 import org.springframework.stereotype.Repository;
 
 @Repository
 public class FlowDetailDaoImpl extends QueryDaoImpl<FlowDetail, Integer>
   implements FlowDetailDao
 {
   public Page<FlowDetail> getPage(int pageNo, int pageSize)
   {
     Criteria crit = createCriteria();
     return findByCriteria(crit, pageNo, pageSize);
   }
 
   public FlowDetail getLastFlowDetail(Integer docId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("doc.id", docId));
     crit.addOrder(Order.desc("priority"));
     return (FlowDetail)findUnique(crit);
   }
 
   public FlowDetail getLastFlowDetail(Integer docId, Integer userId) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("doc.id", docId));
     crit.add(Restrictions.eq("user.id", userId));
     crit.add(Restrictions.eq("checked", Boolean.valueOf(true)));
     crit.addOrder(Order.desc("priority"));
     return (FlowDetail)findUnique(crit);
   }
 
   public FlowDetail getFlowDetailByPri(Integer docId, Integer priority) {
     Criteria crit = createCriteria();
     crit.add(Restrictions.eq("doc.id", docId));
     crit.add(Restrictions.lt("priority", priority));
     crit.add(Restrictions.eq("checked", Boolean.valueOf(true)));
     crit.addOrder(Order.desc("priority"));
     return (FlowDetail)findUnique(crit);
   }
 
   protected Class<FlowDetail> getEntityClass()
   {
     return FlowDetail.class;
   }
 }


 
 
 